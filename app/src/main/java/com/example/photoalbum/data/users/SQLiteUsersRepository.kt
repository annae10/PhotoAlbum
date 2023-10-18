package com.example.photoalbum.data.users

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import com.example.photoalbum.data.AccountAlreadyExistsException
import com.example.photoalbum.data.AuthException
import com.example.photoalbum.data.EmptyFieldException
import com.example.photoalbum.data.Field
import com.example.photoalbum.data.settings.AppSettings
import com.example.photoalbum.data.sqlite.entities.SignUpData
import com.example.photoalbum.data.sqlite.entities.User
import com.example.photoalbum.data.sqlite.AppSQLiteContract.UsersTable
import com.example.photoalbum.data.sqlite.wrapSQLiteException
import com.example.photoalbum.data.utils.AsyncLoader
import com.example.photoalbum.presentation.screens.LoginModule
import dagger.Component
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject

class SQLiteUsersRepository //@Inject constructor
    (
    private val db: SQLiteDatabase,
    private val appSettings: AppSettings,
    private val ioDispatcher: CoroutineDispatcher
) : UsersRepository {

    private val currentUserIdFlow = AsyncLoader {
        MutableStateFlow(UserId(appSettings.getCurrentUserId()))
    }

//    override suspend fun isSignedIn(): Boolean {
//        delay(2000)
//        return appSettings.getCurrentUserId() != AppSettings.NO_ACCOUNT_ID
//    }

    override suspend fun signIn (email: String, password: String)
    = wrapSQLiteException(ioDispatcher) {
        try {
            if (email.isBlank()) throw EmptyFieldException(Field.Email)
            if (password.isBlank()) throw EmptyFieldException(Field.Password)

            delay(1000)

            val userId = findUserIdByEmailAndPassword(email, password)
            appSettings.setCurrentUserId(userId)

            currentUserIdFlow.get().value = UserId(userId)
        }catch (e:Exception){

        }
        return@wrapSQLiteException
    }

//    override suspend fun signUp(signUpData: SignUpData) = wrapSQLiteException(ioDispatcher) {
//        signUpData.validate()
//        delay(1000)
//        createUser(signUpData)
//    }
//
//    override suspend fun logout() {
//        appSettings.setCurrentUserId(AppSettings.NO_ACCOUNT_ID)
//        currentUserIdFlow.get().value = UserId(AppSettings.NO_ACCOUNT_ID)
//    }

//    override suspend fun getUser(): Flow<User?> {
//        return currentUserIdFlow.get()
//            .map { userId ->
//                getUserById(userId.value)
//            }
//            .flowOn(ioDispatcher)
//    }
//
//    override suspend fun updateUserName(newUsername: String) = wrapSQLiteException(ioDispatcher)  {
//        if (newUsername.isBlank()) throw EmptyFieldException(Field.Username)
//        delay(1000)
//        val userId = appSettings.getCurrentUserId()
//        if (userId == AppSettings.NO_ACCOUNT_ID) throw AuthException()
//
//        updateUsernameForAccountId(userId, newUsername)
//
//        currentUserIdFlow.get().value = UserId(userId)
//        return@wrapSQLiteException
//    }

    private fun findUserIdByEmailAndPassword(email: String, password: String): Long {
        val cursor = db.query(
            UsersTable.TABLE_NAME,
            arrayOf(UsersTable.COLUMN_ID, UsersTable.COLUMN_PASSWORD),
            "${UsersTable.COLUMN_EMAIL} = ?",
            arrayOf(email),
            null, null, null
        )

        if (cursor.count == 0) return 0
        cursor.moveToFirst()
        val accountPassword = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_PASSWORD))
        if (accountPassword != password) return 0

        return cursor.getLong(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_ID))

    }

    private fun findUserIdByEmail(email: String): Long {
        val cursor = db.query(
            UsersTable.TABLE_NAME,
            arrayOf(UsersTable.COLUMN_ID),
            "${UsersTable.COLUMN_EMAIL} = ?",
            arrayOf(email),
            null, null, null
        )
        return cursor.use {
            if (cursor.count == 0) throw AuthException()
            cursor.moveToFirst()
            val accountEmail = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_EMAIL))
            if (accountEmail != email) throw AuthException()

            cursor.getLong(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_ID))
        }
    }

    private fun createUser(signUpData: SignUpData) {
        try {
            db.insertOrThrow(
                UsersTable.TABLE_NAME,
                null,
                contentValuesOf(
                    UsersTable.COLUMN_EMAIL to signUpData.email,
                    UsersTable.COLUMN_USERNAME to signUpData.username,
                    UsersTable.COLUMN_PASSWORD to signUpData.password
                )
            )
        } catch (e: SQLiteConstraintException) {
            val appException = AccountAlreadyExistsException()
            appException.initCause(e)
            throw appException
        }
    }

    private fun getUserById(userId: Long): User? {
        if (userId == AppSettings.NO_ACCOUNT_ID) return null
        val cursor = db.query(
            UsersTable.TABLE_NAME,
            arrayOf(UsersTable.COLUMN_ID, UsersTable.COLUMN_EMAIL, UsersTable.COLUMN_USERNAME),
            "${UsersTable.COLUMN_ID} = ?",
            arrayOf(userId.toString()),
            null, null, null
        )
        return cursor.use {
            if (cursor.count == 0) return@use null
            cursor.moveToFirst()
            User(
                id = cursor.getLong(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_ID)),
                username = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_USERNAME)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.COLUMN_EMAIL)),
            )
        }
    }

    private fun updateUsernameForAccountId(userId: Long, newUsername: String) {
        db.update(
            UsersTable.TABLE_NAME,
            contentValuesOf(
                UsersTable.COLUMN_USERNAME to newUsername
            ),
            "${UsersTable.COLUMN_ID} = ?",
            arrayOf(userId.toString())
        )
    }

    private class UserId(val value: Long)

}
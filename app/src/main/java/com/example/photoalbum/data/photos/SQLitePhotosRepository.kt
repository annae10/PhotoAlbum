package com.example.photoalbum.data.photos

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import com.example.photoalbum.data.AuthException
import com.example.photoalbum.data.users.UsersRepository
import com.example.photoalbum.data.photos.entities.Photo
import com.example.photoalbum.data.sqlite.AppSQLiteContract.UsersPhotosSettingsTable
import com.example.photoalbum.data.sqlite.AppSQLiteContract.PhotosTable
import com.example.photoalbum.data.sqlite.wrapSQLiteException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first

class SQLitePhotosRepository (
    private val db: SQLiteDatabase,
    private val usersRepository: UsersRepository,
    private val ioDispatcher: CoroutineDispatcher
) : PhotosRepository {
    private val reconstructFlow = MutableSharedFlow<Unit>(replay = 1).also { it.tryEmit(Unit) }

    override suspend fun getPhotos(onlyActive: Boolean): Flow<List<Photo>> {
//        return combine(usersRepository.getUser(), reconstructFlow) { user, _ ->
//            queryPhotos(onlyActive, user?.id)
//        }
//            .flowOn(ioDispatcher)
        TODO()
    }

    override suspend fun makePhoto() {
    }

    override suspend fun viewPhoto(photo: Photo) {
    }

    override suspend fun activateGeolocation(photo: Photo)  = wrapSQLiteException(ioDispatcher) {
        setActiveFlagForPhoto(photo, true)
    }

    override suspend fun deactivateGeolocation(photo: Photo) = wrapSQLiteException(ioDispatcher) {
        setActiveFlagForPhoto(photo, false)
    }

    private suspend fun setActiveFlagForPhoto(photo: Photo, isActive: Boolean) {
//        val user = usersRepository.getUser().first() ?: throw AuthException()
//        saveActiveFlag(user.id, photo.id, isActive)
//        reconstructFlow.tryEmit(Unit)
    }

    private fun queryPhotos(onlyActive: Boolean, userId: Long?): List<Photo> {
        if (userId == null) return emptyList()

        val cursor = queryPhotos(onlyActive, userId)
        return cursor.use {
            val list = mutableListOf<Photo>()
            while (cursor.moveToNext()) {
                list.add(parsePhoto(cursor))
            }
            return@use list
        }
    }

    private fun parsePhoto(cursor: Cursor): Photo {
        return Photo(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(PhotosTable.COLUMN_ID)),
            geolocation = cursor.getString(cursor.getColumnIndexOrThrow(PhotosTable.COLUMN_GEOLOCATION)),
            title = cursor.getString(cursor.getColumnIndexOrThrow(PhotosTable.COLUMN_TITLE)),
            comment = cursor.getString(cursor.getColumnIndexOrThrow(PhotosTable.COLUMN_COMMENT)),
            tag = cursor.getString(cursor.getColumnIndexOrThrow(PhotosTable.COLUMN_TAG))
        )
    }

    private fun saveActiveFlag(userId: Long, photoId: Long, isActive: Boolean) {
        db.insertWithOnConflict(
            UsersPhotosSettingsTable.TABLE_NAME,
            null,
            contentValuesOf(
                UsersPhotosSettingsTable.COLUMN_PHOTO_ID to photoId,
                UsersPhotosSettingsTable.COLUMN_USER_ID to userId,
                UsersPhotosSettingsTable.COLUMN_IS_ACTIVE_GEOLOCATION to isActive
            ),
            SQLiteDatabase.CONFLICT_REPLACE
        )
    }

    private fun queryPhotos(onlyActive: Boolean, userId: Long): Cursor {
        return if (onlyActive) {
            val sql = "SELECT ${PhotosTable.TABLE_NAME}.* " +
                    "FROM ${PhotosTable.TABLE_NAME} " +
                    "LEFT JOIN ${UsersPhotosSettingsTable.TABLE_NAME} " +
                    "ON ${UsersPhotosSettingsTable.COLUMN_PHOTO_ID} = ${PhotosTable.COLUMN_ID} " +
                    "  AND ${UsersPhotosSettingsTable.COLUMN_USER_ID} = ? " +
                    "WHERE ${UsersPhotosSettingsTable.COLUMN_IS_ACTIVE_GEOLOCATION} IS NULL " +
                    "  OR ${UsersPhotosSettingsTable.COLUMN_IS_ACTIVE_GEOLOCATION} = 1"
            db.rawQuery(sql, arrayOf(userId.toString()))
        } else {
            val sql = "SELECT * FROM ${PhotosTable.TABLE_NAME}"
            db.rawQuery(sql, null)
        }
    }

}
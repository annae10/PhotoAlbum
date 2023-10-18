package com.example.photoalbum.data.users
import com.example.photoalbum.data.sqlite.entities.SignUpData
import com.example.photoalbum.data.sqlite.entities.User
import com.example.photoalbum.presentation.screens.LoginModule
import dagger.Component
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

//@Singleton
//@Component(modules = [LoginModule::class])
interface UsersRepository {
    //suspend fun isSignedIn(): Boolean

//    @Provides
    suspend fun signIn(email: String, password: String)
//    suspend fun signUp(signUpData: SignUpData)
//    suspend fun logout()
//    suspend fun getUser(): Flow<User?>
//    suspend fun updateUserName(newUsername: String)
}
package com.example.photoalbum.data.users
import com.example.photoalbum.data.sqlite.entities.SignUpData
import com.example.photoalbum.data.sqlite.entities.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun isSignedIn(): Boolean
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(signUpData: SignUpData)
    suspend fun logout()
    suspend fun getUser(): Flow<User?>
    suspend fun updateUserName(newUsername: String)
}
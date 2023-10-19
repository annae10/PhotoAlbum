package com.example.photoalbum.data.room

data class UserState(
    val users: List<User> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val isGeolocationEnabled: Boolean = true,
    val isAddingUser: Boolean = false
)

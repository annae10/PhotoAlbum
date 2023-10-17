package com.example.photoalbum.data.sqlite.entities

import com.example.photoalbum.data.EmptyFieldException
import com.example.photoalbum.data.Field
import com.example.photoalbum.data.PasswordMismatchException

data class SignUpData(
    val username: String,
    val email: String,
    val password: String,
    val repeatPassword: String
) {
    fun validate() {
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (username.isBlank()) throw EmptyFieldException(Field.Username)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)
        if (password != repeatPassword) throw PasswordMismatchException()
    }
}
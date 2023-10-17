package com.example.photoalbum.domain.login_usecases

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
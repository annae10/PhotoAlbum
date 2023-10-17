package com.example.photoalbum.domain.login_usecases

class ValidateEmail(){
    fun execute(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}
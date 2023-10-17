package com.example.photoalbum.domain.login_usecases

import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.photoalbum.data.EmptyFieldException
import com.example.photoalbum.data.users.UsersRepository
import com.example.photoalbum.presentation.MainViewModel

class ValidateEmailPassword() {

    fun execute(email: String, password: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }

//        viewModelScope.launch {
//
//            try {
//                accountsRepository.signIn(email, password)
//                launchTabsScreen()
//            } catch (e: EmptyFieldException) {
//
//                  processEmptyFieldException(e)
//            }
//        }

        return ValidationResult(
            successful = true
        )
    }
}
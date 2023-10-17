package com.example.photoalbum.presentation.sqlite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoalbum.presentation.MainViewModel
import com.example.photoalbum.domain.login_usecases.ValidateEmail
import com.example.photoalbum.domain.login_usecases.ValidatePassword
import com.example.photoalbum.data.AuthException
import com.example.photoalbum.data.EmptyFieldException
import com.example.photoalbum.data.StorageException
import com.example.photoalbum.domain.login_usecases.ValidateEmailPassword
import com.example.photoalbum.presentation.RegistrationFormEvent
import com.example.photoalbum.presentation.RegistrationFormState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignInViewModel(
) : ViewModel()  {

    fun signIn(login:String, password:String){

    }
    fun Signing(text_login:String, text_password:String){

    }

    private val validateEmail: ValidateEmail = ValidateEmail()
    private val validatePassword: ValidatePassword = ValidatePassword()
    private val validateEmailPassword: ValidateEmailPassword = ValidateEmailPassword()

    var state by mutableStateOf(RegistrationFormState())
    private val validationEventChannel = Channel<MainViewModel.ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent):Boolean {
        when(event) {
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            else -> {  }
        }
        return submitData()
    }

    private fun submitData():Boolean {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val enail_passwordResult = validateEmailPassword.execute(state.email, state.password)

        val hasError = listOf(
            emailResult,
            passwordResult,
            enail_passwordResult
        ).any { !it.successful }

        if(hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return false
        }
        viewModelScope.launch {
            validationEventChannel.send(MainViewModel.ValidationEvent.Success)
        }
        return true
    }
}
package com.example.photoalbum.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.photoalbum.data.users.UsersRepository

class MainViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private var _is_signed = false
    val is_signed
        get() = _is_signed

    fun change_is_signed(){_is_signed=!_is_signed}

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}

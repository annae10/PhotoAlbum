package com.example.photoalbum.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoalbum.data.users.SQLiteUsersRepository
import com.example.photoalbum.data.users.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
class MainViewModel //@Inject constructor
    (
    private val repository: UsersRepository
) : ViewModel() {

    fun login(){
        viewModelScope.launch {
            repository.signIn("","")
        }
    }

    private var _is_signed = false
    val is_signed
        get() = _is_signed

    fun change_is_signed(){_is_signed=!_is_signed}

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}

package com.example.photoalbum.data.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(
    private val dao: UserDao
): ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state
        get() = _state

    fun onEvent(event: UserEvent){
        when(event){
            is UserEvent.ChangeEnableGeolocation -> {
                _state.update{ it.copy(
                    isGeolocationEnabled = event.isGeolocationEnabled
                )}
            }
            is UserEvent.DeleteUser -> {
                viewModelScope.launch {
                    dao.deleteUser(event.user)
                }

            }
            UserEvent.SaveUser -> {
                val firstName = _state.value.firstName
                val lastName = _state.value.lastName
                val email = _state.value.email
                val password = _state.value.password
                val isGeolocationEnabled = _state.value.isGeolocationEnabled
                if(firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()){
                    return
                }
                val user = User(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password,
                    isGeolocationEnabled = isGeolocationEnabled
                )
                viewModelScope.launch { dao.upsertUser(user) }
                _state.update { it.copy(
                    isAddingUser = false,
                    firstName = "",
                    lastName = "",
                    email = "",
                    password = "",
                    isGeolocationEnabled = true
                ) }
            }
            is UserEvent.SetEmail -> {
                _state.update{ it.copy(
                    email = event.email
                )}
            }
            is UserEvent.SetFirstName -> {
                _state.update{ it.copy(
                    firstName = event.firstName
                )}
            }
            is UserEvent.SetLastName -> {
                _state.update{ it.copy(
                    lastName = event.lastName
                )}
            }
            is UserEvent.SetPassword -> {
                _state.update{ it.copy(
                    password = event.password
                )}
            }
            is UserEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingUser = true
                ) }
            }
            UserEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingUser = false
                ) }
            }
        }
    }
}
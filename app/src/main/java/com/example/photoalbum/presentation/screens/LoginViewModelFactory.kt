package com.example.photoalbum.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photoalbum.data.users.SQLiteUsersRepository
import com.example.photoalbum.data.users.UsersRepository
import javax.inject.Inject

class LoginViewModelFactory //@Inject constructor
    (private val repository: UsersRepository):
ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(repository)as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
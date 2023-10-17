package com.example.photoalbum.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoalbum.data.EmptyFieldException
import com.example.photoalbum.data.Repositories.usersRepository
import com.example.photoalbum.data.users.SQLiteUsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

class LoginViewModel (
    repository: SQLiteUsersRepository
): ViewModel(){

    fun signIn(email:String, password:String)=viewModelScope.launch {
        try{
            usersRepository.signIn(email, password)
        } catch (e: EmptyFieldException){

        }
    }


}
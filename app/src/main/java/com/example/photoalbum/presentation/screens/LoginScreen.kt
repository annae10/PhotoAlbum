package com.example.photoalbum.presentation.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.photoalbum.data.users.SQLiteUsersRepository
import com.example.photoalbum.presentation.RegistrationFormEvent
import com.example.photoalbum.presentation.navigation.CurrentUserId
import com.example.photoalbum.presentation.sqlite.SignInViewModel
import dagger.hilt.android.EarlyEntryPoints.get
import org.koin.androidx.compose.get

@Composable
fun LoginScreen(navController: NavHostController
){
    val context = LocalContext.current
    val viewModelSignIn = viewModel<SignInViewModel>()

//    val viewModelFactory = LoginViewModelFactory(repository = SQLiteUsersRepository())
//    val viewModelSignIn = ViewModelProvider(myContext).get(LoginViewModel::class.java)

    val state = viewModelSignIn.state

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Login Screen",
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = state.email,
            onValueChange = {
                viewModelSignIn.onEvent(RegistrationFormEvent.EmailChanged(it))
            },
            isError = state.emailError != null,
            label = { Text("Login")
            }
        )
        if (state.emailError != null) {
            Text(
                text = state.emailError,
                color = Color.Red,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = state.password,
            onValueChange = {
                viewModelSignIn.onEvent(RegistrationFormEvent.PasswordChanged(it))
            },
            isError = state.passwordError != null,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        if (state.passwordError != null) {
            Text(
                text = state.passwordError,
                color = Color.Red,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                if(viewModelSignIn.onEvent(RegistrationFormEvent.Submit))
                {navController.navigate("home_screen")
                }
            }
        ){
            Text(text = "Sign in")
        }
    }
}
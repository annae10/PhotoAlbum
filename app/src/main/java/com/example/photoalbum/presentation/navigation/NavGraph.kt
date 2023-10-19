package com.example.photoalbum.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import coil.annotation.ExperimentalCoilApi
import com.example.photoalbum.data.room.UserDatabase
import com.example.photoalbum.data.room.UserEvent
import com.example.photoalbum.data.room.UserState
import com.example.photoalbum.data.room.UserViewModel
import com.example.photoalbum.presentation.screens.HomeScreen
import com.example.photoalbum.presentation.screens.LoginScreen

@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun SetupNavGraph(navController: NavHostController, myContext: Context, state: UserState,
                  onEvent: (UserEvent)-> Unit) {



    NavHost(
        navController = navController,
        startDestination = start_dest()
    ) {
        composable(route = Screen.Login.route){
            LoginScreen(navController = navController, onEvent)
        }
        composable(route = Screen.Home.route){
            HomeScreen(navController = navController, state, onEvent)
        }
    }
}

@Composable
fun CurrentUserId() = rememberSaveable { mutableStateOf(0)}

@Composable
fun start_dest(): String = if (CurrentUserId().value.equals(0))
    "login_screen"
else "home_screen"

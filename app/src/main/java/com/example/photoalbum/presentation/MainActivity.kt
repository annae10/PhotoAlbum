package com.example.photoalbum.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import com.example.photoalbum.presentation.theme.PhotoAlbumTheme
import com.example.photoalbum.presentation.navigation.SetupNavGraph
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.photoalbum.data.Repositories
import com.example.photoalbum.data.room.UserDatabase
import com.example.photoalbum.data.room.UserViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val db by lazy {
        Room.databaseBuilder(this, UserDatabase::class.java, "users.db").build()
    }

    val viewModel by viewModels<UserViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return UserViewModel(db.dao) as T
                }
            }
        }
    )

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    @OptIn(ExperimentalPagingApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Repositories.init(applicationContext)
        val myContext: Context = this
        setContent {

            PhotoAlbumTheme {
                val navController = rememberNavController()
                val state by viewModel.state.collectAsState()
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

                fetchLocation()
                SetupNavGraph(navController = navController, myContext, state, viewModel::onEvent)
            }
        }
    }

    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation
        if (ContextCompat.checkSelfPermission(applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION
            )!= PackageManager.PERMISSION_GRANTED){
            return
        }
        task.addOnSuccessListener {
            if(it != null){
                val latitude = it.latitude
                val longitude = it.longitude
            }
        }
    }

    private fun hasRequiredPermissions(): Boolean{
        return CAMERAX_PERMISSIONS.all{
            ContextCompat.checkSelfPermission(
                applicationContext, it
            )== PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
    }
}



@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T{
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}
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
import com.example.photoalbum.data.Repositories

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagingApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Repositories.init(applicationContext)
        setContent {

            PhotoAlbumTheme {
                val navController = rememberNavController()

                SetupNavGraph(navController = navController)
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
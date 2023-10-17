package com.example.photoalbum.presentation.camera

import android.Manifest
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    viewModel: CameraViewModel = hiltViewModel()
) {

    val permissions = if (Build.VERSION.SDK_INT <= 28){
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }else listOf(Manifest.permission.CAMERA)

    val permissionState = rememberMultiplePermissionsState(
        permissions = permissions)

    if (!permissionState.allPermissionsGranted){
        SideEffect {
            permissionState.launchMultiplePermissionRequest()
        }
    }


    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val configuration = LocalConfiguration.current
    val screeHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    var previewView:PreviewView

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (permissionState.allPermissionsGranted){
            Box(modifier = Modifier
                .height(screeHeight * 0.85f)
                .width(screenWidth)) {
                AndroidView(
                    factory = {
                        previewView = PreviewView(it)
                        viewModel.showCameraPreview(previewView, lifecycleOwner)
                        previewView
                    },
                    modifier = Modifier
                        .height(screeHeight * 0.85f)
                        .width(screenWidth)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ){
            IconButton(
                onClick = {
                    if (permissionState.allPermissionsGranted){
                        viewModel.captureAndSave(context)
                    }
                    else{
                        Toast.makeText(
                            context,
                            "Please accept permission in app settings",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }) {

                Icon(
                    imageVector = Icons.Filled.Camera,
                    contentDescription = "",
                    modifier = Modifier.size(45.dp)
                )

            }
            IconButton(onClick = {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                Icon(
                    imageVector = Icons.Filled.Image,
                    contentDescription = "",
                    modifier = Modifier.size(45.dp)
                )
            }
        }

    }
}



//    var selectedImageUris by remember {
//        mutableStateOf<List<Uri>>(emptyList())
//    }

//    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.PickMultipleVisualMedia(),
//        onResult = { uris -> selectedImageUris = uris }
//    )

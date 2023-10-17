package com.example.photoalbum.presentation.camera

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoalbum.domain.camera.CustomCameraRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CameraViewModel @Inject constructor(
    private val repo: CustomCameraRepo
):ViewModel() {

    fun showCameraPreview(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner
    ){
        viewModelScope.launch {
            repo.showCameraPreview(
                previewView,
                lifecycleOwner
            )
        }
    }

    fun captureAndSave(context: Context){
        viewModelScope.launch {
            repo.captureAndSaveImage(context)
        }
    }


}
package com.example.photoalbum.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.photoalbum.data.unsplash.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: Repository
): ViewModel() {
    val getAllImages = repository.getAllImages()
}
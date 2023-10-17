package com.example.photoalbum.data.photos
import com.example.photoalbum.data.photos.entities.Photo
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {

    suspend fun getPhotos(onlyActive: Boolean = false): Flow<List<Photo>>

    suspend fun makePhoto()

    suspend fun viewPhoto(photo: Photo)

    suspend fun activateGeolocation(photo: Photo)

    suspend fun deactivateGeolocation(photo: Photo)
}
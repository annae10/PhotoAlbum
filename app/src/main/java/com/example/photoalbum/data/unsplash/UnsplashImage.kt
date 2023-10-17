package com.example.photoalbum.data.unsplash

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.photoalbum.data.utils.Constants.UNSPLASH_IMAGE_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = UNSPLASH_IMAGE_TABLE)
data class UnsplashImage(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @Embedded
    val urls: Urls,
    val likes: Int,
    @Embedded
    val user: UserUnsplash
)
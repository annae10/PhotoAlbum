package com.example.photoalbum.data.unsplash

import androidx.room.Embedded
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserUnsplash(
    @SerialName("links")
    @Embedded
    val userLinks: UserLinks,
    val username: String
)
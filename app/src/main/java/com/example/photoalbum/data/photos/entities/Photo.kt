package com.example.photoalbum.data.photos.entities

data class Photo (
    val id: Long,
    val geolocation: String,
    val title: String,
    val comment: String,
    val tag: String
)
package com.example.photoalbum.data.unsplash

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.photoalbum.data.unsplash.dao.UnsplashImageDao
import com.example.photoalbum.data.unsplash.dao.UnsplashRemoteKeysDao

@Database(entities = [UnsplashImage::class, UnsplashRemoteKeys::class], version = 1)
abstract class UnsplashDatabase : RoomDatabase() {

    abstract fun unsplashImageDao(): UnsplashImageDao
    abstract fun unsplashRemoteKeysDao(): UnsplashRemoteKeysDao

}
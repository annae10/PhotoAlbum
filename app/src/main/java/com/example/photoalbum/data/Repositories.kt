package com.example.photoalbum.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.photoalbum.data.photos.PhotosRepository
import com.example.photoalbum.data.photos.SQLitePhotosRepository
import com.example.photoalbum.data.sqlite.AppSQLiteHelper
import com.example.photoalbum.data.settings.AppSettings
import com.example.photoalbum.data.settings.SharedPreferencesAppSettings
import com.example.photoalbum.data.users.SQLiteUsersRepository
import com.example.photoalbum.data.users.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object Repositories {
    private lateinit var applicationContext: Context

    private val database: SQLiteDatabase by lazy<SQLiteDatabase> {
        AppSQLiteHelper(applicationContext).writableDatabase
    }

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    private val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(applicationContext)
    }

    val usersRepository: UsersRepository by lazy {
        SQLiteUsersRepository(database, appSettings, ioDispatcher)
    }

    val photosRepository: PhotosRepository by lazy {
        SQLitePhotosRepository(database, usersRepository, ioDispatcher)
    }

    fun init(context: Context) {
        applicationContext = context
    }
}
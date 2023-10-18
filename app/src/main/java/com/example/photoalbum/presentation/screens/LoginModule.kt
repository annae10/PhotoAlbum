package com.example.photoalbum.presentation.screens

import android.database.sqlite.SQLiteDatabase
import com.example.photoalbum.data.settings.AppSettings
import com.example.photoalbum.data.users.SQLiteUsersRepository
import com.example.photoalbum.data.users.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
object LoginModule {
//    @Provides
//    @Singleton
    fun provideLoginRepository(database: SQLiteDatabase, appSettings: AppSettings, ioDispatcher: CoroutineDispatcher): UsersRepository {
        return SQLiteUsersRepository(database, appSettings = appSettings, ioDispatcher = ioDispatcher)
    }
}
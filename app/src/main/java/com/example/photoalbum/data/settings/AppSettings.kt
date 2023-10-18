package com.example.photoalbum.data.settings

import com.example.photoalbum.presentation.screens.LoginModule
import dagger.Component

interface AppSettings {

    fun getCurrentUserId(): Long

    fun setCurrentUserId(accountId: Long)

    companion object {

        const val NO_ACCOUNT_ID = -1L
    }

}
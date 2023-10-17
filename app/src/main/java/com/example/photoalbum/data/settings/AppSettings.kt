package com.example.photoalbum.data.settings

interface AppSettings {

    fun getCurrentUserId(): Long

    fun setCurrentUserId(accountId: Long)

    companion object {

        const val NO_ACCOUNT_ID = -1L
    }

}
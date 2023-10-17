package com.example.photoalbum.data.sqlite

object AppSQLiteContract {
    object UsersTable {
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
    }
    object PhotosTable {
        const val TABLE_NAME = "photos"
        const val COLUMN_ID = "id"
        const val COLUMN_GEOLOCATION = "geolocation"
        const val COLUMN_TITLE = "title"
        const val COLUMN_COMMENT = "comment"
        const val COLUMN_TAG = "tag"
    }
    object UsersPhotosSettingsTable {
        const val TABLE_NAME = "users_photos_settings"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_PHOTO_ID = "photo_id"
        const val COLUMN_IS_ACTIVE_GEOLOCATION = "is_active_geolocation"
    }
}
package com.example.photoalbum.data.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppSQLiteHelper (private val applicationContext: Context)
    :SQLiteOpenHelper (applicationContext, "database.db",null,1){
    override fun onCreate(db: SQLiteDatabase) {
        val sql = applicationContext.assets.open("sqlite_init.sql").bufferedReader().use {
            it.readText()
        }
        sql.split(';')
            .filter { it.isNotBlank() }
            .forEach { db.execSQL(it) }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}
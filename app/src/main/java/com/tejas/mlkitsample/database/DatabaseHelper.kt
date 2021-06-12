package com.tejas.mlkitsample.database

import android.content.Context
import androidx.room.Room
import com.tejas.mlkitsample.BuildConfig
import javax.inject.Inject

class DatabaseHelper @Inject constructor(context: Context) {
    private val db = Room.databaseBuilder(context, Database::class.java, BuildConfig.DATABASE)
        .fallbackToDestructiveMigration()
        .build()

    fun getDatabase() = db

    fun clearDatabase() = db.clearAllTables()
}
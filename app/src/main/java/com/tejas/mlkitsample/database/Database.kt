package com.tejas.mlkitsample.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tejas.mlkitsample.database.dao.ScannedDao
import com.tejas.mlkitsample.model.ScannedData

@Database(
    entities = [ScannedData::class],
    version = 1,
    exportSchema = true
)
abstract class Database : RoomDatabase() {

    abstract fun getBaseDao(): ScannedDao
}
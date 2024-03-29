package com.tejas.mlkitsample.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tejas.mlkitsample.model.ScannedData

@Dao
interface ScannedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScannedData(data: ScannedData)

    @Query("SELECT * FROM SCANNEDDATA")
    fun getAllData(): LiveData<MutableList<ScannedData>>

    @Query("SELECT * FROM SCANNEDDATA WHERE isSynced = 0")
    fun getDataToSync(): MutableList<ScannedData>

    @Query("SELECT * FROM SCANNEDDATA where id=:id")
    suspend fun getDataById(id: Int): MutableList<ScannedData>

    @Query("UPDATE SCANNEDDATA SET isSynced = 1 WHERE isSynced = 0")
    suspend fun updateDataSync()
}
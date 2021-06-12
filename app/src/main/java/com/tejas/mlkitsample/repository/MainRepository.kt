package com.tejas.mlkitsample.repository

import com.tejas.mlkitsample.api.MainApiService
import com.tejas.mlkitsample.database.Database
import com.tejas.mlkitsample.model.ScannedData
import javax.inject.Inject

class MainRepository @Inject constructor(
    private var mainApiService: MainApiService,
    private var database: Database
) {

    suspend fun insertData(data: ScannedData) {
        database.getBaseDao().insertScannedData(data)
    }

    fun getListOfStoredData() = database.getBaseDao().getAllData()
}
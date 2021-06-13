package com.tejas.mlkitsample.api

import com.tejas.mlkitsample.model.SyncDataResponse
import com.tejas.mlkitsample.model.UploadScannedDataModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MainApiService {

    @POST("/api/users")
    suspend fun syncData(
        @Body body: UploadScannedDataModel
    ): Response<SyncDataResponse>
}
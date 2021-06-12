package com.tejas.mlkitsample.api

import retrofit2.http.Body
import retrofit2.http.POST

interface MainApiService {

    @POST("/api/users")
    suspend fun syncData(
        @Body body: MutableMap<String, String>
    )
}
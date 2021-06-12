package com.tejas.mlkitsample.di

import com.tejas.mlkitsample.api.MainApiService
import com.tejas.mlkitsample.api.RetrofitClient
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun getMainApiService(apiClient: RetrofitClient): MainApiService {
        return apiClient.getRetrofitInstance().create(MainApiService::class.java)
    }
}
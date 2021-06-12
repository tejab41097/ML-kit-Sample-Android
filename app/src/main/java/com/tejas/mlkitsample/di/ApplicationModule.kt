package com.tejas.mlkitsample.di

import android.content.Context
import androidx.annotation.NonNull
import com.tejas.mlkitsample.api.RetrofitClient
import com.tejas.mlkitsample.database.Database
import com.tejas.mlkitsample.database.DatabaseHelper
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {
    @Provides
    fun provideDbHelper(@NonNull context: Context): Database {
        return DatabaseHelper(context).getDatabase()
    }

    @Provides
    fun provideApiClient() = RetrofitClient()
}
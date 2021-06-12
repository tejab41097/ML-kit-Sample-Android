package com.tejas.mlkitsample.application

import android.app.Application
import com.tejas.mlkitsample.di.ApplicationComponent
import com.tejas.mlkitsample.di.DaggerApplicationComponent

class MLKitApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        applicationComponent =
            DaggerApplicationComponent.factory().create(applicationContext)
        super.onCreate()
    }
}
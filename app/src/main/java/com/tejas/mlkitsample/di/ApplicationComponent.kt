package com.tejas.mlkitsample.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [ApplicationModule::class, MainActivityModule::class]
)
interface ApplicationComponent {

    fun mainActivityComponent(): MainActivityComponent.Factory

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}
package com.example.testapplication

import android.app.Application
import com.example.testapplication.data.AppContainer
import com.example.testapplication.data.DefaultAppContainer
import com.example.testapplication.data.local.AppContainerLocal
import com.example.testapplication.data.local.AppDataContainer

class AppApplication: Application() {
    /**
     * Экземпляр AppContainer, используемый остальными классами для получения зависимостей
     */
    lateinit var container: AppContainer
    lateinit var containerLocal: AppContainerLocal

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
        containerLocal = AppDataContainer(this)
    }
}
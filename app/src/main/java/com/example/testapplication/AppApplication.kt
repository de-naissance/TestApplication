package com.example.testapplication

import android.app.Application
import com.example.testapplication.data.AppContainer
import com.example.testapplication.data.DefaultAppContainer

class AppApplication: Application() {
    /**
     * Экземпляр AppContainer, используемый остальными классами для получения зависимостей
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
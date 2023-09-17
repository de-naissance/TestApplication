package com.example.testapplication.data.local

import android.content.Context

interface AppContainerLocal {
    val appRepositoryLocal: AppRepositoryLocal
}

class AppDataContainer(private val context: Context) : AppContainerLocal {
    override val appRepositoryLocal: AppRepositoryLocal by lazy {
        OfflineRepository(
            AppDatabase.getDatabase(context).favoriteDao()
        )
    }
}
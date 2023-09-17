package com.example.testapplication.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.testapplication.AppApplication
import com.example.testapplication.ui.screens.homeScreen.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val appRepository = appApplication().container.appRepository
            val appRepositoryLocal = appApplication().containerLocal.appRepositoryLocal
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AppApplication)
            HomeViewModel(
                appRepository = appRepository,
                appRepositoryLocal = appRepositoryLocal
            )
        }
    }
}

fun CreationExtras.appApplication(): AppApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AppApplication)
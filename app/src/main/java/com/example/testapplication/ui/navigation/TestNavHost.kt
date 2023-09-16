package com.example.testapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.testapplication.ui.AppViewModelProvider
import com.example.testapplication.ui.screens.homeScreen.HomeDestination
import com.example.testapplication.ui.screens.homeScreen.HomeScreen
import com.example.testapplication.ui.screens.homeScreen.HomeViewModel

@Composable
fun TestNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            val homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)

            HomeScreen(
                appUiState = homeViewModel.appUiState,
                viewModel = homeViewModel
            )
        }
    }
}
package com.example.testapplication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.testapplication.ui.navigation.TestNavHost

@Composable
fun TestApp(navController: NavHostController = rememberNavController()) {
    TestNavHost(navController = navController)
}
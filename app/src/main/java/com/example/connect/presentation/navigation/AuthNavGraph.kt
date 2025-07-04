package com.example.connect.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.connect.presentation.ui.auth.AuthScreen

@Composable
fun AuthNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Auth.Register.route
    ){
        composable(Screens.Auth.Register.route) {
            AuthScreen()
        }
    }
}
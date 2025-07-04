package com.example.connect.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Auth.route
    ){
        composable(Screens.Auth.route) {
            AuthNavGraph(rememberNavController())
        }
        composable(Screens.Connect.route) {
            ConnectNavGraph(rememberNavController())
        }
    }
}
package com.example.connect.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun ConnectNavGraph (navHostController: NavHostController){
    NavHost(
        navController = navHostController,
        startDestination = Screens.Connect.Home.route
    ){
        composable(Screens.Connect.Home.route){

        }
    }
}
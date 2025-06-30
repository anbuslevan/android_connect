package com.example.connect.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.connect.ui.screen.HomeScreen
import com.example.connect.ui.screen.LoginScreen
import com.example.connect.ui.screen.SettingsScreen
import com.example.connect.utils.Screens

@Composable
fun NavigationHost(navigation_controller: NavHostController){
    NavHost(navController = navigation_controller, startDestination = Screens.Login.route){
        composable(Screens.Login.route){
            LoginScreen(navigation_controller)
        }
        composable(Screens.Home.route) {
            HomeScreen(navigation_controller)
        }
        composable(Screens.Settings.route) {
            SettingsScreen()
        }
    }
}
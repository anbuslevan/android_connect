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
import com.example.connect.ui.screen.LoginScreen

@Composable
fun NavigationHost(navigation_controller: NavHostController){
    NavHost(navController = navigation_controller, startDestination = "login"){
        composable("login"){
            LoginScreen(onLoginSuccess = {
                navigation_controller.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }
        composable("home") {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                BasicText("Welcome Home!")
            }
        }
    }
}
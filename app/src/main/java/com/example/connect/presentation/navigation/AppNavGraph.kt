package com.example.connect.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.connect.presentation.ui.auth.AuthScreen
import com.example.connect.presentation.ui.chat.ChatScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Auth.route
    ){
        composable(Screens.Auth.route) {
            AuthScreen()
        }
        composable(Screens.Chat.route) {
            ChatScreen()
        }
    }
}
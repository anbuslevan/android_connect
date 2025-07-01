package com.example.connect.presentation.navigation

sealed class Screens(val route: String) {
    data object Auth: Screens("auth")
    data object Chat: Screens("chat")
}
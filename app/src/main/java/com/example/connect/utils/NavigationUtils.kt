package com.example.connect.utils

sealed class Screens(val route: String) {
    data object Login : Screens("login")
    data object Home : Screens("home")
    data object Settings : Screens("settings")
}
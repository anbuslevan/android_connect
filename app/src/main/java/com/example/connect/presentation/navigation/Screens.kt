package com.example.connect.presentation.navigation

interface Route {
    val route: String
}

sealed class Screens(override val route: String): Route {
    data object Auth: Screens("auth") {
        object Register: Route {
            override val route: String = "${Auth.route}/register"
        }
    }
    data object Connect: Screens("connect") {
        object Home: Route {
            override val route: String = "${Connect.route}/home"
        }
    }
}
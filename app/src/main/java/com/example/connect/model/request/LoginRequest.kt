package com.example.connect.model.request

data class LoginRequest (
    val user_name: String,
    val email: String,
    val password: String
)
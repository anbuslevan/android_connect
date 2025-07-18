package com.example.connect.domain.model.request

data class LoginRequest (
    val email: String,
    val password: String
) {
    override fun toString(): String {
        return "LoginRequest(email='$email', password='$password')"
    }
}
package com.example.connect.domain.model.response

data class AuthResponse (
    val message: String
) {
    override fun toString(): String {
        return "AuthResponse(message='$message')"
    }
}
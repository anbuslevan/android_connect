package com.example.connect.model.response

data class LoginResponse (
    val message: String
) {
    override fun toString(): String {
        return "LoginResponse(message='$message')"
    }
}
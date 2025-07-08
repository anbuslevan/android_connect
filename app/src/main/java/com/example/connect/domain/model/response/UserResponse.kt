package com.example.connect.domain.model.response

data class UserResponse (
    val userId: String,
    val publicKey: String,
){
    override fun toString(): String {
        return "UserResponse(userId='$userId', publicKey='$publicKey')"
    }
}
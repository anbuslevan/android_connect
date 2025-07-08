package com.example.connect.domain.model.response

data class PublicKeyResponse (
    val message: PublicKey
)

class PublicKey (
    val userId: String,
    val publicKey: String
)
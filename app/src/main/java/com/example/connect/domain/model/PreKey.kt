package com.example.connect.domain.model

data class PreKey(
    val keyId: Int,
    val publicKey: String,
    val signature: String? = null
)


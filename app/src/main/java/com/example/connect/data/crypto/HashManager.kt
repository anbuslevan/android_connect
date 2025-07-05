package com.example.connect.data.crypto

import java.security.MessageDigest
import javax.inject.Inject

class HashManager @Inject constructor(){
    fun hashPayload(payload: String): String{
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(payload.toByteArray(Charsets.UTF_8))
        return hash.joinToString("") { "%02x".format(it) }
    }
}
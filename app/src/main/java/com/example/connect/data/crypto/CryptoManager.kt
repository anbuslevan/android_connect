package com.example.connect.data.crypto

import android.util.Base64
import com.google.gson.Gson
import java.security.MessageDigest
import java.security.PublicKey
import javax.crypto.Cipher
import javax.inject.Inject

class CryptoManager @Inject constructor(
    private val gson: Gson
){
    fun encrypt(payload: Any, publicKey: PublicKey?): String? {
        return try {
            val serializedPayload = gson.toJson(payload)
            val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding")
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)

            val encryptedByte = cipher.doFinal(serializedPayload.toByteArray(Charsets.UTF_8))
            Base64.encodeToString(encryptedByte, Base64.NO_WRAP)
        } catch (exception: Exception){
            null
        }
    }

    fun decrypt() {

    }

    fun hash(payload: String): String{
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(payload.toByteArray(Charsets.UTF_8))
        return hash.joinToString("") { "%02x".format(it) }
    }
}
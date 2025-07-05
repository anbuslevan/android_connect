package com.example.connect.data.crypto

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit
import com.example.connect.network.adapter.NetworkResponse
import com.example.connect.repository.AuthRepository
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import com.google.gson.Gson
import javax.crypto.Cipher

class ServerPublicKeyStore @Inject constructor(
    private val gson: Gson,
    private val repository: AuthRepository,
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("server_public_key", Context.MODE_PRIVATE)
    }

    private val key = "public_key"

    fun hasKey(): Boolean = sharedPreferences.contains(key)

    fun storePublicKey(publicKey: String) {
        sharedPreferences.edit { putString(key, publicKey) }
    }

    fun getPublicKey(): PublicKey? {
        val encodedPublicKey = sharedPreferences.getString(key, "")
        val decoded = Base64.decode(encodedPublicKey, Base64.DEFAULT)
        val spec = X509EncodedKeySpec(decoded)
        val publicKey = KeyFactory.getInstance("RSA").generatePublic(spec)
        return publicKey
    }

    fun clear() {
        sharedPreferences.edit { remove(key) }
    }

    suspend fun fetchAndStorePublicKeyServer() {
         if(!hasKey()) {
             try{
                val response = repository.getPublicKey()

                if(response is NetworkResponse.Success && !response.body.isNullOrBlank()){
                    val publicKey = response.body
                        .replace("-----BEGIN PUBLIC KEY-----", "")
                        .replace("-----END PUBLIC KEY-----", "")
                        .replace("\\s+".toRegex(), "")

                    storePublicKey(publicKey)
                    Log.d("Public key", publicKey)
                }
            } catch (_: Exception){ }
        }
    }

    fun encryptPayload(payload: Any): String?{
        return try {
            val serializedPayload = gson.toJson(payload)
            val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding")
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey())

            val encryptedByte = cipher.doFinal(serializedPayload.toByteArray(Charsets.UTF_8))
            Base64.encodeToString(encryptedByte, Base64.NO_WRAP)
        } catch (exception: Exception){
            null
        }
    }
}

package com.example.connect.data.crypto

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import androidx.core.content.edit
import com.example.connect.network.adapter.NetworkResponse
import com.example.connect.domain.repository.AuthRepository
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.inject.Inject

class ServerPublicKeyStore @Inject constructor(
    private val gson: Gson,
    private val cryptoManager: CryptoManager,
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

                if(response is NetworkResponse.Success && response.body != null){
                    val publicKey = response.body.message.publicKey
                        .replace("-----BEGIN PUBLIC KEY-----", "")
                        .replace("-----END PUBLIC KEY-----", "")
                        .replace("\\s+".toRegex(), "")

                    storePublicKey(publicKey)
                    Log.d("Public key", publicKey)
                }
            } catch (_: Exception){ }
        }
    }

    fun encryptPayload(payload: Any): String? {
        return cryptoManager.encrypt(payload, getPublicKey())
    }
}

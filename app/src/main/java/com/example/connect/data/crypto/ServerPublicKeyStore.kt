package com.example.connect.data.crypto

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit
import com.example.connect.network.adapter.NetworkResponse
import com.example.connect.repository.AuthRepository

class ServerPublicKeyStore @Inject constructor(
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

    fun getPublicKey(): String? = sharedPreferences.getString(key, null)

    fun clear() {
        sharedPreferences.edit { remove(key) }
    }

    suspend fun fetchAndStorePublicKeyServer(){
        if(!hasKey()){
            try{
                val response = repository.getPublicKey()

                if(response is NetworkResponse.Success && !response.body.isNullOrBlank()){
                    storePublicKey(response.body)
                }
            } catch (exception: Exception){

            }
        }
    }
}

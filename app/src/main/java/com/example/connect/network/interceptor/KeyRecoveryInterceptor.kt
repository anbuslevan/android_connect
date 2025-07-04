package com.example.connect.network.interceptor

import com.example.connect.data.crypto.ServerPublicKeyStore
import com.example.connect.model.response.ErrorResponse
import com.example.connect.network.adapter.NetworkResponse
import okhttp3.Interceptor
import okhttp3.Response
import java.security.PublicKey

class KeyRecoveryInterceptor (
    private val serverPublicKeyStore: ServerPublicKeyStore,
    private val publicKeyFetcher: suspend () -> PublicKey
): Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response = chain.proceed(request)

        return chain.proceed(request)
    }

    private fun isKeyError(response: NetworkResponse<*, ErrorResponse>): Boolean {
        if(response !is NetworkResponse.Error){
            return false
        }
        if(response.code == 400 || response.code == 403){
            return response.body.code == "Invalid public key"
        }
        return false
    }
}
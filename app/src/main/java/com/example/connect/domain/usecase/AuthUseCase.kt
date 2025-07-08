package com.example.connect.domain.usecase

import com.example.connect.data.crypto.CryptoManager
import com.example.connect.data.crypto.IdentityKeyStore
import com.example.connect.data.crypto.PublicKeyStore
import com.example.connect.data.crypto.ServerPublicKeyStore
import com.example.connect.domain.model.request.AuthRequest
import com.example.connect.domain.model.request.MobileAuthRequest
import com.example.connect.domain.model.request.TwoFARequest
import com.example.connect.domain.model.response.AuthResponse
import com.example.connect.domain.model.response.ErrorResponse
import com.example.connect.domain.repository.AuthRepository
import com.example.connect.network.adapter.NetworkResponse
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val cryptoManager: CryptoManager,
    private val repository: AuthRepository,
    private val publicKeyStore: PublicKeyStore,
    private val identityKeyStore: IdentityKeyStore,
    private val serverPublicKeyStore: ServerPublicKeyStore
) {
    suspend fun requestOTP(mobileNumber: String): NetworkResponse<AuthResponse, ErrorResponse> {
        val payload = serverPublicKeyStore.encryptPayload(
            MobileAuthRequest(mobileNumber = mobileNumber,
                deviceToken = identityKeyStore.generateDeviceId()
            )
        )
        return repository.requestOTP(AuthRequest(encryptedContext = payload ?: ""))
    }

    suspend fun verifyOTP(mobileNumber: String, otp: String): NetworkResponse<AuthResponse, ErrorResponse> {
        val payload = serverPublicKeyStore.encryptPayload(
            TwoFARequest(mobileNumber = mobileNumber,
                otp = cryptoManager.hash(otp),
                deviceToken = identityKeyStore.generateDeviceId()
            )
        )
        return repository.verifyOTP(AuthRequest(encryptedContext = payload ?: ""))
    }

    suspend fun fetchServersPublicKey(){
        publicKeyStore.fetchPublicKey()
    }

    fun generateDeviceId(){
        identityKeyStore.generateDeviceId()
    }
}
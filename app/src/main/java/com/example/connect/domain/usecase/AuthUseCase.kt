package com.example.connect.domain.usecase

import com.example.connect.data.crypto.CryptoManager
import com.example.connect.data.crypto.IdentityKeyStore
import com.example.connect.data.crypto.PreKeyStore
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
    private val preKeyStore: PreKeyStore,
    private val publicKeyStore: PublicKeyStore,
    private val identityKeyStore: IdentityKeyStore,
    private val serverPublicKeyStore: ServerPublicKeyStore
) {
    suspend fun requestOTP(mobileNumber: String): NetworkResponse<AuthResponse, ErrorResponse> {
        val payload = serverPublicKeyStore.encryptPayload(
            MobileAuthRequest(mobileNumber = mobileNumber,
                deviceId = identityKeyStore.getDeviceId()
            )
        )
        return repository.requestOTP(AuthRequest(encryptedContext = payload ?: ""))
    }

    suspend fun verifyOTP(mobileNumber: String, otp: String): NetworkResponse<AuthResponse, ErrorResponse> {
        val payload = serverPublicKeyStore.encryptPayload(
            TwoFARequest(mobileNumber = mobileNumber,
                otp = cryptoManager.hash(otp),
                deviceId = identityKeyStore.getDeviceId()
            )
        )
        return repository.verifyOTP(AuthRequest(encryptedContext = payload ?: ""))
    }

    suspend fun registerDevice() {
        val deviceId = identityKeyStore.getDeviceId()
        val identityKeyPair = identityKeyStore.ensureKeyPair()
//
//        val signedPreKeyId = preKeyStore.generateSignedPreKey(identityKeyPair.private)
//        val oneTimePreKeys = preKeyStore.gener

    }

    suspend fun fetchServersPublicKey(){
        publicKeyStore.fetchPublicKey()
    }

    fun generateDeviceId(){
        identityKeyStore.getDeviceId()
    }
}
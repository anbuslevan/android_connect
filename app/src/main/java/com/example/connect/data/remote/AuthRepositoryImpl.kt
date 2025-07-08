package com.example.connect.data.remote

import com.example.connect.domain.model.request.AuthRequest
import com.example.connect.domain.model.response.AuthResponse
import com.example.connect.domain.model.response.ErrorResponse
import com.example.connect.domain.model.response.PublicKeyResponse
import com.example.connect.domain.repository.AuthRepository
import com.example.connect.network.adapter.NetworkResponse
import com.example.connect.service.AuthService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
): AuthRepository {
    override suspend fun requestOTP(authRequest: AuthRequest): NetworkResponse<AuthResponse, ErrorResponse> {
        return authService.requestOTP(authRequest)
    }

    override suspend fun verifyOTP(authRequest: AuthRequest): NetworkResponse<AuthResponse, ErrorResponse> {
        return authService.verifyOTP(authRequest)
    }

    override suspend fun getPublicKey(): NetworkResponse<PublicKeyResponse, ErrorResponse> {
        return authService.getPublicKey()
    }

}
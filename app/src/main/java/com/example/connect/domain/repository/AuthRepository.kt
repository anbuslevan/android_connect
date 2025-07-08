package com.example.connect.domain.repository

import com.example.connect.domain.model.request.AuthRequest
import com.example.connect.domain.model.response.AuthResponse
import com.example.connect.domain.model.response.ErrorResponse
import com.example.connect.domain.model.response.PublicKeyResponse
import com.example.connect.network.adapter.NetworkResponse

interface AuthRepository {
    suspend fun getPublicKey(): NetworkResponse<PublicKeyResponse, ErrorResponse>
    suspend fun requestOTP(authRequest: AuthRequest): NetworkResponse<AuthResponse, ErrorResponse>
    suspend fun verifyOTP(authRequest: AuthRequest): NetworkResponse<AuthResponse, ErrorResponse>
}
package com.example.connect.repository

import com.example.connect.model.request.AuthRequest
import com.example.connect.network.AuthService
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authService: AuthService){
    suspend fun requestOTP(authRequest: AuthRequest) = authService.requestOTP(authRequest)
    suspend fun verifyOTP(authRequest: AuthRequest) = authService.verifyOTP(authRequest)
    suspend fun getPublicKey() = authService.getPublicKey()
}
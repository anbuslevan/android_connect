package com.example.connect.repository

import com.example.connect.model.request.LoginRequest
import com.example.connect.model.request.TwoFARequest
import com.example.connect.network.AuthService
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authService: AuthService){
    suspend fun login(email: String, password: String) = authService.login(LoginRequest(email, password))
    suspend fun requestOTP(phoneNumber: String) = authService.requestOTP(TwoFARequest(mobileNumber = phoneNumber))
    suspend fun getPublicKey() = authService.getPublicKey()
}
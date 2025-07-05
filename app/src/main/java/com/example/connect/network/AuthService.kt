package com.example.connect.network

import com.example.connect.model.request.AuthRequest
import com.example.connect.model.request.LoginRequest
import com.example.connect.model.response.ErrorResponse
import com.example.connect.model.response.AuthResponse
import com.example.connect.network.adapter.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @GET("auth/public-key")
    suspend fun getPublicKey(): NetworkResponse<String, ErrorResponse>

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): NetworkResponse<AuthResponse, ErrorResponse>

    @POST("auth/request-otp")
    suspend fun requestOTP(@Body authRequest: AuthRequest): NetworkResponse<AuthResponse, ErrorResponse>

    @POST("auth/verify-otp")
    suspend fun verifyOTP(@Body authRequest: AuthRequest): NetworkResponse<AuthResponse, ErrorResponse>
}
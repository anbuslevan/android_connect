package com.example.connect.network

import com.example.connect.model.request.LoginRequest
import com.example.connect.model.request.TwoFARequest
import com.example.connect.model.response.ErrorResponse
import com.example.connect.model.response.LoginResponse
import com.example.connect.network.adapter.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @GET("auth/public-key")
    suspend fun getPublicKey(): NetworkResponse<String, ErrorResponse>

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): NetworkResponse<LoginResponse, ErrorResponse>

    @POST("auth/request-otp")
    suspend fun requestOTP(@Body twoFARequest: TwoFARequest): NetworkResponse<LoginResponse, ErrorResponse>
}
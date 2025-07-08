package com.example.connect.service

import com.example.connect.domain.model.request.AuthRequest
import com.example.connect.domain.model.response.AuthResponse
import com.example.connect.domain.model.response.ErrorResponse
import com.example.connect.domain.model.response.PublicKeyResponse
import com.example.connect.network.adapter.NetworkResponse
import com.example.connect.utils.URL
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @GET(URL.Auth.GetPublicKey)
    suspend fun getPublicKey(): NetworkResponse<PublicKeyResponse, ErrorResponse>

    @POST(URL.Auth.RequestOtp)
    suspend fun requestOTP(@Body authRequest: AuthRequest): NetworkResponse<AuthResponse, ErrorResponse>

    @POST(URL.Auth.VerifyOtp)
    suspend fun verifyOTP(@Body authRequest: AuthRequest): NetworkResponse<AuthResponse, ErrorResponse>
}
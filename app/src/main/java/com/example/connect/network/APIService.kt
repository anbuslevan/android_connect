package com.example.connect.network

import com.example.connect.model.request.LoginRequest
import com.example.connect.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}
package com.example.connect.repository

import com.example.connect.model.request.LoginRequest
import com.example.connect.network.APIService
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: APIService){
    suspend fun login(username: String, email: String, password: String) = apiService.login(LoginRequest( username, email, password))
}
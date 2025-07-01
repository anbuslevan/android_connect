package com.example.connect.network.adapter

sealed class NetworkResponse<out T : Any, out U : Any?> {
    data class Success<T : Any>(val body: T?, val code: Int) : NetworkResponse<T, Nothing>()
    data class Error<U : Any>(val body: U, val code: Int) : NetworkResponse<Nothing, U>()
}
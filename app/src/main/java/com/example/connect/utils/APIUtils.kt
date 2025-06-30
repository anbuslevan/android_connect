package com.example.connect.utils

import com.example.connect.model.response.ErrorResponse
import com.google.gson.Gson
import okhttp3.ResponseBody

object APIUtils {
    fun parse(errorBody: String?): ErrorResponse? {
        if (!errorBody.isNullOrBlank()) {
            return Gson().fromJson(errorBody, ErrorResponse::class.java)
        }
        return null
    }
}
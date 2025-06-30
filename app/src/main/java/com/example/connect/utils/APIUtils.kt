package com.example.connect.utils

import com.example.connect.model.response.ErrorResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

object APIUtils {
    private val gson = GsonBuilder()
        .registerTypeAdapter(ErrorResponse::class.java, ErrorResponseDeserializer())
        .create()

    fun parse(errorBody: String?): ErrorResponse? {
        if (!errorBody.isNullOrBlank()) {
            return gson.fromJson(errorBody, ErrorResponse::class.java)
        }
        return null
    }
}

class ErrorResponseDeserializer: JsonDeserializer<ErrorResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ErrorResponse {
        val jsonObject = json?.asJsonObject

        val errorElement = jsonObject?.get("error")
        val errorArray = mutableListOf<String>()

        if (errorElement != null ) {
            if(errorElement.isJsonArray) {
                for (element in errorElement.asJsonArray) {
                    errorArray.add(element.asString)
                }
            } else if(errorElement.isJsonPrimitive) {
                errorArray.add(errorElement.asString)
            }
        }

        return ErrorResponse(errorArray.toTypedArray())
    }
}
package com.example.connect.domain.model.response

class ErrorResponse (
    val error: Array<String>?,
    val code: String?
) {
    override fun toString(): String {
        return "ErrorResponse(error=${error.contentToString()})"
    }
}
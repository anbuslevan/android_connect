package com.example.connect.network.adapter

import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response

internal class NetworkResponseCall<S: Any, E: Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>
): Call<NetworkResponse<S, E>>{
    override fun enqueue(callback: Callback<NetworkResponse<S, E>>) {
        return delegate.enqueue(object: Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if(response.isSuccessful){
                    callback.onResponse(
                        this@NetworkResponseCall,
                        Response.success(NetworkResponse.Success(body, code))
                    )
                } else {
                    val errorBody: E? = try {
                        error?.let { errorConverter.convert(it) }
                    } catch (e: Exception) {
                        null
                    }
                    if (errorBody != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Error(errorBody, code))
                        )
                    } else {
                        // Optional: you can define another subclass like NetworkResponse.UnknownError if needed
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Error(
                                body = errorConverter.convert("{}".toResponseBody(null))!!,
                                code = code
                            ))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                callback.onFailure(this@NetworkResponseCall, throwable)
            }
        })
    }

    override fun clone(): Call<NetworkResponse<S, E>> = NetworkResponseCall(delegate.clone(), errorConverter)
    override fun execute(): Response<NetworkResponse<S, E>> = throw UnsupportedOperationException("APIAdapter doesn't support execute")
    override fun isExecuted(): Boolean = delegate.isExecuted
    override fun cancel() = delegate.cancel()
    override fun isCanceled(): Boolean = delegate.isCanceled
    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()
}
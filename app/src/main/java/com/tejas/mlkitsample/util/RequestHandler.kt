package com.tejas.mlkitsample.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException

abstract class RequestHandler {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): ApiResponse {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                ApiResponse(response.body()!!)
            } else {
                if (response.errorBody() != null) {
                    try {
                        ApiResponse(
                            Gson().fromJson(
                                response.errorBody()!!.charStream(),
                                object : TypeToken<ErrorBody>() {}.type
                            )
                        )
                    } catch (ex: Exception) {
                        ApiResponse(
                            errorBody = ErrorBody(
                                response.code(),
                                response.message(),
                                response.errorBody().toString()
                            )
                        )
                    }
                } else {
                    ApiResponse(
                        ErrorBody(
                            100, "No Error Body Found"
                        )
                    )
                }
            }
        } catch (ex: Exception) {
            val errorBody: ErrorBody = when (ex) {
                is UnknownHostException, is ConnectException ->
                    ErrorBody(100, "Please, check your internet connection.")
                else -> ErrorBody(400, ex.message.toString())
            }
            ApiResponse(errorBody)
        }
    }
}

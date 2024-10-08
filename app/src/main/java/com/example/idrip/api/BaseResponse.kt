package com.example.idrip.api

sealed class BaseResponse<T>(val data : T? = null , val message : String? = null) {

    class Success<T>(data: T?) : BaseResponse<T>(data)

    class Error<T>(data: T?, message: String?) : BaseResponse<T>(data, message)

    class Loading<T>() : BaseResponse<T>()


}
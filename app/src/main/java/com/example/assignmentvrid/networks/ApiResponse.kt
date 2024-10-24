package com.example.assignmentvrid.networks

sealed class ApiResponse<T>(
    val data: T?=null,
    val message: String?=null
) {
    class ApiResponseSuccess<T>(data: T) : ApiResponse<T>(data)
    class ApiResponseError<T>(message: String, data: T?=null) : ApiResponse<T>(data, message)
    class ApiResponseLoading<T> : ApiResponse<T>()


}
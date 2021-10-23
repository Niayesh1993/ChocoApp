package com.example.choco.utils

sealed class ApiResult<out T> {
    data class Success<out T>(val value: T) : ApiResult<T>()
    data class Error(val error: ApiError? = null) : ApiResult<Nothing>()
    object NetworkError : ApiResult<Nothing>()
}
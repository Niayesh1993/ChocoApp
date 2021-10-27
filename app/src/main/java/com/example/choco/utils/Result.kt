package com.example.choco.utils

sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Error(val error: ApiError? = null) : Result<Nothing>()
    object NetworkError : Result<Nothing>()
}
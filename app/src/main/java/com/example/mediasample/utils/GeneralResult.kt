package com.example.mediasample.utils

sealed class GeneralResult {

    data class Progress(val loading: Boolean = true) : GeneralResult()

    internal data class Error(val error: String) : GeneralResult()

    internal data class SuccessGeneric<T>(val data: T) : GeneralResult()
}
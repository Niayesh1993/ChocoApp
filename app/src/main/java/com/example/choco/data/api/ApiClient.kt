package com.example.choco.data.api

import com.example.choco.BuildConfig
import com.example.choco.data.api.StatusCode.AUTHORIZATOIN_TIMEOUT
import com.example.choco.data.api.StatusCode.UNAUTHORIZED
import com.example.choco.utils.ApiError
import com.example.choco.utils.ApiErrorResponse
import com.example.choco.utils.ApiResult
import com.example.choco.utils.JsonHelper
import java.io.IOException
import retrofit2.HttpException


suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        Result.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> Result.NetworkError
            is HttpException -> {
                val code = throwable.code()
                var errorResponse: ApiError? = convertErrorBody(throwable)

                if (errorResponse == null) {
                    errorResponse = ApiError()
                    errorResponse.text = StatusCode.messageText(code)
                    errorResponse.widget = ApiError.Widget.DIALOG_BOX
                }
                errorResponse.code = code
                Result.Error(errorResponse)
            }
            else -> {
                Result.Error(null)
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ApiError? {
    try {
        throwable.response()?.errorBody()?.string().let {
            return JsonHelper.createFromJson(it.toString(), ApiErrorResponse::class.java)?.messages?.get(0)
        }
    } catch (exception: Exception) {
    }
    return ApiError()
}

fun ApiError?.unAuthorized(): Boolean {
    return StatusCode.messageText(UNAUTHORIZED) == this?.text ||
            StatusCode.messageText(AUTHORIZATOIN_TIMEOUT) == this?.text
}

object StatusCode {

    const val SUCCESS = 200
    const val BAD_REQUEST = 400
    const val UNAUTHORIZED = 401
    const val FORBIDDEN = 403
    const val NOT_FOUND = 404
    const val METHOD_NOT_ALLOWED = 405
    const val NOT_ACCEPTABLE = 406
    const val TIMEOUT = 408
    const val AUTHORIZATOIN_TIMEOUT = 419
    const val UPDATE_REQUIRED = 428
    const val INTERNAL_SERVER_ERROR = 500
    const val BAD_GATEWAY = 502
    const val SERVICE_UNAVAILABLE = 503
    const val GATEWAY_TIMEOUT = 504
    const val INSUFFICIENT_STORAGE = 507
    const val NO_INTERNET_ACCESS = 990


    fun messageText(statusCode: Int): String {
        return ""
//        return when (statusCode) {
//            BAD_REQUEST -> R.string.bad_request.stringRes()
//            UNAUTHORIZED -> R.string.unauthorized.stringRes()
//            FORBIDDEN -> R.string.forbidden.stringRes()
//            NOT_FOUND -> R.string.not_found.stringRes()
//            METHOD_NOT_ALLOWED -> R.string.method_not_allowed.stringRes()
//            NOT_ACCEPTABLE -> R.string.not_acceptable.stringRes()
//            TIMEOUT -> R.string.timeout.stringRes()
//            UPDATE_REQUIRED -> R.string.update_required.stringRes()
//            INTERNAL_SERVER_ERROR, BAD_GATEWAY, SERVICE_UNAVAILABLE, GATEWAY_TIMEOUT, INSUFFICIENT_STORAGE -> R.string.server_error.stringRes()
//            NO_INTERNET_ACCESS -> R.string.no_internet_access.stringRes()
//            AUTHORIZATOIN_TIMEOUT -> R.string.authorization_timeout.stringRes()
//            else -> R.string.default_exception_msg.stringRes()
//        }
    }
}

const val API_BASE_URL = BuildConfig.BASE_URL
const val AUTH_BASE_URL = BuildConfig.BASE_URL
const val IMAGE_BASE_URL = BuildConfig.BASE_URL
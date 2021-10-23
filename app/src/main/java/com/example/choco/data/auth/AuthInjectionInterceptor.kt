package com.example.choco.data.auth

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Interceptor to push the authorization header into requests.
 */
class AuthInjectionInterceptor @Inject constructor(
//    private val authPreferences: AppPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authenticatedRequest = chain.request()
            .newBuilder()
            .addAuthHeader("authPreferences.accessToken")
            .build()

        return chain.proceed(authenticatedRequest)
    }
}
package com.example.choco.data.auth

import com.example.choco.utils.Constants
import com.example.choco.utils.SettingManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Interceptor to push the authorization header into requests.
 */
class AuthInjectionInterceptor @Inject constructor(

) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authenticatedRequest = chain.request()
            .newBuilder()
            .addAuthHeader(SettingManager.getString(Constants().ACCESS_TOKEN)!!)
            .build()

        return chain.proceed(authenticatedRequest)
    }
}
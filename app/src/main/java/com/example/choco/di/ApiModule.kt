package com.example.choco.di

import com.example.choco.data.api.API_BASE_URL
import com.example.choco.data.api.ApiService
import com.example.choco.data.api.StatusCode.SUCCESS
import com.example.choco.data.auth.AuthInjectionInterceptor
import com.example.choco.data.auth.HttpLoggingInterceptor
import com.example.choco.utils.Helper
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideApiService(client: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }


    @Provides
    @Reusable
    fun provideOkHttpClient(
        authInjection: AuthInjectionInterceptor
    ): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(interceptor)
            .addInterceptor(emptyBodyInterceptor)
            .addInterceptor(authInjection)
            .retryOnConnectionFailure(true)
            .followRedirects(false)
            .followSslRedirects(false)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .also {
                if (Helper.isDebug) {
                    val httpLoggingInterceptor = HttpLoggingInterceptor()
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    it.addInterceptor(httpLoggingInterceptor)
                }
            }
            .build()
    }

    /**
     * Interceptor for okHttp that doesn't fail when the server responds with a content length of zero
     */
    private val emptyBodyInterceptor = object : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val response = chain.proceed(chain.request())
            if (response.isSuccessful.not() ||
                response.code().let { it != 204 && it != 205 && it !=201 }) {
                return response
            }
            return response
                .newBuilder()
                .code(SUCCESS)
                .also {
                    if ((response.body()?.contentLength() ?: -1) < 0 ||
                        response.body()?.string().isNullOrBlank()) {
                        val mediaType = MediaType.parse("text/plain")
                        val emptyBody = ResponseBody.create(mediaType, "{}")
                        it.body(emptyBody)
                    }
                }
                .build()
        }
    }

    companion object {
        const val TIMEOUT_SECONDS: Long = 100
    }
}
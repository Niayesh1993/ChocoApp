package com.example.choco.data.repository

import com.example.choco.data.model.Product
import com.example.choco.utils.Result

class MainRepository(private val remoteDataSource: DataSource) {


    suspend fun getProducts(token: String): Result<ArrayList<Product>> {
        return remoteDataSource.getProduct(token)
    }

    private fun saveCookie(cookieResult: ApiResult<String>): ApiResult<String> {
        if (cookieResult is ApiResult.Success) {
            saveTokens(cookieResult.value)
        }
        return cookieResult
    }

    private fun saveTokens(token: String) {

//        appPreferences.apply {
//            accessToken = token
//        }
    }

    companion object {
        @Volatile
        private var INSTANCE: MainRepository? = null

        fun getInstance(remoteDataSource: DataSource): MainRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: MainRepository(remoteDataSource
                    ).also { INSTANCE = it }
            }
        }
    }
}
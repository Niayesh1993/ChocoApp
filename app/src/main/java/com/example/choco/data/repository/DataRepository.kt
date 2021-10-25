package com.example.choco.data.repository

import com.example.choco.data.model.Login
import com.example.choco.data.model.LoginResult
import com.example.choco.data.model.Product
import com.example.choco.utils.ApiResult

class DataRepository(private val remoteDataSource: DataSource) {

    suspend fun login(login: Login): ApiResult<LoginResult> {
        return remoteDataSource.login(login)
    }

    suspend fun getProducts(token: String): ApiResult<ArrayList<Product>> {
        return remoteDataSource.getProduct(token)
    }

    companion object {
        @Volatile
        private var INSTANCE: DataRepository? = null

        fun getInstance(remoteDataSource: DataSource): DataRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: DataRepository(remoteDataSource
                    ).also { INSTANCE = it }
            }
        }
    }
}
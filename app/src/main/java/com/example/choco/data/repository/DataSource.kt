package com.example.choco.data.repository

import com.example.choco.data.api.ApiService
import com.example.choco.data.api.safeApiCall
import com.example.choco.data.model.Login
import com.example.choco.data.model.LoginResult
import com.example.choco.data.model.Product
import com.example.choco.utils.ApiResult
import javax.inject.Inject

class DataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun login(login: Login): ApiResult<LoginResult> {
        return safeApiCall {
            apiService.login(login)
        }
    }

    suspend fun getProduct(token: String): ApiResult<ArrayList<Product>> {
        return safeApiCall {
            apiService.getProducts(token)
        }
    }

}
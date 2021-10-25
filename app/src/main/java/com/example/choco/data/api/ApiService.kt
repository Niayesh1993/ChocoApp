package com.example.choco.data.api

import com.example.choco.data.model.Login
import com.example.choco.data.model.LoginResult
import com.example.choco.data.model.Product
import retrofit2.http.*

interface ApiService {

    /**
     * Performs a login request with the API.
     */

    @POST("/choco/login")
    suspend fun login(@Body request: Login): LoginResult



    @GET("/choco/products")
    suspend fun getProducts(@Query("token") token: String): ArrayList<Product>
}
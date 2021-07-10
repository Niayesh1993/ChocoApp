package com.example.mediasample.data.repository

import android.content.Context
import com.example.mediasample.data.model.Product
import com.example.mediasample.data.model.Result


interface MainActivityRepository {

    suspend fun getListOfProducts(context: Context, fileName: String): Result<List<Product>>
}
package com.example.mediasample.data.repository

import android.content.Context
import android.util.Log
import com.example.mediasample.data.model.Product
import com.example.mediasample.data.model.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.IOException

internal class MainActivityRepositoryImpl: MainActivityRepository {

    //parse json file as a list of objects
    override suspend fun getListOfProducts(context: Context, fileName: String): Result<List<Product>> = withContext(Dispatchers.IO){
        val jsonFileStringDeferred = async { getJsonDataFromAsset(context, fileName) }
        val jsonFileString = jsonFileStringDeferred.await()

        val gson = Gson()
        val listPersonType = object : TypeToken<List<Product>>() {}.type

        var products: List<Product> = gson.fromJson(jsonFileString, listPersonType)
        products.forEachIndexed { idx, person -> Log.i("data", "> Item $idx:\n$person") }

        return@withContext Result(products, null)
    }

    //read json file from assets folder
    fun getJsonDataFromAsset(context: Context, fileName: String): String? {

        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

}
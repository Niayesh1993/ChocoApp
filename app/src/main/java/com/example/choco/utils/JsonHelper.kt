package com.example.choco.utils

import com.google.gson.GsonBuilder
import java.lang.Exception
import java.lang.reflect.Modifier

object JsonHelper {
    private val GSON = GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
        .serializeNulls()
        .create()

    fun <T> createFromJson(string: String?, clazz: Class<T>?): T? {
        val `object`: T?
        `object` = try {
            GSON.fromJson(string, clazz)
        } catch (e: Exception) {
            null
        }
        return `object`
    }

}

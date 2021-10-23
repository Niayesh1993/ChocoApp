package com.example.choco.utils

import android.net.Uri
import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.lang.reflect.Modifier
import java.util.ArrayList

const val EMPTY_JSON = "{}"

object JsonHelper {
    private val GSON = GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
        .serializeNulls()
        .create()

    fun <T> toJson(`object`: T): String {
        try {
            return GSON.toJson(`object`)
        } catch (e: Exception) {

        }
        return EMPTY_JSON
    }

    fun <T> createJson(`object`: T): JSONObject {
        val jsonObj: JSONObject
        jsonObj = try {
            JSONObject(GSON.toJson(`object`))
        } catch (e: Exception) {
            e.printStackTrace()
            JSONObject()
        }
        return jsonObj
    }

    fun <T> createFromJson(string: String?, clazz: Class<T>?): T? {
        val `object`: T?
        `object` = try {
            GSON.fromJson(string, clazz)
        } catch (e: Exception) {
            null
        }
        return `object`
    }

    fun <T> createFromJson(json: JSONObject?, clazz: Class<T>?): T? {
        var json = json
        if (json == null) {
            json = JSONObject()
        }
        return createFromJson(json.toString(), clazz)
    }

    fun <T> createFromJson(array: JSONArray, clazz: Class<T>?): ArrayList<T> {
        return try {
            val list = ArrayList<T>()
            for (i in 0 until array.length()) {
                val item = createFromJson(array.getJSONObject(i), clazz)
                if (item != null) {
                    list.add(item)
                }
            }
            list
        } catch (e: Exception) {
            ArrayList(1)
        }
    }

    fun uriToJson(uri: Uri?): String {
        if (uri != null) {
            var encodedQuery = uri.encodedQuery
            if (!isEmpty(encodedQuery)) {
                encodedQuery = encodedQuery!!.replace("=".toRegex(), "\":\"")
                encodedQuery = encodedQuery.replace("&".toRegex(), "\",\"")
                return "{\"$encodedQuery\"}"
            }
        }
        return EMPTY_JSON
    }

    fun <T> listToString(targetList: Collection<T>?): String {
        return GSON.toJson(targetList, object : TypeToken<ArrayList<T>?>() {}.type)
    }

    fun <T> stringToList(stringArray: String?, clazz: Class<T>?): ArrayList<T> {
        return try {
            val jsonArray = JSONArray(stringArray)
            createFromJson(jsonArray, clazz)
        } catch (e: JSONException) {
            ArrayList()
        }
    }

    fun isEmpty(text: String?): Boolean {
        return (text == null || TextUtils.isEmpty(text)
                || isWhiteSpaces(text)
                || text.equals("null", ignoreCase = true))
    }

    private fun isWhiteSpaces(s: String?): Boolean {
        return s != null && s.matches("\\s+".toRegex())
    }

}

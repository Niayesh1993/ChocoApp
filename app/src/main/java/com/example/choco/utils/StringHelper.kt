package com.example.choco.utils

import android.annotation.SuppressLint
import java.lang.StringBuilder
import java.util.*

@SuppressLint("DefaultLocale")
object StringHelper {
    const val EMPTY = ""
    const val EMPTY_JSON = "{}"
    const val SEPARATOR = ","
    fun tomanFormatPrice(price: Long): String {
        var price = price
        var toamnFormatPrice: String
        val index: String
        if (price >= 0) {
            index = " "
        } else {
            price = price * -1
            index = "- "
        }
        toamnFormatPrice = String.format("%,d", price)
        toamnFormatPrice = toamnFormatPrice + index + "تومان"
        return toamnFormatPrice
    }



    fun stringToListNonNull(str: String, separator: String): List<String?> {
        var strings = stringToList(str, separator)
        if (strings == null) {
            strings = ArrayList()
        }
        return strings
    }

    fun stringToList(str: String, separator: String): List<String?>? {
        var list: List<String>? = null
        if (!str.contains(separator)) {
            if (!InputHelper.isEmpty(str)) {
                list = listOf(str)
            }
        } else {
            val strs = str.split(separator).toTypedArray()
            list = Arrays.asList(*strs)
        }
        return if (list == null) null else ArrayList(list)
    }

    fun listToString(list: List<String?>, separator: String): String {
        val stringBuilder = StringBuilder("")
        if (list.size == 0 || InputHelper.isEmpty(separator)) {
            return stringBuilder.toString()
        }
        for (i in list.indices) {
            stringBuilder.append(list[i])
            if (i != list.size - 1) {
                stringBuilder.append(separator)
            }
        }
        return stringBuilder.toString()
    }
}

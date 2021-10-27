package com.example.choco.data.model

import com.google.gson.annotations.SerializedName

class Product() {

    @SerializedName("Id")
    private val id: String = ""

    @SerializedName("name")
    private val name: String = ""

    @SerializedName("Description")
    private val description: String = ""

    @SerializedName("price")
    private val price: String = ""

    @SerializedName("photo")
    private val photo: String = ""

    private var order: Boolean = false

    fun getId(): String {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getDesc(): String {
        return description
    }

    fun getPrice(): String {
        return price
    }
    fun getPhoto(): String {
        return photo
    }

    fun getOrder(): Boolean {
        return order
    }

    fun setOrder(status: Boolean)
    {
        this.order = status
    }
}
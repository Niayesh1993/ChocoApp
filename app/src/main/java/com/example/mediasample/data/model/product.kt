package com.example.mediasample.data.model
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Product : Serializable {

    @SerializedName("title")
    @Expose
     var title: String = ""

    @SerializedName("author")
    @Expose
     var author: String = ""

    @SerializedName("imageURL")
    @Expose
     var imageURL: String = ""

    var favorite: Boolean = false

}
package com.example.choco.data.model
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LastProduct : Serializable {

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
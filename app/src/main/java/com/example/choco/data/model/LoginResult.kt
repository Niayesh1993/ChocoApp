package com.example.choco.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResult {
    @SerializedName("token")
    @Expose
    var token: String = ""

}
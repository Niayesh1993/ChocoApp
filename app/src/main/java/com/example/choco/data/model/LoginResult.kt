package com.example.choco.data.model

import com.google.gson.annotations.SerializedName

data class LoginResult(@SerializedName("token") val token: String = "") { }
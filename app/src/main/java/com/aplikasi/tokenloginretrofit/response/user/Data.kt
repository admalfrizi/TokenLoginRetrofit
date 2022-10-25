package com.aplikasi.tokenloginretrofit.response.user

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("access_token")
    val accessToken : String,

    @SerializedName("token_type")
    val tokenType: String,

    @SerializedName("user")
    val User: User,
)

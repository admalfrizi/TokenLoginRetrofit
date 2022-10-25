package com.aplikasi.tokenloginretrofit.response.user

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("meta")
    val meta: Meta,

    @SerializedName("data")
    val data: Data
)


package com.aplikasi.tokenloginretrofit.api

import com.aplikasi.tokenloginretrofit.constants.Constants
import com.aplikasi.tokenloginretrofit.request.LoginRequest
import com.aplikasi.tokenloginretrofit.request.RegisterRequest
import com.aplikasi.tokenloginretrofit.response.user.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST(Constants.LOGIN_URL)
    fun login(@Body request: LoginRequest): Call<UserResponse>

    @FormUrlEncoded
    @POST(Constants.REGISTER_URL)
    fun register(@Field("name") name: String,
                 @Field("email") email: String,
                 @Field("password") password : String
    ): Call<UserResponse>
}
package com.aplikasi.tokenloginretrofit.api

import android.content.Context
import com.aplikasi.tokenloginretrofit.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(context: Context): Interceptor {

    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        sessionManager.fetchToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }


}
package com.aplikasi.tokenloginretrofit

import android.content.Context
import android.content.SharedPreferences
import com.aplikasi.tokenloginretrofit.response.user.User
import com.google.gson.Gson

class SessionManager(context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val TOKEN = "token"
        const val USER = "user"

        const val LOGIN = "login"
    }

    fun setSession(status: Boolean){
        val editor = prefs.edit()
        editor.putBoolean(LOGIN, status).apply()
    }

    fun getStatusLogin(): Boolean {
        return prefs.getBoolean(LOGIN, false)
    }

    fun setUser(value: User){
        val data: String = Gson().toJson(value, User::class.java)
        prefs.edit().putString(USER, data).apply()
    }

    fun getUser(): User? {
        val data: String = prefs.getString(USER, null) ?: return null
        return Gson().fromJson(data, User::class.java)
    }

    fun saveToken(token: String){
        val editor = prefs.edit()
        editor.putString(TOKEN, token).apply()
    }

    fun fetchToken(): String? {
        return prefs.getString(TOKEN, null)
    }

    fun deleteToken() {
        val editor = prefs.edit()
        editor.clear().apply()
    }
}
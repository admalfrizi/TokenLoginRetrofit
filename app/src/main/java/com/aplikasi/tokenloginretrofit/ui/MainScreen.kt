package com.aplikasi.tokenloginretrofit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aplikasi.tokenloginretrofit.SessionManager
import com.aplikasi.tokenloginretrofit.databinding.ActivityMainScreenBinding


class MainScreen : AppCompatActivity() {

    private lateinit var binding: ActivityMainScreenBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sessionManager = SessionManager(this)

        val token = sessionManager.fetchToken()

        sessionManager.getStatusLogin()

        if(token != null) {
            binding.tvToken.text = token

            val user = sessionManager.getUser()!!
            binding.tvName.text = user.name
            binding.tvEmail.text = user.email

        } else {
            toLogin()
        }

        if(!sessionManager.getStatusLogin()){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogout.setOnClickListener {
            toLogin()
        }
    }

    private fun toLogin() {
        sessionManager.deleteToken()
        Intent(this, LoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }
}
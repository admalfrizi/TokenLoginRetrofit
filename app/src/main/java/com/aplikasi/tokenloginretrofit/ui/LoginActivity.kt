package com.aplikasi.tokenloginretrofit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.aplikasi.tokenloginretrofit.SessionManager
import com.aplikasi.tokenloginretrofit.api.ApiClient
import com.aplikasi.tokenloginretrofit.databinding.ActivityLoginBinding
import com.aplikasi.tokenloginretrofit.request.LoginRequest
import com.aplikasi.tokenloginretrofit.response.user.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityLoginBinding
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        binding.toSignup.setOnClickListener {
            Intent(this, RegisterActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        if(sessionManager.getStatusLogin()){
            startActivity(Intent(this, MainScreen::class.java))
        } else {
            binding.loginBtn.setOnClickListener {

                binding.ld.visibility = View.VISIBLE

                val email: String = binding.emailEdt.text.trim().toString()
                val password: String = binding.pwEdt.text.trim().toString()

                if (email.isEmpty() || password.isEmpty()) {
                    binding.ld.visibility = View.GONE
                    Toast.makeText(this, "Email Tidak Terdaftar Pada Sistem", Toast.LENGTH_LONG)
                        .show()
                } else {
                    apiClient.getApiService(this).login(LoginRequest(email, password))
                        .enqueue(object : Callback<UserResponse> {
                            override fun onResponse(
                                call: Call<UserResponse>,
                                response: Response<UserResponse>
                            ) {
                                binding.ld.visibility = View.GONE
                                val loginResponse = response.body()

                                if (loginResponse?.meta?.code == 200 && loginResponse.data.accessToken.isNotEmpty()) {
                                    sessionManager.saveToken(loginResponse.data.accessToken)
                                    sessionManager.setSession(true)
                                    sessionManager.setUser(loginResponse.data.User)
                                    Toast.makeText(
                                        applicationContext,
                                        loginResponse.meta.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                    toMain()
                                }
                            }

                            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                                binding.ld.visibility = View.GONE
                                Toast.makeText(
                                    applicationContext,
                                    "error : " + t.message,
                                    Toast.LENGTH_LONG
                                ).show()

                            }
                        })
                }
            }
        }
    }


    fun toMain() {
        Intent(applicationContext, MainScreen::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }

    }
}
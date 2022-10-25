package com.aplikasi.tokenloginretrofit.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.aplikasi.tokenloginretrofit.R
import com.aplikasi.tokenloginretrofit.api.ApiClient
import com.aplikasi.tokenloginretrofit.databinding.ActivityRegisterBinding
import com.aplikasi.tokenloginretrofit.request.RegisterRequest
import com.aplikasi.tokenloginretrofit.response.user.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toSignin.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        apiClient = ApiClient()

        binding.regBtn.setOnClickListener {
            register()

        }


    }

    private fun register() {
        binding.ld.visibility = View.VISIBLE

        val user : String = binding.userEdt.text.trim().toString()
        val email : String = binding.emailEdt.text.trim().toString()
        val pwd : String = binding.pwEdt.text.trim().toString()

        if(user.isEmpty() || email.isEmpty() || pwd.isEmpty()) {
            binding.ld.visibility = View.GONE

            Toast.makeText(this, "Semua Data Harus Di Isi", Toast.LENGTH_LONG).show()
        }


        apiClient.getApiService(this).register(user,email,pwd).enqueue(
            object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    binding.ld.visibility = View.GONE
                    val regResponse = response.body()!!

                    if(regResponse.meta.code == 200) {
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        toast()
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    binding.ld.visibility = View.GONE
                    Toast.makeText(applicationContext, "Error :" + t.message, Toast.LENGTH_LONG).show()
                    Log.d("TAG","error :" +t.message)
                }

            }
        )
    }

    fun toast() {
        Toast.makeText(this, "Data anda Telah Didaftarkan", Toast.LENGTH_LONG).show()
    }
}
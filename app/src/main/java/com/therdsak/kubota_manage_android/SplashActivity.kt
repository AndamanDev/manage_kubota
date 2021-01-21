package com.therdsak.kubota_manage_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.gson.Gson
import com.therdsak.kubota_manage_android.controller.KubotaPreference
import com.therdsak.kubota_manage_android.model.LoginResponse
import com.therdsak.kubota_manage_android.service.ServiceCreate
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    var kubotaPreference: KubotaPreference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        if (ServiceCreate.serviceKubota == null) {
            ServiceCreate.createKubotaService()
        }


        kubotaPreference = KubotaPreference(this)


        if (kubotaPreference?.getEmail("email").equals("") || kubotaPreference?.getEmail("email") == null){
            Handler().postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }else{
            val json = JSONObject()
            json.put("username", kubotaPreference?.getEmail("email"))
            json.put("password",  kubotaPreference?.getPassword("password"))

            val requestBody: RequestBody =
                RequestBody.create(MediaType.parse("application/json"), json.toString())
            callLogin(requestBody)
        }
    }



    private fun callLogin(request: RequestBody) {
        val callService = ServiceCreate.serviceKubota?.callLogin(request)
        callService?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.body()?.message.equals("ok")){
                    openMain(response.body()?.data?.access_token!!)
                    kubotaPreference?.saveEmail("email",edit_text_username.text.toString())
                    kubotaPreference?.savePassword("password",edit_text_password.text.toString())
                }else{
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

            }
        })


    }


    fun openMain(token : String){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("token",token)
        intent.putExtra("type","")
        startActivity(intent)
        finish()
    }
}

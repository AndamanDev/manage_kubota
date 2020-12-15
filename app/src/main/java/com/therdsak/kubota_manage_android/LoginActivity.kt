package com.therdsak.kubota_manage_android

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.therdsak.kubota_manage_android.model.LoginResponse
import com.therdsak.kubota_manage_android.service.ServiceCreate
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (ServiceCreate.serviceKubota == null) {
            ServiceCreate.createKubotaService()
        }

        button_sign_in.setOnClickListener {
            if (edit_text_username.text.toString().isEmpty()){
//                if (netsumanPreference?.getLang("ln").equals("th")){
//                    showDialog("เเจ้งปัญหา","กรุณากรอกชื่อผู้ใช้งาน")
//                }else{
//                    showDialog("Problem","Please Fill Username")
//                }
            }else if (edit_text_password.text.toString().isEmpty()) {
//                if (netsumanPreference?.getLang("ln").equals("th")){
//                    showDialog("เเจ้งปัญหา", "กรุณากรอกรหัสผ่าน")
//                }else{
//                    showDialog("Problem","Please Fill Password")
//                }
            } else{
                val json = JSONObject()
                json.put("username", edit_text_username.text.toString())
                json.put("password", edit_text_password.text.toString())

                val requestBody: RequestBody =
                    RequestBody.create(MediaType.parse("application/json"), json.toString())
                callLogin(requestBody)
            }

        }

        linear_container_login.setOnClickListener {
            it.hideKeyboard()
        }
    }



    private fun callLogin(request: RequestBody) {
       // linear_blur.visibility = View.VISIBLE
        val callService = ServiceCreate.serviceKubota?.callLogin(request)
        callService?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("!!!","test data : " + Gson().toJson(response.body()?.message))
//                linear_blur.visibility = View.GONE
                if (response.body()?.message.equals("ok")){
                    openMain(response.body()?.data?.access_token!!)
                }else{
//                    if (netsumanPreference?.getLang("ln").equals("th")){
//                        showDialog("เเจ้งปัญหา","ไม่มีผู้ใช้งานในระบบ")
//                    }else{
//                        showDialog("Problem","Data not correct")
//                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                linear_blur.visibility = View.GONE
//                if (netsumanPreference?.getLang("ln").equals("th")){
//                    showDialog("เเจ้งปัญหา","ไม่มีผู้ใช้งานในระบบ")
//                }else{
//                    showDialog("Problem","Data not correct")
//                }

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


    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }



}
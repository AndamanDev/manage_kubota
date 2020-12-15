package com.therdsak.kubota_manage_android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.therdsak.kubota_manage_android.model.VisitorContactResponse
import com.therdsak.kubota_manage_android.model.VisitorListDataResponse
import com.therdsak.kubota_manage_android.model.VisitorResponse
import com.therdsak.kubota_manage_android.service.ServiceCreate
import kotlinx.android.synthetic.main.activity_contact_detail.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactDetailActivity : AppCompatActivity() {

    var visitor_ids : Int = 0
    var code: String? = ""
    var type: String? = ""
    var token: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)


        visitor_ids = intent.getIntExtra("visitor_ids",0)
        code = intent.getStringExtra("code")
        type = intent.getStringExtra("type")
        token = intent.getStringExtra("token")


        callDetailVisitor(token!!, code!! , visitor_ids)

        button_back.setOnClickListener {
            val intent = Intent(this, ContactActivity::class.java)
            intent.putExtra("code",code)
            intent.putExtra("type",type)
            intent.putExtra("token",token)
            startActivity(intent)
           // finish()
        }
    }


    fun callDetailVisitor(authorization : String , visitor_card_id : String ,visitor_ids : Int ) {
        val callService = ServiceCreate.serviceKubota?.callGetUserContact("Bearer $authorization", visitor_card_id,visitor_ids)
        callService?.enqueue(object : Callback<VisitorContactResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<VisitorContactResponse>, response: Response<VisitorContactResponse>) {
                if (response.body()?.message.equals("ok")){
                    text_view_type.text = response.body()?.data!!.visitor_type_desc
                    text_view_name.text = "ชื่อผู้ติดต่อ : " + response.body()?.data!!.visitor_nm
                    text_view_company.text = "บริษัท : " + response.body()?.data!!.visitor_company
                    text_view_phone.text = "เบอร์โทรศัพท์ : " + response.body()?.data!!.visitor_phone
                    text_view_address.text = "ที่อยู่ : " + response.body()?.data!!.visitor_address
                    image_view_visitor.visibility = View.VISIBLE
                    linear_no_data.visibility = View.GONE
                    setImage(response.body()?.data!!.visitor_pic_online!!)
                }else{
                    image_view_visitor.visibility = View.GONE
                    linear_no_data.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<VisitorContactResponse>, t: Throwable) {
                image_view_visitor.visibility = View.GONE
                linear_no_data.visibility = View.VISIBLE

            }
        })


    }


    fun setImage(image : String){
        Glide
            .with(this)
            .load(image)
            .into(image_view_visitor)
    }

    override fun onBackPressed() {
        // super.onBackPressed()

    }
}
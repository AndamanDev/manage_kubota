package com.therdsak.kubota_manage_android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_vertify.*

class VerifyActivity : AppCompatActivity() {
    var type : String = ""
    var message : String = ""
    var token : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vertify)
        type = intent.getStringExtra("type")
        message = intent.getStringExtra("message")
        token = intent.getStringExtra("token")



        if (type == "in"){
            text_view_name.text = "บันทึกการติดต่อ"
        }else{
            text_view_name.text = "บันทึกการออกงาน"
        }

        if (message == "ผ่าน"){
            image_status.background = resources.getDrawable(R.drawable.check)
            text_view_status.text = "บันทึกข้อมูลสำเร็จ"
        }else if (message == "ไม่พบข้อมูล"){
            image_status.background = resources.getDrawable(R.drawable.close_red)
            text_view_status.text = "ไม่พบข้อมูล"
        } else{
            image_status.background = resources.getDrawable(R.drawable.cancel)
            text_view_status.text = "ไม่มีสิทธิ์บันทึกข้อมูล"
        }


        button_submit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("token",token)
            intent.putExtra("type",type)
            startActivity(intent)
        //    finish()
        }
    }


    override fun onBackPressed() {
       // super.onBackPressed()

    }
}
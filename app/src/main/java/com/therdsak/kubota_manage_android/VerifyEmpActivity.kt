package com.therdsak.kubota_manage_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_vertify_emp.*
import kotlinx.android.synthetic.main.activity_vertify_emp.text_view_name

class VerifyEmpActivity : AppCompatActivity() {
    var type : String = ""
    var type_data : String? = ""
    var message : String = ""
    var token : String = ""
    var name : String? = ""
    var department : String? = ""
    var position : String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vertify_emp)
        type = intent.getStringExtra("type")!!
        token = intent.getStringExtra("token")!!
        name = intent.getStringExtra("name")
        position = intent.getStringExtra("position")
        type_data = intent.getStringExtra("type_data")
        department = intent.getStringExtra("department")




        text_view_status.text = "[" + type_data +  "]"
        text_view_emp_position.text = "ส่วนงาน : " + position
        text_view_emp_name.text = "ชื่อลงบันทึก : " + name
        text_view_emp_division.text = "เเผนก : " + department




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
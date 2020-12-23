package com.therdsak.kubota_manage_android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_data_complete.*
import kotlinx.android.synthetic.main.activity_data_complete.text_view_emp_division
import kotlinx.android.synthetic.main.activity_data_complete.text_view_emp_name
import kotlinx.android.synthetic.main.activity_data_complete.text_view_emp_position
import kotlinx.android.synthetic.main.activity_vertify.*

class DataCompleteActivity : AppCompatActivity() {
    var name : String = ""
    var department : String = ""
    var division : String = ""
    var date : String = ""
    var time : String = ""
    var token : String = ""
    var type : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_complete)

        name =  intent.getStringExtra("name")!!
        department =  intent.getStringExtra("department")!!
        division =  intent.getStringExtra("division")!!
        date =  intent.getStringExtra("date")!!
        time =  intent.getStringExtra("time")!!
        token =  intent.getStringExtra("token")!!
        type =  intent.getStringExtra("type")!!


        text_view_emp_name.text = "ชื่อพนักงาน : " + name
        text_view_emp_position.text = "เเผนกบริษัท : " + department
        text_view_emp_division.text = "ส่วนงาน : " + division
        text_view_sec_date.text = "วันที่ : " + date
        text_view_sec_time.text = "เวลา : " + time


        button_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("token",token)
            intent.putExtra("type",type)
            startActivity(intent)
        }
    }


    override fun onBackPressed() {
       // super.onBackPressed()

    }
}
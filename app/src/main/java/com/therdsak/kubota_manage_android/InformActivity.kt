package com.therdsak.kubota_manage_android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.therdsak.kubota_manage_android.model.EmpResponse
import com.therdsak.kubota_manage_android.model.MessageResponse
import com.therdsak.kubota_manage_android.model.SecResponse
import com.therdsak.kubota_manage_android.model.VisitorResponse
import com.therdsak.kubota_manage_android.service.ServiceCreate
import kotlinx.android.synthetic.main.activity_inform.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformActivity : AppCompatActivity() {

    var type: String? = ""
    var token: String? = ""
    var em_id: String? = ""
    var code: String? = ""
    var visit_tran_ids: Int? = 0
    var id_result : String? = ""
    var position : String? = ""
    var name : String? = ""
    var type_data : String? = ""
    var department : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inform)


        type = intent.getStringExtra("type")
        token = intent.getStringExtra("token")
        code = intent.getStringExtra("code")
        em_id = intent.getStringExtra("em_id")
        position = intent.getStringExtra("position")
        name = intent.getStringExtra("name")
        type_data = intent.getStringExtra("type_data")
        department = intent.getStringExtra("department")
        visit_tran_ids = intent.getIntExtra("visit_tran_ids",0)


        Log.d("!!!","visit_tran_ids : " + visit_tran_ids)
        Log.d("!!!","code : " + code)


        if (type == "in"){
            button_condition.text = "บันทึกการติดต่อ"
            linear_emp.visibility = View.VISIBLE
            linear_sec.visibility = View.GONE
            callEmp(token!!,code!!)
        }else{
            button_condition.text = "บันทึกการออก"
            linear_emp.visibility = View.GONE
            linear_sec.visibility = View.VISIBLE
            callSec(token!!,code!!)
        }

        button_back.setOnClickListener {
            if (type == "in"){
                val intent = Intent(this, ScanEmpActivity::class.java)
                intent.putExtra("type",type)
                intent.putExtra("token",token)
                intent.putExtra("visit_tran_ids",visit_tran_ids!!)
                intent.putExtra("em_id",em_id)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, ScanSecActivity::class.java)
                intent.putExtra("type",type)
                intent.putExtra("token",token)
                intent.putExtra("visit_tran_ids",visit_tran_ids!!)
                intent.putExtra("em_id",em_id)
                startActivity(intent)
                finish()
            }
        }
    }


    fun callEmp(authorization : String , code : String) {
        val callService = ServiceCreate.serviceKubota?.callGetEmployee("Bearer $authorization", code)
        callService?.enqueue(object : Callback<EmpResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<EmpResponse>, response: Response<EmpResponse>) {
                Log.d("!!!","come in emp  : " + response.body()?.message)
                linear_data.visibility = View.VISIBLE
                if (response.body()?.message.equals("ok")){



                        text_view_emp_name.text = "ชื่อพนักงาน : " + response.body()!!.data!!.em_nm
                        text_view_emp_position.text = "เเผนกบริษัท : " + response.body()!!.data!!.department_desc
                        text_view_emp_division.text = "ส่วนงาน : " + response.body()!!.data!!.division_desc

                        id_result = response.body()!!.data!!.em_id
                        button_submit.setOnClickListener {
                            callCheck(token!!,visit_tran_ids.toString(), code)
                        }





                }else{
                }
            }

            override fun onFailure(call: Call<EmpResponse>, t: Throwable) {
                openVerify(type!!  , "ไม่พบข้อมูล" ,authorization )
            }
        })


    }



    fun callSec(authorization : String , code : String) {
        val callService = ServiceCreate.serviceKubota?.callGetSec("Bearer $authorization", code)
        callService?.enqueue(object : Callback<SecResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<SecResponse>, response: Response<SecResponse>) {
                linear_data.visibility = View.VISIBLE
                if (response.body()?.message.equals("ok")){
                    text_view_sec_name.text = "ชื่อพนักงาน : " + response.body()!!.data!!.sec_nm

                    id_result = response.body()!!.data!!.sec_rfid_id
                    button_submit.setOnClickListener {
                        callCheck(token!!,visit_tran_ids.toString(), code)
                    }
                }else{
                }
            }

            override fun onFailure(call: Call<SecResponse>, t: Throwable) {
                openVerify(type!!  , "ไม่พบข้อมูล" ,authorization )
            }
        })


    }



    fun callCheck(authorization : String , visit_tran_ids : String , em_id : String ) {
        val callService = ServiceCreate.serviceKubota?.callCheckvisit("Bearer $authorization", visit_tran_ids , em_id)
        callService?.enqueue(object : Callback<MessageResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                Log.d("!!!","come in emp  : " + response.body()?.data)
                if (response.body()?.message.equals("ok")){

                    if (response.body()?.data!!.message == "ผ่าน"){
                        if (type == "in"){
                            callUpdateVisitTran(authorization ,visit_tran_ids , id_result!!)
                        }else {
                            callUpdateVisitOut(authorization, visit_tran_ids, id_result!!)
                        }
                    }else{
                        openVerify(type!!  , "ไม่ผ่าน" ,authorization )
                    }



                }else{
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Log.d("!!!","t : " + t.message)
            }
        })


    }






    fun callUpdateVisitTran(authorization : String , visit_tran_ids : String , em_id : String ) {
        val callService = ServiceCreate.serviceKubota?.callupdateVisitTran("Bearer $authorization", visit_tran_ids , em_id)
        callService?.enqueue(object : Callback<MessageResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                Log.d("!!!","come in emp  : " + response.body()?.data)
                if (response.body()?.message.equals("ok")){

                    openVerify(type!!  , "ผ่าน",authorization)

                }else{
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Log.d("!!!","t : " + t.message)
            }
        })


    }


    fun callUpdateVisitOut(authorization : String , visit_tran_ids : String , sec_eid : String ) {
        val callService = ServiceCreate.serviceKubota?.callupdateVisitOut("Bearer $authorization", visit_tran_ids , sec_eid)
        callService?.enqueue(object : Callback<MessageResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                Log.d("!!!","come in emp  : " + response.body()?.data)
                if (response.body()?.message.equals("ok")){

                    openVerify(type!!  , "ผ่าน",authorization)

                }else{
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Log.d("!!!","t : " + t.message)
            }
        })


    }


    fun openVerify(type : String , message : String , token : String  ){
        val intent = Intent(this, VerifyEmpActivity::class.java)
        intent.putExtra("type",type)
        intent.putExtra("message",message)
        intent.putExtra("token",token)
        intent.putExtra("position",position)
        intent.putExtra("name",name)
        intent.putExtra("type_data",type_data)
        intent.putExtra("department",department)
        startActivity(intent)
    }

    override fun onBackPressed() {
        // super.onBackPressed()

    }
}
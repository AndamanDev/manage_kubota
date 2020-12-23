package com.therdsak.kubota_manage_android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.therdsak.kubota_manage_android.adapter.ItemPersonAdapter
import com.therdsak.kubota_manage_android.model.LoginResponse
import com.therdsak.kubota_manage_android.model.VisitorDataResponse
import com.therdsak.kubota_manage_android.model.VisitorListDataResponse
import com.therdsak.kubota_manage_android.model.VisitorResponse
import com.therdsak.kubota_manage_android.service.ServiceCreate
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.android.synthetic.main.fragment_contact.button_next
import kotlinx.android.synthetic.main.fragment_contact.recycler_view
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactActivity : AppCompatActivity() {
    var personAdapter: ItemPersonAdapter? = null
    var code: String? = ""
    var type: String? = ""
    var token: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        code = intent.getStringExtra("code")
        type = intent.getStringExtra("type")
        token = intent.getStringExtra("token")


        callVisitor(token!! , code!!)


        button_cancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("token",token)
            intent.putExtra("type",type)
            startActivity(intent)
          //  finish()
        }



      //
    }


     fun callVisitor(authorization : String , visitor_card_id : String) {
        val callService = ServiceCreate.serviceKubota?.callGetvisitorContact("Bearer $authorization", visitor_card_id)
        callService?.enqueue(object : Callback<VisitorResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<VisitorResponse>, response: Response<VisitorResponse>) {
                linear_data.visibility = View.VISIBLE
                if (response.body()?.message.equals("ok")){

                      if (response.body()?.data!!.visit_tran_status_id == 1){
                          text_view_position.text = "ส่วนงาน : " + response.body()?.data!!.position
                          text_view_name.text = "ชื่อพนักงาน : " + response.body()?.data!!.em_nm
                          text_view_type.text = "ประเภทการติดต่อ : " + response.body()?.data!!.visit_type_desc
                          setRecyclerView(response.body()?.data!!.visitors!!)
//                          var name = ""
//                          var position = ""
//                          var visit_type_desc = ""
//                          var workunit_desc = ""
//                          if (response.body()?.data!!.em_nm == null){
//                              name = ""
//                          }else{
//                              name = response.body()?.data!!.em_nm
//                          }
//
//                          if (response.body()?.data!!.position == null){
//                              position = ""
//                          }else{
//                              position = response.body()?.data!!.position
//                          }
//
//                          if (response.body()?.data!!.visit_type_desc == null){
//                              visit_type_desc = ""
//                          }else{
//                              visit_type_desc = response.body()?.data!!.visit_type_desc
//                          }
//
//                          if (response.body()?.data!!.workunit_desc == null){
//                              workunit_desc = ""
//                          }else{
//                              workunit_desc = response.body()?.data!!.workunit_desc
//                          }
                          openScan(response.body()?.data!!.visit_tran_ids!! ,response.body()?.data!!.em_id,
                              response.body()?.data!!.position,
                              response.body()?.data!!.em_nm,
                              response.body()?.data!!.visit_type_desc,
                              response.body()?.data!!.workunit_desc)
                      }else{
                          openDetail("",token!! , response.body()?.data!!.em_nm!!,
                              response.body()?.data!!.workunit_desc!!,
                              response.body()?.data!!.position!! ,
                              response.body()?.data!!.visit_in_date!!.substring(0,10) ,
                              response.body()?.data!!.visit_in_date!!.substring(11,19))
                      }



                }else{

                }
            }

            override fun onFailure(call: Call<VisitorResponse>, t: Throwable) {
                    openVerify()

            }
        })


    }


    fun setRecyclerView(list :  List<VisitorListDataResponse>) {
        personAdapter =
            ItemPersonAdapter(list)
            { partItem: VisitorListDataResponse ->
                partItemClicked(partItem)
            }

        try {
            recycler_view.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                isNestedScrollingEnabled = false
                adapter = personAdapter
                onFlingListener = null
            }
        }catch (e : Exception){

        }
    }


    private fun openScan(visit_tran_ids : Int , em_id : String? , position : String? , name : String? , type_data : String? , department: String? ){
        button_next.setOnClickListener {
            if (type == "in"){
                val intent = Intent(this, ScanEmpActivity::class.java)
                intent.putExtra("type",type)
                intent.putExtra("token",token)
                intent.putExtra("visit_tran_ids",visit_tran_ids)
                intent.putExtra("em_id",em_id)
                intent.putExtra("position",position)
                intent.putExtra("name",name)
                intent.putExtra("type_data",type_data)
                intent.putExtra("department",department)
                startActivity(intent)
            }else{
                val intent = Intent(this, ScanSecActivity::class.java)
                intent.putExtra("type",type)
                intent.putExtra("token",token)
                intent.putExtra("visit_tran_ids",visit_tran_ids)
                intent.putExtra("em_id",em_id)
                startActivity(intent)

            }
        }
    }



    private fun openDetail(type : String , token : String , name : String ,department :  String , division  :String , date : String , time : String  ){
        val intent = Intent(this, DataCompleteActivity::class.java)
        intent.putExtra("type",type)
        intent.putExtra("token",token)
        intent.putExtra("name",name)
        intent.putExtra("department",department)
        intent.putExtra("division",division)
        intent.putExtra("date",date)
        intent.putExtra("time",time)
        startActivity(intent)
    }


    private fun openVerify(){
        val intent = Intent(this, VerifyActivity::class.java)
        intent.putExtra("type",type)
        intent.putExtra("message","ไม่พบข้อมูล")
        intent.putExtra("token",token)
        startActivity(intent)
    }

    private fun partItemClicked(data : VisitorListDataResponse) {
        val intent = Intent(this, ContactDetailActivity::class.java)
        intent.putExtra("visitor_ids" , data.visitor_ids)
        intent.putExtra("code" ,code)
        intent.putExtra("token" , token)
        intent.putExtra("type" , type)
        startActivity(intent)
      //  finish()
    }


    override fun onBackPressed() {
        // super.onBackPressed()

    }



}
package com.therdsak.kubota_manage_android

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.therdsak.kubota_manage_android.adapter.ItemPersonAdapter
import com.therdsak.kubota_manage_android.model.LoginResponse
import com.therdsak.kubota_manage_android.model.VisitorDataResponse
import com.therdsak.kubota_manage_android.model.VisitorListDataResponse
import com.therdsak.kubota_manage_android.model.VisitorResponse
import com.therdsak.kubota_manage_android.service.ServiceCreate
import com.vanstone.trans.api.PiccApi
import com.vanstone.utils.CommonConvert
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.android.synthetic.main.fragment_contact.button_next
import kotlinx.android.synthetic.main.fragment_contact.recycler_view
import kotlinx.android.synthetic.main.fragment_scan.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScanEmpActivity : AppCompatActivity() {
    var personAdapter: ItemPersonAdapter? = null
    var code: String? = ""
    var type: String? = ""
    var token: String? = ""
    var em_id: String? = ""
    var position: String? = ""
    var name: String? = ""
    var type_data: String? = ""
    var department: String? = ""
    var visit_tran_ids: Int? = 0

    var r : Ringtone? = null
    private var cardType = ByteArray(2) // 2 at least for M1 card
    private var serialNo = ByteArray(12) // 12 at least for M1 card
    private var piccOpenStatus = -1
    private var piccCheckStatus = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_emp)

        type = intent.getStringExtra("type")
        token = intent.getStringExtra("token")
        em_id = intent.getStringExtra("em_id")
        position = intent.getStringExtra("position")
        name = intent.getStringExtra("name")
        type_data = intent.getStringExtra("type_data")
        department = intent.getStringExtra("department")
        visit_tran_ids = intent.getIntExtra("visit_tran_ids",0)




        piccOpenStatus = PiccApi.PiccOpen_Api()
        Log.d("!!!","hello 1 : " + piccOpenStatus)
//            if (value == "in"){
//
//            }else{
//                listener.onCheckOut()
//            }

        if (piccOpenStatus == 0) {
            //  setTextM1("Please put on your card")
            checkM1Picc(7)
        } else {
            //    setTextM1("Picc open failed")
        }

//        button_scan.setOnClickListener {
//            if (piccOpenStatus == 0) {
//                //  setTextM1("Please put on your card")
//                checkM1Picc(7)
//            } else {
//                //    setTextM1("Picc open failed")
//            }
//        }



    }


    private fun checkM1Picc(type: Int) { // 0: read block, 1: write block  2: value block increase, 3: value block decrease
        object : Thread() {
            override fun run() {
                super.run()
                while (true) {
                    piccCheckStatus = PiccApi.PiccCheck_Api(3, cardType, serialNo)
                    Log.v("======= piccCheckStatus", piccCheckStatus.toString() + "")
                    if (piccCheckStatus == 0) {
                        when (type) {
                            7 ->

                            {

                                CardHandler.sendEmptyMessage(7)
                            }
                        }
//                        Log.d("!!!", CommonConvert.bcdToASCString(cardType))
//                        Log.d("!!!", CommonConvert.bcdToASCString(serialNo))
                        break
                    }
                }
            }
        }.start()
    }


    val CardHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {

                7 ->
                    {
                        try {
                            val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                            val r = RingtoneManager.getRingtone(
                                this@ScanEmpActivity,
                                notification
                            )
                            r.play()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        openDetail(
                            CommonConvert.bcdToASCString(serialNo)
                            .substring(2, 10))
                    }
                //            val intent = Intent(this, InformActivity::class.java)
//            startActivity(intent)
//                    listener.onCheckIn(
//                        CommonConvert.bcdToASCString(serialNo)
//                        .substring(2, 10))



//                    Log.d("!!!","serial no : " + CommonConvert.bcdToASCString(serialNo)
//                    .substring(2, 10))




            }
            closePicc()
        }

    }


    private fun openDetail(code : String){
        val intent = Intent(this, InformActivity::class.java)
        intent.putExtra("token",token)
        intent.putExtra("type",type)
        intent.putExtra("em_id",em_id)
        intent.putExtra("code",code)
        intent.putExtra("position",position)
        intent.putExtra("name",name)
        intent.putExtra("type_data",type_data)
        intent.putExtra("department",department)
        intent.putExtra("visit_tran_ids",visit_tran_ids)
        startActivity(intent)
        finish()
    }

    private fun closePicc() {
        if (PiccApi.PiccClose_Api() == 0) {
            piccOpenStatus = -1
            // listener.onCheckIn()
        }
    }






}
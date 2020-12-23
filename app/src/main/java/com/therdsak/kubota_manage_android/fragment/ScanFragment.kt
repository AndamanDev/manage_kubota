package com.therdsak.kubota_manage_android.fragment

import android.annotation.SuppressLint
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.therdsak.kubota_manage_android.R
import com.vanstone.trans.api.PiccApi
import com.vanstone.utils.CommonConvert


class ScanFragment : Fragment() {

    var value : String = ""
    private var cardType = ByteArray(2) // 2 at least for M1 card
    private var serialNo = ByteArray(12) // 12 at least for M1 card
    private var piccOpenStatus = -1
    private var piccCheckStatus = -1
   // val readCardHandler: ReadCardHandler? = null

    interface ScanListener {
        fun onCheckIn(code : String)
        fun onCheckOut(code : String)

    }

    private lateinit var listener: ScanListener


    companion object {

        fun newInstance(value : String): ScanFragment {
            val fragment = ScanFragment()
            val args = Bundle()
            args.putString("value",value)
            fragment.arguments = args
            return fragment
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        value = arguments?.getString("value")!!
       // readCardHandler = ReadCardHandler()

        piccOpenStatus = PiccApi.PiccOpen_Api()



    }





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (context is ScanListener) {
            listener = context as ScanListener
        } else {
            throw ClassCastException(
                context.toString() + " must implement OnDogSelected.") as Throwable
        }
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d("!!!","hello 1 : " + piccOpenStatus)



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

    private fun  checkM1Picc(type: Int) { // 0: read block, 1: write block  2: value block increase, 3: value block decrease
        object : Thread() {
            override fun run() {
                super.run()
                while (true) {
                    piccCheckStatus = PiccApi.PiccCheck_Api(3, cardType, serialNo)
                    Log.v("======= piccCheckStatus", piccCheckStatus.toString() + "")
                    if (piccCheckStatus == 0) {
                        when (type) {
                            7 -> {

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

                    if (value == "in"){
                        try {
                            val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                            val r = RingtoneManager.getRingtone(
                                context,
                                notification
                            )
                            r.play()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                            listener.onCheckIn(CommonConvert.bcdToASCString(serialNo)
                                .substring(2, 10))

                    }else{
                        try {
                        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                        val r = RingtoneManager.getRingtone(
                            context,
                            notification
                        )
                        r.play()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                            listener.onCheckOut(
                                CommonConvert.bcdToASCString(serialNo)
                                    .substring(2, 10)
                            )

                    }





//                    Log.d("!!!","serial no : " + CommonConvert.bcdToASCString(serialNo)
//                    .substring(2, 10))




            }
            closePicc()
        }

    }


    private fun closePicc() {
        if (PiccApi.PiccClose_Api() == 0) {
            piccOpenStatus = -1
           // listener.onCheckIn()
        }
    }
}
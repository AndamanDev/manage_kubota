package com.therdsak.kubota_manage_android.service


import com.therdsak.kubota_manage_android.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ServiceKubota {
    @POST("/v1/user/login")
    fun callLogin(@Body request: RequestBody): Call<LoginResponse>




    @GET("/v1/visitor/persons")
    fun callGetvisitorContact(@Header("Authorization") authorization : String,@Query("visitor_card_id") visitor_card_id : String): Call<VisitorResponse>
//
//
//
    @GET("/v1/visitor/person")
    fun callGetUserContact(@Header("Authorization") authorization : String,
                           @Query("visitor_card_id") visitor_card_id : String ,
                           @Query("visitor_ids") visitor_ids : Int): Call<VisitorContactResponse>
//
//
    // in
    @GET("/v1/visitor/employee")
    fun callGetEmployee(@Header("Authorization") authorization : String,
                           @Query("em_rfid") em_rfid : String): Call<EmpResponse>



    // out
    @GET("/v1/visitor/sec")
    fun callGetSec(@Header("Authorization") authorization : String,
                           @Query("sec_rfid_id") sec_rfid_id : String ): Call<SecResponse>

    
//
//
    @GET("/v1/visitor/checking")
    fun callCheckvisit(@Header("Authorization") authorization : String,
                       @Query("visit_tran_ids") visit_tran_ids : String,
                       @Query("em_id") em_id : String): Call<MessageResponse>
//
//    // in
    @POST("v1/visitor/update-visittarn")
    fun callupdateVisitTran(@Header("Authorization") authorization : String,
                       @Query("visit_tran_ids") visit_tran_ids : String,
                       @Query("em_id_meet") em_id_meet : String): Call<MessageResponse>
//
//
//    // out
    @POST("v1/visitor/update-visitout")
    fun callupdateVisitOut(@Header("Authorization") authorization : String,
                       @Query("visit_tran_ids") visit_tran_ids : String,
                       @Query("sec_eid") sec_eid : String): Call<MessageResponse>
//
//


}


package com.therdsak.kubota_manage_android.model

import com.google.gson.annotations.SerializedName

class VisitorListDataResponse {
    @SerializedName("visit_tran_ids")
    var visit_tran_ids: Int? = 0
    @SerializedName("visitor_ids")
    var visitor_ids: Int? = 0
    @SerializedName("visitor_nm")
    var visitor_nm:  String? = ""
    @SerializedName("visitor_type_desc")
    var visitor_type_desc: String? = ""
    @SerializedName("visitor_phone")
    var visitor_phone: String? = ""
    @SerializedName("visitor_company")
    var visitor_company: String? = ""
    @SerializedName("visitor_address")
    var visitor_address: String? = ""
    @SerializedName("visitor_pic_online")
    var visitor_pic_online: String? = ""
}
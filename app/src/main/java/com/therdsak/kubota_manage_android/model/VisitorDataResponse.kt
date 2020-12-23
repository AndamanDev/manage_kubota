package com.therdsak.kubota_manage_android.model

import com.google.gson.annotations.SerializedName

class VisitorDataResponse {
    @SerializedName("visit_tran_ids")
    var visit_tran_ids: Int? = 0
    @SerializedName("visit_type_desc")
    var visit_type_desc: String? = ""
    @SerializedName("visit_in_date")
    var visit_in_date: String? = ""
    @SerializedName("workunit_desc")
    var workunit_desc: String? = ""
    @SerializedName("visit_tran_status_id")
    var visit_tran_status_id: Int? = 0
    @SerializedName("em_nm")
    var em_nm:  String? = ""
    @SerializedName("em_id")
    var em_id:  String? = ""
    @SerializedName("position")
    var position: String? = ""
    @SerializedName("visitors")
    var visitors : List<VisitorListDataResponse>? = null
}
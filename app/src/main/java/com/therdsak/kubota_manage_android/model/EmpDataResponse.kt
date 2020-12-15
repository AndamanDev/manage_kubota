package com.therdsak.kubota_manage_android.model

import com.google.gson.annotations.SerializedName

class EmpDataResponse {
    @SerializedName("em_id")
    var em_id: String? = ""
    @SerializedName("em_nm")
    var em_nm: String? = ""
    @SerializedName("department_desc")
    var department_desc: String? = ""
    @SerializedName("division_desc")
    var division_desc: String? = ""
}
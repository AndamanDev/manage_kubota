package com.therdsak.kubota_manage_android.model

import com.google.gson.annotations.SerializedName

class EmpResponse {
    @SerializedName("statusCode")
    var statusCode: Int? = 0
    @SerializedName("success")
    var success: Boolean? = false
    @SerializedName("message")
    var message: String? = ""
    @SerializedName("data")
    var data: EmpDataResponse? = null
}
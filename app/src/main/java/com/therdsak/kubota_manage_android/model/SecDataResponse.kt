package com.therdsak.kubota_manage_android.model

import com.google.gson.annotations.SerializedName

class SecDataResponse {
    @SerializedName("sec_eid")
    var sec_eid: String? = ""
    @SerializedName("sec_nm")
    var sec_nm: String? = ""
    @SerializedName("sec_rfid_id")
    var sec_rfid_id: String? = ""

}
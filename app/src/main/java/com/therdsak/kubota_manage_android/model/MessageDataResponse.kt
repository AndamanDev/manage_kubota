package com.therdsak.kubota_manage_android.model

import com.google.gson.annotations.SerializedName

class MessageDataResponse {
    @SerializedName("checking")
    var checking: Boolean? = false
    @SerializedName("message")
    var message: String? = ""
}
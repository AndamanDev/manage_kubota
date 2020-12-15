package com.therdsak.kubota_manage_android.model

import com.google.gson.annotations.SerializedName


class LoginDataResponse {
    @SerializedName("access_token")
    var access_token: String? = ""
    @SerializedName("expires_in")
    var expires_in: Int? = 0
    @SerializedName("refresh_token")
    var refresh_token: String? = ""
    @SerializedName("token_type")
    var token_type: String? = ""
}
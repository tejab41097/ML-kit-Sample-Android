package com.tejas.mlkitsample.util

import com.google.gson.annotations.SerializedName

data class ErrorBody(

    @SerializedName("status")
    var status: Int? = 0,

    @SerializedName("message")
    var message: String? = "",

    @SerializedName("error")
    var error: String? = ""

)
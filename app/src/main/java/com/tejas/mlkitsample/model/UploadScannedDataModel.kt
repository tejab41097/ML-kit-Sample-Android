package com.tejas.mlkitsample.model

data class UploadScannedDataModel(
    val list: MutableList<ScannedData>,
    val email: String = "example@gmail.com",
    val password: String = "password"
)

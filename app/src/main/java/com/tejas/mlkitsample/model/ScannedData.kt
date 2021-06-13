package com.tejas.mlkitsample.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScannedData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var type: String,
    var timeStamp: String,
    var text1: String? = null,
    var text2: String? = null,
    var isSynced: Boolean = false
)

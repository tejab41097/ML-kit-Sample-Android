package com.tejas.mlkitsample.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScannedData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var data: String,
    var timeStamp: String,
    var isSynced: Boolean = false
)

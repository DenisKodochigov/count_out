package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_settings")
data class SettingDB(
    @PrimaryKey(autoGenerate = true) var idSetting: Long = 0,
    var parameter: Int = 0,
    var value: Int = 0,
)

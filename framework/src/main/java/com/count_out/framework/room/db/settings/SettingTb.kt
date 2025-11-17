package com.count_out.framework.room.db.settings

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_tb")
data class SettingTb(
    @PrimaryKey(autoGenerate = true) var idSetting: Long = 0,
    var parameter: Int = 0,
    var value: Int = 0,
)
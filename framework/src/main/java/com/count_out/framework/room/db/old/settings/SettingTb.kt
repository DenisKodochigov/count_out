package com.count_out.framework.room.db.old.settings

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_settings")
data class SettingTb(
    @PrimaryKey(autoGenerate = true) var idSetting: Long = 0,
    var parameter: Int = 0,
    var value: Int = 0,
)

package com.count_out.framework.room.db.settings

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SettingDao {
    @Insert
    fun insert(item: SettingTb): Long

    @Update
    fun update(item: SettingTb)

    @Query("SELECT * FROM settings_tb")
    fun gets(): List<SettingTb>

    @Query("SELECT * FROM settings_tb WHERE parameter = :parameter")
    fun get(parameter: Int): SettingTb

    @Query("SELECT * FROM settings_tb WHERE idSetting = :id")
    fun getId(id: Long): SettingTb
}
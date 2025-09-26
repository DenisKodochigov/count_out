package com.count_out.framework.room.db.old.settings

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

    @Query("SELECT * FROM tb_settings")
    fun gets(): List<SettingTb>

    @Query("SELECT * FROM tb_settings WHERE parameter = :parameter")
    fun get(parameter: Int): SettingTb

    @Query("SELECT * FROM tb_settings WHERE idSetting = :id")
    fun getId(id: Long): SettingTb
}
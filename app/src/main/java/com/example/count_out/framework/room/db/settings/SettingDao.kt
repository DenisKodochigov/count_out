package com.example.count_out.framework.room.db.settings

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SettingDao {
    @Insert
    fun add(item: SettingTable): Long

    @Update
    fun update(item: SettingTable)

    @Query("SELECT * FROM tb_settings")
    fun gets(): SettingTable

    @Query("SELECT * FROM tb_settings WHERE parameter = :parameter")
    fun get(parameter: Int): SettingTable

    @Query("SELECT * FROM tb_settings WHERE idSetting = :id")
    fun getId(id: Long): SettingTable
}
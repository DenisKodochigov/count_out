package com.example.framework.room.db.settings

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SettingDao {
    @Insert
    fun add(item: SettingTable): Long

    @Update
    fun update(item: SettingTable): Int

    @Query("SELECT * FROM tb_settings")
    fun gets(): List<SettingTable>

    @Query("SELECT * FROM tb_settings WHERE parameter = :parameter")
    fun get(parameter: Int): SettingTable

    @Query("SELECT * FROM tb_settings WHERE idSetting = :id")
    fun getId(id: Long): SettingTable
}
package com.count_out.framework.room.db.traking

import androidx.room.Dao
import androidx.room.Query
import com.count_out.framework.room.db.PrimeDao

@Dao
interface TrackingDao: PrimeDao<TemporaryTb> {
    @Query("DELETE FROM tb_temporary")
    fun clearTemporaryData()
    @Query("SELECT COUNT(id) FROM tb_temporary")
    fun countTemporary(): Int
    @Query("SELECT * FROM tb_temporary LIMIT :limit OFFSET :offset")
    fun selectNRecord(limit: Int, offset: Int): List<TemporaryTb>
}
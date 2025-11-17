package com.count_out.framework.room.db.traking

import androidx.room.Dao
import androidx.room.Query
import com.count_out.framework.room.db.PrimeDao

@Dao
interface TrackingDao: PrimeDao<TemporaryTb> {
    @Query("DELETE FROM temporary_tb")
    fun clearTemporaryData()
    @Query("SELECT COUNT(id) FROM temporary_tb")
    fun countTemporary(): Int
    @Query("SELECT * FROM temporary_tb LIMIT :limit OFFSET :offset")
    fun selectNRecord(limit: Int, offset: Int): List<TemporaryTb>
}
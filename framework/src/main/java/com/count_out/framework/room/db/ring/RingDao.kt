package com.count_out.framework.room.db.ring

import androidx.room.Dao
import androidx.room.Query
import com.count_out.framework.room.db.PrimeDao

@Dao
interface RingDao: PrimeDao<RingTb> {
    @Query("DELETE FROM ring_tb WHERE idRing = :id")
    fun del(id: Long): Int

    @Query("SELECT * FROM ring_tb WHERE partId = :id ORDER BY idView ASC")
    fun getRingInPart(id: Long): List<RingTb>
}

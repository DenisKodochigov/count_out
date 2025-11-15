package com.count_out.framework.room.db.ring

import androidx.room.Dao
import androidx.room.Query
import com.count_out.framework.room.db.PrimeDao

@Dao
interface RingDao: PrimeDao<RingTb> {
    @Query("DELETE FROM tb_ring WHERE idRing = :id")
    fun del(id: Long): Int

    @Query("SELECT * FROM tb_ring WHERE partId = :id ORDER BY idView ASC")
    fun getRingInPart(id: Long): List<RingTb>
}

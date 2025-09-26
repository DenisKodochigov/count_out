package com.count_out.framework.room.db.ring

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.count_out.framework.room.db.PrimeDao

@Dao
interface RingDao: PrimeDao<RingTb> {
    @Query("DELETE FROM tb_ring WHERE idRing = :id")
    fun del(id: Long): Int
}

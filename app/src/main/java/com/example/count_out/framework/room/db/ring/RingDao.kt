package com.example.count_out.framework.room.db.ring

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.count_out.framework.room.db.relation.RingRel
import kotlinx.coroutines.flow.Flow

@Dao
interface RingDao {
    @Insert
    fun add(item: RingTable): Long

    @Update
    fun update(item: RingTable)

    @Transaction
    @Query("SELECT * FROM tb_ring WHERE idRing = :id")
    fun get(id: Long): RingRel

    @Transaction
    @Query("SELECT * FROM tb_ring WHERE trainingId = :trainingID")
    fun gets( trainingID: Long): List<RingRel>

    @Query("DELETE FROM tb_ring WHERE idRing = :id")
    fun del(id: Long)

    @Query("SELECT * FROM tb_ring WHERE trainingId = :trainingID")
    fun getRingsForTraining( trainingID: Long): List<RingTable>
}
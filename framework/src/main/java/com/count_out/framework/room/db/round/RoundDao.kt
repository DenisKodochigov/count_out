package com.count_out.framework.room.db.round

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.count_out.framework.room.db.relation.RoundRel
import kotlinx.coroutines.flow.Flow

@Dao
interface RoundDao {
    @Insert
    fun add(item: RoundTable): Long

    @Update
    fun update(item: RoundTable)

    @Transaction
    @Query("SELECT * FROM tb_round WHERE idRound = :id")
    fun get(id: Long): Flow<RoundRel>

    @Transaction
    @Query("SELECT * FROM tb_round WHERE trainingId = :trainingID")
    fun gets( trainingID: Long): Flow<List<RoundRel>>

    @Query("DELETE FROM tb_round WHERE idRound = :id")
    fun del(id: Long)

    @Query("SELECT trainingId FROM tb_round WHERE idRound = :id")
    fun getIdTrainingFormRound(id: Long): Long
}
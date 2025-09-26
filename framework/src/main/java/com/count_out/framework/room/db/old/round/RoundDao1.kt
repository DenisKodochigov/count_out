package com.count_out.framework.room.db.old.round

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RoundDao1 {
//    @Insert
//    fun add(item: RoundTable): Long
//    @Update
//    fun update(item: RoundTable): Int
//    @Query("DELETE FROM tb_round WHERE idRound = :id")
//    fun del(id: Long): Int
//    @Query("SELECT trainingId FROM tb_round WHERE idRound = :id")
//    fun getIdTrainingFormRound(id: Long): Long?
}
//    @Transaction
//    @Query("SELECT * FROM tb_round WHERE idRound = :id")
//    fun get(id: Long): Flow<RoundRel?>
//    @Transaction
//    @Query("SELECT * FROM tb_round WHERE trainingId = :trainingID")
//    fun gets( trainingID: Long): Flow<List<RoundRel>>
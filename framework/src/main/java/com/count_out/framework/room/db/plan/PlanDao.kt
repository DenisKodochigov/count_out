package com.count_out.framework.room.db.plan

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.count_out.framework.room.db.PrimeDao
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao: PrimeDao<PlanTb> {
    @Query("DELETE FROM plan_tb WHERE idPlan = :id")
    fun del(id: Long): Int
    @Transaction
    @Query("SELECT * FROM plan_tb WHERE idPlan = :id")
    fun getPlan(id: Long): Flow<PlanTb>
    @Transaction
    @Query("SELECT * FROM plan_tb WHERE idPlan != 1")
    fun getPlans(): Flow<List<PlanTb>>
}
//    @Transaction
//    @Query("SELECT * FROM tb_trainings WHERE idTraining = :id")
//    fun getPlanRel(id: Long): TrainingRel?
//@Query("SELECT name FROM tb_trainings WHERE idTraining = :id")
//fun getName(id: Long): Flow<String?>
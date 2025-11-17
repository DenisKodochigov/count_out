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
    fun getPlan(id: Long): Flow<PlanRel?>
    @Transaction
    @Query("SELECT * FROM plan_tb WHERE idPlan != 1 ORDER BY idView ASC")
    fun getPlans(): Flow<List<PlanRel>>

    @Query("SELECT * FROM plan_tb WHERE idPlan != 1 ORDER BY idView ASC")
    fun gets(): List<PlanTb>
    @Query("UPDATE plan_tb SET name = :name WHERE idPlan =:id")
    fun updateName( name: String, id: Long): Int
}

package com.count_out.framework.room.db.activity

import androidx.room.Dao
import androidx.room.Query
import com.count_out.framework.room.db.PrimeDao
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao: PrimeDao<ActivityTb> {

    @Query("SELECT * FROM tb_activity")
    fun gets(): Flow<List<ActivityTb>>

    @Query("SELECT * FROM tb_activity WHERE idActivity = :id")
    fun get(id: Long): Flow<ActivityTb>

    @Query("DELETE FROM tb_activity WHERE idActivity = :id")
    fun del(id: Long): Int

    @Query("SELECT idExercise FROM exercise_tb WHERE activityId =:activityId")
    fun checkExerciseWithActivity(activityId: Long): Long?
}
//    @Query("UPDATE tb_activity SET color = :color WHERE idActivity =:activityId")
//    fun setColor(activityId: Long, color: Int): Int
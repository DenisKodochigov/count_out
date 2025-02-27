package com.count_out.framework.room.db.activity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    @Query("SELECT * FROM tb_activity")
    fun gets(): Flow<List<ActivityTable>>

    @Query("SELECT * FROM tb_activity WHERE idActivity = :id")
    fun get(id: Long): Flow<ActivityTable>

    @Insert
    fun add(item: ActivityTable): Long

    @Update
    fun update(item: ActivityTable)

    @Query("DELETE FROM tb_activity WHERE idActivity = :id")
    fun del(id: Long)

    @Query("UPDATE tb_activity SET color = :color WHERE idActivity =:activityId")
    fun setColor(activityId: Long, color: Int): Int

    @Query("SELECT idExercise FROM tb_exercise WHERE activityId =:activityId")
    fun checkExerciseWithActivity(activityId: Long): Long?
}
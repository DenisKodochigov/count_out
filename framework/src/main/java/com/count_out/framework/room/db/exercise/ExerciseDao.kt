package com.count_out.framework.room.db.exercise

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.count_out.framework.room.db.PrimeDao

@Dao
interface ExerciseDao: PrimeDao<ExerciseTb> {
    @Query("DELETE FROM exercise_tb WHERE idExercise = :id")
    fun del(id: Long): Int
    @Query("UPDATE exercise_tb SET activityId = :activityId WHERE idExercise =:exerciseId")
    fun setActivity(exerciseId: Long, activityId: Long): Int
    @Transaction
    @Query("SELECT * FROM exercise_tb WHERE ringId = :id ORDER BY idView ASC")
    fun getExerciseRing(id: Long): List<ExerciseTb>
}

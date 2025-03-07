package com.count_out.framework.room.db.traking

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TrackingDao {
    @Insert
    fun addRecordMetric(item: TemporaryTable): Long

    @Query("DELETE FROM tb_temporary")
    fun clearTemporaryData()

    @Query("SELECT COUNT(id) FROM tb_temporary")
    fun countTemporary(): Int
    
    @Query("SELECT * FROM tb_temporary LIMIT :limit OFFSET :offset")
    fun selectNRecord(limit: Int, offset: Int): List<TemporaryTable>

    @Insert
    fun addWorkout(workout: WorkoutTable): Long
}
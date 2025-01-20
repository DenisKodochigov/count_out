package com.example.framework.room.db.training

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.framework.room.db.relation.TrainingRel
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {
    @Insert
    fun add(item: TrainingTable): Long

    @Update
    fun update(item: TrainingTable)

    @Query("DELETE FROM tb_trainings WHERE idTraining = :id")
    fun del(id: Long)

    @Transaction
    @Query("SELECT * FROM tb_trainings WHERE idTraining = :id")
    fun getTrainingRel(id: Long): Flow<TrainingRel>

    @Transaction
    @Query("SELECT * FROM tb_trainings WHERE idTraining != 1")
    fun getTrainingsRel(): Flow<List<TrainingRel>>

    @Query("SELECT name FROM tb_trainings WHERE idTraining = :id")
    fun getName(id: Long): Flow<String>
}
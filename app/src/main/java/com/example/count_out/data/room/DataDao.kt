package com.example.count_out.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.TrainingDB

@Dao
interface DataDao {
//Training
    @Insert
    fun addTraining(item: TrainingDB): Long
    @Update
    fun updateTraining(item: TrainingDB): Int
    @Query("SELECT * FROM tb_trainings WHERE idTraining = :id")
    fun getTraining(id: Long): TrainingDB
    @Query("SELECT * FROM tb_trainings")
    fun getTrainings(): List<TrainingDB>
    @Query("SELECT * FROM tb_trainings WHERE idTraining IN (:list)")
    fun getTrainingsFilter( list: List<Long>): List<TrainingDB>
    @Query("DELETE FROM tb_trainings WHERE idTraining = :id")
    fun delTraining(id: Long)

//Round
    @Insert
    fun addRound(item: RoundDB): Long
    @Update
    fun updateRound(item: RoundDB): Int
    @Query("SELECT * FROM tb_round WHERE idRound = :id")
    fun getRound(id: Long): RoundDB
    @Query("SELECT * FROM tb_round WHERE idRound IN (:list)")
    fun getRounds( list: List<Long>): List<RoundDB>
    @Query("DELETE FROM tb_round WHERE idRound = :id")
    fun delRound(id: Long)

//Exercise
    @Insert
    fun addExercise(item: ExerciseDB): Long
    @Update
    fun updateExercise(item: ExerciseDB): Int
    @Query("SELECT * FROM tb_exercise WHERE idExercise = :id")
    fun getExercise(id: Long): ExerciseDB
    @Query("SELECT * FROM tb_exercise ")
    fun getExercises(): List<ExerciseDB>
    @Query("SELECT * FROM tb_exercise WHERE idExercise IN (:list)")
    fun getExercisesFilter( list: List<Long>): List<ExerciseDB>
    @Query("DELETE FROM tb_exercise WHERE idExercise = :id")
    fun delExercise(id: Long)

//Activity
    @Insert
    fun addActivity(item: ActivityDB): Long
    @Update
    fun updateActivity(item: ActivityDB): Int
    @Query("SELECT * FROM tb_activity WHERE idActivity = :id")
    fun getActivity(id: Long): ActivityDB?
    @Query("SELECT * FROM tb_activity WHERE idActivity IN (:list)")
    fun getActivities( list: List<Long>): List<ActivityDB>
    @Query("DELETE FROM tb_activity WHERE idActivity = :id")
    fun delActivity(id: Long)

//Set
    @Insert
    fun addSet(item: SetDB): Long
    @Update
    fun updateSet(item: SetDB): Int
    @Query("SELECT * FROM tb_set WHERE idSet = :id")
    fun getSet(id: Long): SetDB
    @Query("SELECT * FROM tb_set WHERE idSet IN (:list)")
    fun getSets( list: List<Long>): List<SetDB>
    @Query("DELETE FROM tb_set WHERE idSet = :id")
    fun delSet(id: Long)

//Speech
    @Insert
    fun addSpeech(item: SpeechDB): Long
    @Update
    fun updateSpeech(item: SpeechDB): Int
    @Query("SELECT * FROM tb_speech WHERE idSpeech = :id")
    fun getSpeech(id: Long): SpeechDB
    @Query("SELECT * FROM tb_speech WHERE idSpeech IN (:list)")
    fun getSpeechs( list: List<Long>): List<SpeechDB>
    @Query("DELETE FROM tb_speech WHERE idSpeech = :id")
    fun delSpeech(id: Long)
}

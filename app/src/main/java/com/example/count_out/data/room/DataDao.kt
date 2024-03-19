package com.example.count_out.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.count_out.data.room.relation.ExerciseRel
import com.example.count_out.data.room.relation.NameTrainingRel
import com.example.count_out.data.room.relation.RoundRel
import com.example.count_out.data.room.relation.SetRel
import com.example.count_out.data.room.relation.SpeechKitRel
import com.example.count_out.data.room.relation.TrainingRel
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.SpeechKitDB
import com.example.count_out.data.room.tables.TrainingDB

@Dao
interface DataDao {
//Training
    @Insert
    fun addTraining(item: TrainingDB): Long
    @Update
    fun updateTraining(item: TrainingDB): Int
    @Transaction @Query("SELECT * FROM tb_trainings WHERE idTraining = :id")
    fun getTrainingRel(id: Long): TrainingRel
    @Transaction @Query("SELECT * FROM tb_trainings")
    fun getTrainingsRel(): List<TrainingRel>
    @Query("DELETE FROM tb_trainings WHERE idTraining = :id")
    fun delTraining(id: Long)
    @Query("SELECT name FROM tb_trainings WHERE idTraining = :id")
    fun getNameTraining(id: Long): String
//Round
    @Insert
    fun addRound(item: RoundDB): Long
    @Update
    fun updateRound(item: RoundDB): Int
    @Query("SELECT * FROM tb_round WHERE idRound = :id")
    fun getRound(id: Long): RoundDB
    @Transaction @Query("SELECT * FROM tb_round WHERE idRound = :id")
    fun getRoundRel(id: Long): RoundRel
    @Query("SELECT * FROM tb_round WHERE idRound IN (:list)")
    fun getRounds( list: List<Long>): List<RoundDB>
    @Query("SELECT * FROM tb_round WHERE trainingId = :trainingID")
    fun getRoundsForTraining( trainingID: Long): List<RoundDB>
    @Query("DELETE FROM tb_round WHERE idRound = :id")
    fun delRound(id: Long)
    @Query("SELECT trainingId FROM tb_round WHERE idRound = :id")
    fun getIdTrainingFormRound(id: Long): Long
    @Query("SELECT roundType FROM tb_round WHERE idRound = :id")
    fun getNameRound(id: Long): String
    @Transaction @Query("SELECT * FROM tb_round WHERE idRound = :id")
    fun getNameTrainingFromRound(id: Long): NameTrainingRel

//Exercise
    @Insert
    fun addExercise(item: ExerciseDB): Long
    @Update
    fun updateExercise(item: ExerciseDB): Int
    @Transaction
    @Query("SELECT * FROM tb_exercise WHERE idExercise = :id")
    fun getExercise(id: Long): ExerciseDB
    @Transaction
    @Query("SELECT * FROM tb_exercise WHERE idExercise = :id")
    fun getExerciseRel(id: Long): ExerciseRel

    @Transaction @Query("SELECT * FROM tb_exercise WHERE roundId = :id")
    fun getExercisesForRoundRel(id: Long): List<ExerciseRel>
    @Query("SELECT * FROM tb_exercise ")
    fun getExercises(): List<ExerciseDB>
    @Query("SELECT * FROM tb_exercise WHERE idExercise IN (:list)")
    fun getExercisesFilter( list: List<Long>): List<ExerciseDB>
    @Query("DELETE FROM tb_exercise WHERE idExercise = :id")
    fun deleteExercise(id: Long)
    @Query("UPDATE tb_exercise SET activityId = :activityId WHERE idExercise =:exerciseId")
    fun changeActivityExercise(exerciseId: Long, activityId: Long): Int

//Activity
    @Insert
    fun addActivity(item: ActivityDB): Long
    @Update
    fun updateActivity(item: ActivityDB): Int
    @Query("SELECT * FROM tb_activity WHERE idActivity = :id")
    fun getActivity(id: Long): ActivityDB
    @Query("SELECT * FROM tb_activity")
    fun getActivities(): List<ActivityDB>
    @Query("DELETE FROM tb_activity WHERE idActivity = :id")
    fun delActivity(id: Long)
    @Query("UPDATE tb_activity SET color = :color WHERE idActivity =:activityId")
    fun setColorActivity(activityId: Long, color: Int): Int
//Set
    @Insert
    fun addSet(item: SetDB): Long
    @Update
    fun updateSet(item: SetDB): Int
    @Query("SELECT * FROM tb_set WHERE idSet = :id")
    fun getSet(id: Long): SetDB
    @Transaction @Query("SELECT * FROM tb_set WHERE idSet = :id")
    fun getSetRel(id: Long): SetRel
    @Query("SELECT * FROM tb_set WHERE idSet IN (:list)")
    fun getSets( list: List<Long>): List<SetDB>
    @Query("SELECT * FROM tb_set WHERE exerciseId = :exerciseId")
    fun getSets( exerciseId: Long): List<SetDB>
    @Query("DELETE FROM tb_set WHERE idSet = :id")
    fun delSet(id: Long)

//SpeechKit
    @Query("DELETE FROM tb_speech_kit WHERE idSpeechKit = :id")
    fun delSpeechKit(id: Long)
    @Query("SELECT * FROM tb_speech_kit WHERE idSpeechKit = :id")
    fun getSpeechKit(id: Long): SpeechKitRel
    @Insert
    fun addSpeechKit(item: SpeechKitDB): Long
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

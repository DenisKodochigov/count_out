package com.example.count_out.framework.room.old.tables
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import androidx.room.Transaction
//import androidx.room.Update
//import com.example.count_out.framework.room.relation.ExerciseRel
//import com.example.count_out.framework.room.relation.RoundRel
//import com.example.count_out.framework.room.relation.SetRel
//import com.example.count_out.framework.room.relation.TrainingRel
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface DataDao {
////Training
//    @Insert
//    fun addTraining(item: TrainingDB): Long
//
//    @Update
//    fun updateTraining(item: TrainingDB): Int
//
//    @Transaction @Query("SELECT * FROM tb_trainings WHERE idTraining = :id")
//    fun getTrainingRel(id: Long): TrainingRel
//
//    @Transaction @Query("SELECT * FROM tb_trainings WHERE idTraining != 1")
//    fun getTrainingsRel(): List<TrainingRel>
//
//    @Transaction @Query("SELECT * FROM tb_trainings WHERE idTraining != 1")
//    fun getTrainingsRelFlow(): Flow<List<TrainingRel>>
//
//    @Query("DELETE FROM tb_trainings WHERE idTraining = :id")
//    fun delTraining(id: Long)
//
//    @Query("SELECT name FROM tb_trainings WHERE idTraining = :id")
//    fun getNameTraining(id: Long): String
////Round
//    @Insert
//    fun addRound(item: RoundDB): Long
//
//    @Update
//    fun updateRound(item: RoundDB): Int
//
//    @Query("SELECT * FROM tb_round WHERE idRound = :id")
//    fun getRound(id: Long): RoundDB
//
//    @Transaction
//    @Query("SELECT * FROM tb_round WHERE idRound = :id")
//    fun getRoundRel(id: Long): RoundRel
//
//    @Query("SELECT * FROM tb_round WHERE trainingId = :trainingID")
//    fun getRoundsForTraining( trainingID: Long): List<RoundDB>
//
//    @Query("DELETE FROM tb_round WHERE idRound = :id")
//    fun delRound(id: Long)
//
//    @Query("SELECT trainingId FROM tb_round WHERE idRound = :id")
//    fun getIdTrainingFormRound(id: Long): Long
//
//    @Query("SELECT roundType FROM tb_round WHERE idRound = :id")
//    fun getNameRound(id: Long): String
//
////Exercise
//    @Insert
//    fun addExercise(item: ExerciseDB): Long
//
//    @Transaction
//    @Query("SELECT * FROM tb_exercise WHERE idExercise = :id")
//    fun getExercise(id: Long): ExerciseDB
//
//    @Transaction
//    @Query("SELECT * FROM tb_exercise WHERE idExercise = :id ORDER BY  idView ASC")
//    fun getExerciseRel(id: Long): ExerciseRel
//
//    @Transaction @Query("SELECT * FROM tb_exercise WHERE roundId = :id ORDER BY idView ASC")
//    fun getExercisesForRoundRel(id: Long): List<ExerciseRel>
//
//    @Transaction @Query("SELECT * FROM tb_exercise WHERE roundId = :id ORDER BY idView ASC")
//    fun getExercisesForRound(id: Long): List<ExerciseDB>
//
//    @Query("SELECT * FROM tb_exercise ")
//    fun getExercises(): List<ExerciseDB>
//
//    @Query("SELECT * FROM tb_exercise WHERE idExercise IN (:list)")
//    fun getExercisesFilter( list: List<Long>): List<ExerciseDB>
//
//    @Query("DELETE FROM tb_exercise WHERE idExercise = :id")
//    fun deleteExercise(id: Long)
//
//    @Query("UPDATE tb_exercise SET activityId = :activityId WHERE idExercise =:exerciseId")
//    fun changeActivityExercise(exerciseId: Long, activityId: Long): Int
//
//    @Query("SELECT max( idView) FROM tb_exercise WHERE roundId = :roundId")
//    fun getExerciseMaxSequential(roundId: Long): Int
//
//    @Query("UPDATE tb_exercise SET idView = :idView WHERE idExercise =:exerciseId")
//    fun updateIdView( exerciseId: Long, idView: Int)
////Activity
//    @Insert
//    fun addActivity(item: ActivityDB): Long
//
//    @Update
//    fun updateActivity(item: ActivityDB): Int
//
//    @Query("SELECT * FROM tb_activity WHERE idActivity = :id")
//    fun getActivity(id: Long): ActivityDB
//
//    @Query("SELECT * FROM tb_activity")
//    fun getActivities(): List<ActivityDB>
//
//    @Query("DELETE FROM tb_activity WHERE idActivity = :id")
//    fun delActivity(id: Long)
//
//    @Query("UPDATE tb_activity SET color = :color WHERE idActivity =:activityId")
//    fun setColorActivity(activityId: Long, color: Int): Int
////Set
//    @Insert
//    fun addSet(item: SetDB): Long
//
//    @Update
//    fun updateSet(item: SetDB): Int
//
//    @Transaction @Query("SELECT * FROM tb_set WHERE idSet = :id")
//    fun getSetRel(id: Long): SetRel
//
//    @Query("SELECT * FROM tb_set WHERE exerciseId = :exerciseId")
//    fun getSets( exerciseId: Long): List<SetDB>
//
//    @Query("DELETE FROM tb_set WHERE idSet = :id")
//    fun delSet(id: Long)
//
////SpeechKit
//    @Query("DELETE FROM tb_speech_kit WHERE idSpeechKit = :id")
//    fun delSpeechKit(id: Long)
//
//    @Insert
//    fun addSpeechKit(item: SpeechKitDB): Long
////Speech
//    @Insert
//    fun addSpeech(item: SpeechDB): Long
//
//    @Update
//    fun updateSpeech(item: SpeechDB): Int
//
//    @Query("SELECT * FROM tb_speech WHERE idSpeech = :id")
//    fun getSpeech(id: Long): SpeechDB
//
//    @Query("DELETE FROM tb_speech WHERE idSpeech = :id")
//    fun delSpeech(id: Long)
//
//    @Query("UPDATE tb_speech SET duration = :duration WHERE idSpeech =:id")
//    fun updateDuration(duration: Long, id: Long): Int
//    //Settings
//    @Insert
//    fun addSetting(item: SettingDB): Long
//
//    @Update
//    fun updateSetting(item: SettingDB): Int
//
//    @Query("SELECT * FROM tb_settings")
//    fun getSettings(): List<SettingDB>
//
//    @Query("SELECT * FROM tb_settings WHERE parameter = :parameter")
//    fun getSetting(parameter: Int): SettingDB
//
//    @Query("SELECT * FROM tb_settings WHERE idSetting = :id")
//    fun getSettingId(id: Long): SettingDB
//
//    //Temporary record training
//    @Insert
//    fun addRecordMetric(item: TemporaryDB): Long
//
//    @Query("DELETE FROM tb_temporary")
//    fun clearTemporaryData()
//
//    @Query("SELECT COUNT(id) FROM tb_temporary")
//    fun countTemporary(): Int
//
//    @Query("SELECT * FROM tb_temporary LIMIT :limit OFFSET :offset")
//    fun selectNRecord(limit: Int, offset: Int): List<TemporaryDB>
//
//    @Insert
//    fun addWorkout(workout: WorkoutDB): Long
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun addCounts(objects: List<CountDB>)
//}

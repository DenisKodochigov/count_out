package com.count_out.framework.room.db.old.exercise
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import androidx.room.Transaction
//import androidx.room.Update
//import com.count_out.framework.room.db.old.exercise.ExerciseTable

//@Dao
//interface ExerciseDao1 {
//    @Insert
//    fun add(item: ExerciseTable): Long
//    @Update
//    fun update( exercise: ExerciseTable): Int
//    @Update
//    fun updates( exercise: List<ExerciseTable>): Int
//    @Query("DELETE FROM tb_exercise WHERE idExercise = :id")
//    fun del(id: Long): Int
//    @Query("UPDATE tb_exercise SET activityId = :activityId WHERE idExercise =:exerciseId")
//    fun setActivity(exerciseId: Long, activityId: Long): Int
//
//    @Transaction
//    @Query("SELECT * FROM tb_exercise WHERE roundId = :id ORDER BY idView ASC")
//    fun getExerciseRound(id: Long): List<ExerciseTable>
//}

//    @Query("SELECT max( idView) FROM tb_exercise WHERE roundId = :roundId")
//    fun getExerciseMaxSequential(roundId: Long): Flow<ExerciseRel>

//    @Query("UPDATE tb_exercise SET idView = :idView WHERE idExercise =:exerciseId")
//    fun updateIdView( exerciseId: Long, idView: Int)
//    @Query("DELETE FROM tb_exercise WHERE roundId = :id")
//    fun delRound(id: Long)
//    @Query("DELETE FROM tb_exercise WHERE ringId = :id")
//    fun delRing(id: Long)
//    @Transaction
//    @Query("SELECT * FROM tb_exercise ")
//    fun gets(): Flow<List<ExerciseRel>>
//    @Transaction
//    @Query("SELECT * FROM tb_exercise WHERE idExercise = :id ORDER BY idView ASC")
//    fun get(id: Long): Flow<ExerciseRel?>
//    @Transaction
//    @Query("SELECT * FROM tb_exercise WHERE roundId = :id ORDER BY idView ASC")
//    fun getForRound(id: Long): Flow<List<ExerciseRel>>
//    @Transaction
//    @Query("SELECT * FROM tb_exercise WHERE ringId = :id ORDER BY idView ASC")
//    fun getForRing(id: Long): Flow<List<ExerciseRel>>
//    @Transaction
//    @Query("SELECT * FROM tb_exercise WHERE idExercise IN (:list)")
//    fun getFilter( list: List<Long>): Flow<List<ExerciseRel>>
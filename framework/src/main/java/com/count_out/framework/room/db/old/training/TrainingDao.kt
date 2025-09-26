package com.count_out.framework.room.db.old.training

//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import androidx.room.Transaction
//import androidx.room.Update
//import kotlinx.coroutines.flow.Flow

//@Dao
//interface TrainingDao {
//    @Insert
//    fun add(item: TrainingTable): Long
//    @Update
//    fun update(item: TrainingTable): Int
//    @Query("DELETE FROM tb_trainings WHERE idTraining = :id")
//    fun del(id: Long): Int
//    @Transaction
//    @Query("SELECT * FROM tb_trainings WHERE idTraining = :id")
//    fun getTrainingRel(id: Long): Flow<TrainingRelo?>
//    @Transaction
//    @Query("SELECT * FROM tb_trainings WHERE idTraining != 1")
//    fun getTrainingsRel(): Flow<List<TrainingRelo>>
//}
//    @Transaction
//    @Query("SELECT * FROM tb_trainings WHERE idTraining = :id")
//    fun getPlanRel(id: Long): TrainingRel?
//@Query("SELECT name FROM tb_trainings WHERE idTraining = :id")
//fun getName(id: Long): Flow<String?>
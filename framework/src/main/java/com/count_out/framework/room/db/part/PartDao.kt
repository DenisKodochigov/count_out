package com.count_out.framework.room.db.part

import androidx.room.Dao
import com.count_out.framework.room.db.PrimeDao

@Dao
interface PartDao: PrimeDao<PartTb> {

}
//    @Transaction
//    @Query("SELECT * FROM tb_trainings WHERE idTraining = :id")
//    fun getPlanRel(id: Long): TrainingRel?
//@Query("SELECT name FROM tb_trainings WHERE idTraining = :id")
//fun getName(id: Long): Flow<String?>
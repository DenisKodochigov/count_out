package com.count_out.framework.room.db.old.set

//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import androidx.room.Update

//@Dao
//interface SetDao1 {
//    @Insert
//    fun add(item: SetTable): Long
//    @Update
//    fun update(item: SetTable): Int
//    @Query("DELETE FROM tb_set WHERE idSet = :id")
//    fun del(id: Long): Int
//
//}
//    @Query("DELETE FROM tb_set WHERE exerciseId = :id")
//    fun dels(id: Long): Int
//    @Transaction
//    @Query("SELECT * FROM tb_set WHERE exerciseId = :exerciseId")
//    fun gets(exerciseId: Long): Flow<List<SetRel?>>
//    @Transaction
//    @Query("SELECT * FROM tb_set WHERE idSet = :id")
//    fun get(id: Long): Flow<SetRel?>
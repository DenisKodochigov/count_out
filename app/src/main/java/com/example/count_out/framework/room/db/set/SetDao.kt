package com.example.count_out.framework.room.db.set

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.count_out.framework.room.db.relation.SetRel
import kotlinx.coroutines.flow.Flow

@Dao
interface SetDao {
    @Insert
    fun add(item: SetTable): Long

    @Update
    fun update(item: SetTable)

    @Query("DELETE FROM tb_set WHERE idSet = :id")
    fun del(id: Long)

    @Query("DELETE FROM tb_set WHERE exerciseId = :id")
    fun dels(id: Long)

    @Transaction
    @Query("SELECT * FROM tb_set WHERE exerciseId = :exerciseId")
    fun gets(exerciseId: Long): List<SetRel>

    @Transaction
    @Query("SELECT * FROM tb_set WHERE idSet = :id")
    fun getRel(id: Long): Flow<SetRel>
}
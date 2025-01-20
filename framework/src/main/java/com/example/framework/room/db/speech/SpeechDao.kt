package com.example.framework.room.db.speech

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SpeechDao {
    @Insert
    fun add(item: SpeechTable): Long

    @Update
    fun update(item: SpeechTable)

    @Query("SELECT * FROM tb_speech WHERE idSpeech = :id")
    fun get(id: Long): Flow<SpeechTable>

    @Query("DELETE FROM tb_speech WHERE idSpeech = :id")
    fun del(id: Long)

    @Query("UPDATE tb_speech SET duration = :duration WHERE idSpeech =:id")
    fun updateDuration(duration: Long, id: Long)
}
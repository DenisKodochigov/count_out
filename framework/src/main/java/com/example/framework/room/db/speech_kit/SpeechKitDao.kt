package com.example.framework.room.db.speech_kit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.framework.room.db.relation.SpeechKitRel
import kotlinx.coroutines.flow.Flow

@Dao
interface SpeechKitDao {

    @Insert
    fun add(item: SpeechKitTable): Flow<SpeechKitRel>

    @Query("SELECT * FROM tb_speech_kit WHERE idSpeechKit = :id")
    fun get(id: Long): Flow<SpeechKitRel>

    @Query("DELETE FROM tb_speech_kit WHERE idSpeechKit = :id")
    fun del(id: Long)
}
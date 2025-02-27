package com.count_out.framework.room.db.speech_kit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.count_out.framework.room.db.relation.SpeechKitRel
import kotlinx.coroutines.flow.Flow

@Dao
interface SpeechKitDao {

    @Insert
    fun add(item: SpeechKitTable): Long

    @Transaction
    @Query("SELECT * FROM tb_speech_kit WHERE idSpeechKit = :id")
    fun get(id: Long): Flow<SpeechKitRel>

    @Query("DELETE FROM tb_speech_kit WHERE idSpeechKit = :id")
    fun del(id: Long)
}
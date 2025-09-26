package com.count_out.framework.room.db.speech

import androidx.room.Dao
import androidx.room.Query
import com.count_out.framework.room.db.PrimeDao

@Dao
interface SpeechDao: PrimeDao<SpeechTb> {
    @Query("SELECT * FROM speech_tb WHERE idKit = :id")
    fun getForKit(id: Long): List<SpeechTb>
}


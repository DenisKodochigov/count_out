package com.count_out.framework.room.db.speech

import androidx.room.Dao
import androidx.room.Query
import com.count_out.framework.room.db.PrimeDao

@Dao
interface SpeechDao: PrimeDao<SpeechTb> {
    @Query("SELECT * FROM speech_tb " +
        "WHERE setId = :setId OR exerciseId = :exerciseId OR ringId = :ringId OR partId = :partId OR planId = :planId")
    fun getSpeeches(
        setId: Long? = null,
        exerciseId: Long? = null,
        ringId: Long? = null,
        partId: Long? = null,
        planId: Long? = null): List<SpeechTb>
}


package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.count_out.entity.SpeechActivity
@Entity(tableName = "tb_speech")
data class SpeechActivityDB(
    @PrimaryKey(autoGenerate = true) override val idSpeech: Long = 0,
    override val soundBeforeStart: String = "",
    override val soundAfterStart: String = "",
    override val soundBeforeEnd: String = "",
    override val soundAfterEnd: String = "",
): SpeechActivity

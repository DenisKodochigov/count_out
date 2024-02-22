package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.count_out.entity.Speech
@Entity(tableName = "tb_speech")
data class SpeechDB(
    @PrimaryKey(autoGenerate = true) override var idSpeech: Long = 0,
    override var beforeStart: String = "",
    override var afterStart: String = "",
    override var beforeEnd: String = "",
    override var afterEnd: String = "",
): Speech

package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.count_out.entity.Speech
import com.example.count_out.entity.SpeechKit

@Entity(tableName = "tb_speech_kit")
data class SpeechKitDB(
    @PrimaryKey(autoGenerate = true) override var idSpeechKit: Long = 0,
    override var idBeforeStart: Long = 0L,
    override var idAfterStart: Long = 0L,
    override var idBeforeEnd: Long = 0L,
    override var idAfterEnd: Long = 0L,
    @Ignore override var beforeStart: Speech = SpeechDB(),
    @Ignore override var afterStart: Speech = SpeechDB(),
    @Ignore override var beforeEnd: Speech = SpeechDB(),
    @Ignore override var afterEnd: Speech = SpeechDB(),
): SpeechKit
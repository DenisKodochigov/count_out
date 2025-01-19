package com.example.framework.room.db.speech_kit

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_speech_kit")
data class SpeechKitTable(
    @PrimaryKey(autoGenerate = true)  var idSpeechKit: Long = 0,
     var idBeforeStart: Long = 0L,
     var idAfterStart: Long = 0L,
     var idBeforeEnd: Long = 0L,
     var idAfterEnd: Long = 0L,
//    @Ignore  var beforeStart: Speech = SpeechTable(),
//    @Ignore  var afterStart: Speech = SpeechTable(),
//    @Ignore  var beforeEnd: Speech = SpeechTable(),
//    @Ignore  var afterEnd: Speech = SpeechTable(),
)
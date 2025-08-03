package com.count_out.framework.room.db.speech_kit

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.count_out.data.models.SpeechKitImplD
import com.count_out.domain.entity.workout.SpeechKit

@Entity(tableName = "tb_speech_kit")
data class SpeechKitTable(
    @PrimaryKey(autoGenerate = true)  var idSpeechKit: Long = 0,
     var idBeforeStart: Long = 0L,
     var idAfterStart: Long = 0L,
     var idBeforeEnd: Long = 0L,
     var idAfterEnd: Long = 0L,
) {
    constructor(item: SpeechKitImplD): this(
        idBeforeStart = item.beforeStart?.idSpeech ?: 0,
        idAfterStart = item.afterStart?.idSpeech ?: 0,
        idBeforeEnd = item.beforeEnd?.idSpeech ?: 0,
        idAfterEnd = item.afterEnd?.idSpeech ?: 0,
    )
}
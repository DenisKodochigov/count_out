package com.count_out.framework.room.db.speech

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.Index
import com.count_out.data.models.SpeechDb
import com.count_out.framework.room.db.speech_kit.SpeechKitTb

@Entity(tableName = "speech_tb",
    primaryKeys = ["idSpeech"],
    ignoredColumns = ["addMessage"],
    indices = [Index(value = ["idSpeech"], unique = true),Index(value = ["idKit"])],
    foreignKeys = [ForeignKey(
        entity = SpeechKitTb::class,
        parentColumns = ["idSpeechKit"],
        childColumns = ["idKit"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = NO_ACTION
    )],
)
data class SpeechTb(
    override var idSpeech: Long = 0,
    override var idKit: Long = 0,
    override var message: String = "",
    override var duration: Long = 0L,
    override var addMessage: String = "",
): SpeechDb

package com.count_out.framework.room.db.set

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.TypeConverters
import com.count_out.framework.room.db.speech.SpeechTb
import com.count_out.framework.room.db.speech_kit.SpeechKitTb

data class SetRel(
    @Embedded val parentTb: SetTb,
    @param:TypeConverters(SetConverter::class)
    @Relation(parentColumn = "idSet", entityColumn = "setId", entity = SpeechTb::class) val speeches: List<SpeechTb>
)
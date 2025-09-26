package com.count_out.framework.room.db.set

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.TypeConverters
import com.count_out.framework.room.db.speech.SpeechTb

data class SetRel(
    @Embedded val parentTb: SetTb,
    @param:TypeConverters(SetConverter::class)
    @Relation(parentColumn = "speechId", entityColumn = "idKit", entity = SpeechTb::class) val speeches: List<SpeechTb>
)
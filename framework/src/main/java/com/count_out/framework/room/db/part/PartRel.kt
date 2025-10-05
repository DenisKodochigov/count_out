package com.count_out.framework.room.db.part

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.TypeConverters
import com.count_out.framework.room.db.ring.RingTb
import com.count_out.framework.room.db.speech.SpeechTb

data class PartRel(
    @Embedded val parentTb: PartTb,
    @param:TypeConverters(PartConverter::class)
    @Relation(parentColumn = "idPart", entityColumn = "partId", entity = RingTb::class) val rings: List<RingTb>,
    @Relation(parentColumn = "idSet", entityColumn = "setId", entity = SpeechTb::class) val speeches: List<SpeechTb>)

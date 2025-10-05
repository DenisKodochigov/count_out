package com.count_out.framework.room.db.plan

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.TypeConverters
import com.count_out.framework.room.db.part.PartTb
import com.count_out.framework.room.db.speech.SpeechTb

data class PlanRel(
    @Embedded val parentTb: PlanTb,
    @param:TypeConverters(PlanConverter::class)
    @Relation(parentColumn = "idPlan", entityColumn = "planId", entity = PartTb::class) val parts: List<PartTb>,
    @Relation(parentColumn = "idSet", entityColumn = "setId", entity = SpeechTb::class) val speeches: List<SpeechTb>)

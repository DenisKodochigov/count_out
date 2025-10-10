package com.count_out.framework.room.db.plan

import androidx.room.Embedded
import androidx.room.Relation
import com.count_out.data.models.PlanDb
import com.count_out.framework.room.db.part.PartRel
import com.count_out.framework.room.db.part.PartTb
import com.count_out.framework.room.db.speech.SpeechTb

data class PlanRel(
    @Embedded val parentTb: PlanTb,
    @Relation(parentColumn = "idPlan", entityColumn = "planId", entity = PartTb::class) val parts: List<PartRel>,
    @Relation(parentColumn = "idPlan", entityColumn = "planId", entity = SpeechTb::class) val speeches: List<SpeechTb>
) {
    fun toTable(): PlanTb {
        this.parentTb.parts = this.parts.map { it.toTable() }
        this.parentTb.speeches = this.speeches
        return this.parentTb
    }
}
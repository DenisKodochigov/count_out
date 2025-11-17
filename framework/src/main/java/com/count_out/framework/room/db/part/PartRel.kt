package com.count_out.framework.room.db.part

import androidx.room.Embedded
import androidx.room.Relation
import com.count_out.framework.room.db.ring.RingRel
import com.count_out.framework.room.db.ring.RingTb
import com.count_out.framework.room.db.speech.SpeechTb

data class PartRel(
    @Embedded val parentTb: PartTb,
    @Relation(parentColumn = "idPart", entityColumn = "partId", entity = RingTb::class) val rings: List<RingRel>,
    @Relation(parentColumn = "idPart", entityColumn = "partId", entity = SpeechTb::class) val speeches: List<SpeechTb>
){
    fun toTable(): PartTb {
        this.parentTb.speeches = this.speeches
        this.parentTb.rings = this.rings.map { it.toTable() }.sortedBy { it.idView }
        return this.parentTb
    }
}

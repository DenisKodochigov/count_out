package com.count_out.framework.room.db.set

import androidx.room.Embedded
import androidx.room.Relation
import com.count_out.framework.room.db.speech.SpeechTb

data class SetRel(
    @Embedded val parentTb: SetTb,
    @Relation(parentColumn = "idSet", entityColumn = "setId", entity = SpeechTb::class) val speeches: List<SpeechTb>
){
    fun toTable(): SetTb {
        parentTb.speeches = speeches
        return parentTb
    }
}
package com.count_out.framework.room.db.ring

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.TypeConverters
import com.count_out.framework.room.db.exercise.ExerciseRel
import com.count_out.framework.room.db.exercise.ExerciseTb
import com.count_out.framework.room.db.speech.SpeechTb

data class RingRel(
    @Embedded val parentTb: RingTb,
    @Relation(parentColumn = "idRing", entityColumn = "ringId", entity = ExerciseTb::class) val exercises: List<ExerciseRel>,
    @Relation(parentColumn = "idRing", entityColumn = "ringId", entity = SpeechTb::class) val speeches: List<SpeechTb>)
{
    fun toTable(): RingTb {
        parentTb.speeches = speeches
        parentTb.exercises = exercises.map { it.toTable() }
        return parentTb
    }
}

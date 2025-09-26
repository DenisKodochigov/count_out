package com.count_out.framework.room.db.ring

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.TypeConverters
import com.count_out.framework.room.db.exercise.ExerciseTb
import com.count_out.framework.room.db.speech.SpeechTb

data class RingRel(
    @Embedded val parentTb: RingTb,
    @param:TypeConverters(RingConverter::class)
    @Relation(parentColumn = "idRing", entityColumn = "ringId", entity = ExerciseTb::class) val exercises: List<ExerciseTb>,
    @Relation(parentColumn = "speechId", entityColumn = "idKit", entity = SpeechTb::class) val speeches: List<SpeechTb>)

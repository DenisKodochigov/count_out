package com.count_out.framework.room.db.ring

import androidx.room.TypeConverter
import com.count_out.framework.room.db.exercise.ExerciseRel
import com.count_out.framework.room.db.exercise.ExerciseTb

class RingConverter {
    @TypeConverter
    fun toTable(relation: RingRel): RingTb {
        relation.parentTb.speeches = relation.speeches
        relation.parentTb.exercises = relation.exercises
        return relation.parentTb
    }
}
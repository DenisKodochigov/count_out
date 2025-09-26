package com.count_out.framework.room.db.exercise

import androidx.room.TypeConverter

class ExerciseConverter {
    @TypeConverter
    fun toTable(relation: ExerciseRel): ExerciseTb {
        relation.parentTb.speeches = relation.speeches
        relation.parentTb.sets = relation.sets
        relation.parentTb.activity = relation.activity
        return relation.parentTb
    }
}
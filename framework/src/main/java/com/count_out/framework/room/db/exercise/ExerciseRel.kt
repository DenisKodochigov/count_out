package com.count_out.framework.room.db.exercise

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.TypeConverters
import com.count_out.framework.room.db.activity.ActivityTb
import com.count_out.framework.room.db.set.SetTb
import com.count_out.framework.room.db.speech.SpeechTb

data class ExerciseRel(
    @Embedded val parentTb: ExerciseTb,
    @param:TypeConverters(ExerciseConverter::class)
    @Relation(parentColumn = "activityId", entityColumn = "idActivity", entity = ActivityTb::class) val activity: ActivityTb,
    @Relation(parentColumn = "idExercise", entityColumn = "exerciseId", entity = SetTb::class) val sets: List<SetTb>,
    @Relation(parentColumn = "idSet", entityColumn = "setId", entity = SpeechTb::class) val speeches: List<SpeechTb>)

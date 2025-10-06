package com.count_out.framework.room.db.exercise

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.TypeConverters
import com.count_out.framework.room.db.activity.ActivityTb
import com.count_out.framework.room.db.set.SetRel
import com.count_out.framework.room.db.set.SetTb
import com.count_out.framework.room.db.speech.SpeechTb

data class ExerciseRel(
    @Embedded val parentTb: ExerciseTb,
    @Relation(parentColumn = "activityId", entityColumn = "idActivity", entity = ActivityTb::class) val activity: ActivityTb,
    @Relation(parentColumn = "idExercise", entityColumn = "exerciseId", entity = SetTb::class) val sets: List<SetRel>,
    @Relation(parentColumn = "idExercise", entityColumn = "exerciseId", entity = SpeechTb::class) val speeches: List<SpeechTb>)
{
    fun toTable(): ExerciseTb {
        parentTb.speeches = speeches
        parentTb.sets = sets.map { it.toTable() }
        parentTb.activity = activity
        return parentTb
    }
}

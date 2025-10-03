package com.count_out.framework.room.db.set

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.Index
import com.count_out.data.models.SetDb
import com.count_out.framework.room.db.exercise.ExerciseTb
import com.count_out.framework.room.db.speech.SpeechTb

@Entity(tableName = "set_tb", 
    primaryKeys = ["idSet"],
    ignoredColumns = ["speeches"],
    indices = [
        Index(value = ["idSet"], unique = true),
        Index(value = ["speechId"], unique = true),
        Index(value = ["exerciseId"])],
    foreignKeys = [ForeignKey(
        entity = ExerciseTb::class,
        parentColumns = ["idExercise"],
        childColumns = ["exerciseId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = NO_ACTION
    )])
data class SetTb (
    override var idSet: Long = 0L,
    override var exerciseId: Long = 0,
    override var name: String = "",
    override var goal: Int = 1,
    override var reps: Int = 0,
    override var durationV: Double = 0.0,
    override var durationU: Int = 1,
    override var distanceV: Double = 0.0,
    override var distanceU: Int = 1,
    override var weightV: Double = 0.0,
    override var weightU: Int = 1,
    override var intervalReps: Double = 0.0,
    override var intensity: Int = 1,
    override var intervalDown: Int = 0,
    override var groupCount: String = "",
    override var timeRestV: Double = 0.0,
    override var timeRestU: Int = 1,
    override var speeches: List<SpeechTb> = emptyList(),
): SetDb
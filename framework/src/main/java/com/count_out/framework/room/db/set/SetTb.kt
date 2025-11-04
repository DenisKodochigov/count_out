package com.count_out.framework.room.db.set

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.Index
import androidx.room.PrimaryKey
import com.count_out.data.models.entity.SetDb
import com.count_out.framework.room.db.exercise.ExerciseTb
import com.count_out.framework.room.db.speech.SpeechTb
import com.count_out.framework.room.db.speech.SpeechTb.Companion.toTb

@Entity(tableName = "set_tb",
    ignoredColumns = ["speeches"],
    indices = [
        Index(value = ["idSet"], unique = true),
        Index(value = ["exerciseId"])],
    foreignKeys = [ForeignKey(
        entity = ExerciseTb::class,
        parentColumns = ["idExercise"],
        childColumns = ["exerciseId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = NO_ACTION
    )])
data class SetTb (
    @PrimaryKey(autoGenerate = true) override var idSet: Long = 0L,
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
): SetDb{
    companion object{
        fun SetDb.toTb(
            idSet: Long = this.idSet,
            exerciseId: Long = this.exerciseId,
            name: String = this.name,
            goal: Int = this.goal,
            reps: Int = this.reps,
            durationV: Double = this.durationV,
            durationU: Int = this.durationU,
            distanceV: Double = this.distanceV,
            distanceU: Int = this.distanceU,
            weightV: Double = this.weightV,
            weightU: Int = this.weightU,
            intervalReps: Double = this.intervalReps,
            intensity: Int = this.intensity,
            intervalDown: Int = this.intervalDown,
            groupCount: String = this.groupCount,
            timeRestV: Double = this.timeRestV,
            timeRestU: Int = this.timeRestU,
            speeches: List<SpeechTb> = this.speeches.map { it.toTb() },
        ) = SetTb(
            idSet = idSet,
            exerciseId = exerciseId,
            name = name,
            reps = reps,
            goal = goal,
            durationV = durationV,
            durationU = durationU,
            distanceV = distanceV,
            distanceU = distanceU,
            weightV = weightV,
            weightU = weightU,
            intervalReps = intervalReps,
            intensity = intensity,
            intervalDown = intervalDown,
            groupCount = groupCount,
            timeRestV = timeRestV,
            timeRestU = timeRestU,
            speeches = speeches,
        )
    }
}
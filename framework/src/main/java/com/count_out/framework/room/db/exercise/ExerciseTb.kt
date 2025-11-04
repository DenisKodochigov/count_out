package com.count_out.framework.room.db.exercise

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.Index
import androidx.room.PrimaryKey
import com.count_out.data.models.entity.ExerciseDb
import com.count_out.framework.room.db.activity.ActivityTb
import com.count_out.framework.room.db.activity.ActivityTb.Companion.toTb
import com.count_out.framework.room.db.ring.RingTb
import com.count_out.framework.room.db.set.SetTb
import com.count_out.framework.room.db.set.SetTb.Companion.toTb
import com.count_out.framework.room.db.speech.SpeechTb
import com.count_out.framework.room.db.speech.SpeechTb.Companion.toTb

@Entity(
    tableName = "exercise_tb",
    ignoredColumns = ["speeches","sets","activity"],
    indices = [
        Index(value = ["idExercise"], unique = true),
        Index(value = ["ringId"])],
    foreignKeys = [ ForeignKey(
            entity = RingTb::class,
            parentColumns = ["idRing"],
            childColumns = ["ringId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = NO_ACTION),
    ])
data class ExerciseTb(
    @PrimaryKey(autoGenerate = true) override var idExercise: Long = 0L,
    override var ringId: Long = 0,
    override var activityId: Long = 0,
    override var idView: Int = 0,
    override var amountSet: Int = 0,
    override var duration: Double = 0.0,
    override var speeches: List<SpeechTb> = emptyList(),
    override var sets: List<SetTb> = emptyList(),
    override var activity: ActivityTb = ActivityTb(),
): ExerciseDb{
    companion object{
        fun ExerciseDb.toTb(
            idExercise: Long = this.idExercise,
            ringId: Long = this.ringId,
            activityId: Long = this.activityId,
            idView: Int = this.idView,
            amountSet: Int = this.amountSet,
            duration: Double = this.duration,
            speeches: List<SpeechTb> = this.speeches.map { it.toTb() },
            sets: List<SetTb> = this.sets.map{ it.toTb()},
            activity: ActivityTb = this.activity.toTb(),
        ) = ExerciseTb (
            idExercise = idExercise,
            ringId = ringId,
            activityId = activityId,
            idView = idView,
            amountSet = amountSet,
            duration = duration,
            speeches = speeches.map { it.toTb() },
            sets = sets.map{ it.toTb()},
            activity = activity.toTb(),
        )
    }
}

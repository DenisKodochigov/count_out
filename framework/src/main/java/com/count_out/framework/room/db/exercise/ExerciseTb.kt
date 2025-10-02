package com.count_out.framework.room.db.exercise

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.Index
import com.count_out.data.models.ExerciseDb
import com.count_out.framework.room.db.activity.ActivityTb
import com.count_out.framework.room.db.ring.RingTb
import com.count_out.framework.room.db.set.SetTb
import com.count_out.framework.room.db.speech.SpeechTb

@Entity(
    tableName = "exercise_tb", 
    primaryKeys = ["idExercise"], 
    ignoredColumns = ["speeches","sets","activity"],
    indices = [
        Index(value = ["idExercise"], unique = true),
        Index(value = ["speechId"], unique = true),
        Index(value = ["ringId"])],
    foreignKeys = [ ForeignKey(
            entity = RingTb::class,
            parentColumns = ["idRing"],
            childColumns = ["ringId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = NO_ACTION),
    ])
data class ExerciseTb(
    override var idExercise: Long = 0L,
    override var ringId: Long = 0,
    override var speechId: Long? = null,
    override var activityId: Long = 0,
    override var idView: Int = 0,
    override var amountSet: Int = 0,
    override var duration: Double = 0.0,
    override var speeches: List<SpeechTb> = emptyList(),
    override var sets: List<SetTb> = emptyList(),
    override var activity: ActivityTb? = null,
): ExerciseDb {
//    constructor(item: ExerciseImplD, speechId: Long = item.speechId, idExercise: Long = item.idExercise): this(
//        idExercise = idExercise,
//        ringId = item.ringId,
//        speechId = speechId,
//        activityId = item.activityId,
//        idView = item.idView,
//    )
}
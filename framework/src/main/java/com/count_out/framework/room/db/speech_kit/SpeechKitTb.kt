package com.count_out.framework.room.db.speech_kit

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.Index
import com.count_out.data.models.SpeechKitDb
import com.count_out.framework.room.db.exercise.ExerciseTb
import com.count_out.framework.room.db.part.PartTb
import com.count_out.framework.room.db.plan.PlanTb
import com.count_out.framework.room.db.ring.RingTb
import com.count_out.framework.room.db.set.SetTb

@Entity(tableName = "speech_kit_tb",
    primaryKeys = ["idSpeechKit"],
    indices = [Index(value = ["idSpeechKit", "idOwner"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = PlanTb::class,
            parentColumns = ["idPlan"],
            childColumns = ["planId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = NO_ACTION),
        ForeignKey(
            entity = PartTb::class,
            parentColumns = ["idPart"],
            childColumns = ["partId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = NO_ACTION
        ),
        ForeignKey(
            entity = RingTb::class,
            parentColumns = ["idRing"],
            childColumns = ["ringId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = NO_ACTION),
        ForeignKey(
            entity = ExerciseTb::class,
            parentColumns = ["idExercise"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = NO_ACTION),
        ForeignKey(
            entity = SetTb::class,
            parentColumns = ["idSet"],
            childColumns = ["setId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = NO_ACTION),])
data class SpeechKitTb(
    override var idSpeechKit: Long = 0,
    override val setId: Long? = null,
    override val exerciseId: Long? = null,
    override val ringId: Long? = null,
    override val partId: Long? = null,
    override val planId: Long? = null,
    ): SpeechKitDb
//@PrimaryKey(autoGenerate = true)
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
    indices = [Index(value = ["idSpeechKit"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = PlanTb::class,
            parentColumns = ["speechId"],
            childColumns = ["idSpeechKit"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = NO_ACTION),
        ForeignKey(
            entity = PartTb::class,
            parentColumns = ["speechId"],
            childColumns = ["idSpeechKit"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = NO_ACTION
        ),
        ForeignKey(
            entity = RingTb::class,
            parentColumns = ["speechId"],
            childColumns = ["idSpeechKit"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = NO_ACTION),
        ForeignKey(
            entity = ExerciseTb::class,
            parentColumns = ["speechId"],
            childColumns = ["idSpeechKit"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = NO_ACTION),
        ForeignKey(
            entity = SetTb::class,
            parentColumns = ["speechId"],
            childColumns = ["idSpeechKit"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = NO_ACTION),])
data class SpeechKitTb(override var idSpeechKit: Long = 0): SpeechKitDb
//@PrimaryKey(autoGenerate = true)
package com.count_out.framework.room.db.ring

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.Index
import com.count_out.data.models.RingDb
import com.count_out.framework.room.db.exercise.ExerciseTb
import com.count_out.framework.room.db.part.PartTb
import com.count_out.framework.room.db.speech.SpeechTb

@Entity(
    tableName = "tb_ring",
    primaryKeys = ["idRing"],
    ignoredColumns = ["speeches","exercises"],
    indices = [
        Index(value = ["idRing"], unique = true),
        Index(value = ["speechId"], unique = true),
        Index(value = ["partId"])],
    foreignKeys = [ForeignKey(
            entity = PartTb::class,
            parentColumns = ["idPart"],
            childColumns = ["partId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = NO_ACTION)
    ])
data class RingTb(
    override var idRing: Long = 0L,
    override var partId: Long = 0L,
    override var speechId: Long = 0,
    override var numberLaps: Int = 0,
    override var amount: Int = 0,
    override var duration: Double = 0.0,
    override var speeches: List<SpeechTb> = emptyList(),
    override var exercises: List<ExerciseTb> = emptyList(),
): RingDb
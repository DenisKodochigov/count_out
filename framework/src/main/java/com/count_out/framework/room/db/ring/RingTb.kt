package com.count_out.framework.room.db.ring

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.Index
import androidx.room.PrimaryKey
import com.count_out.data.models.entity.RingDb
import com.count_out.framework.room.db.exercise.ExerciseTb
import com.count_out.framework.room.db.exercise.ExerciseTb.Companion.toTb
import com.count_out.framework.room.db.part.PartTb
import com.count_out.framework.room.db.speech.SpeechTb
import com.count_out.framework.room.db.speech.SpeechTb.Companion.toTb

@Entity(
    tableName = "tb_ring",
    ignoredColumns = ["speeches","exercises"],
    indices = [Index(value = ["idRing"], unique = true), Index(value = ["partId"])],
    foreignKeys = [ForeignKey(
            entity = PartTb::class,
            parentColumns = ["idPart"],
            childColumns = ["partId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = NO_ACTION)
    ])
data class RingTb(
    @PrimaryKey(autoGenerate = true) override var idRing: Long = 0L,
    override var partId: Long = 0L,
    override var idView: Long = 0,
    override var numberLaps: Int = 0,
    override var amount: Int = 0,
    override var duration: Double = 0.0,
    override var speeches: List<SpeechTb> = emptyList(),
    override var exercises: List<ExerciseTb> = emptyList(),
): RingDb{
    companion object{
        fun RingDb.toTb(
            idRing: Long = this.idRing,
            partId: Long = this.partId,
            idView: Long = this.idView,
            numberLaps: Int = this.numberLaps,
            amount: Int = this.amount,
            duration: Double = this.duration,
            speeches: List<SpeechTb> = this.speeches.map { it.toTb() },
            exercises: List<ExerciseTb> = this.exercises.map{ it.toTb()},
        ) = RingTb(
            idRing = idRing,
            partId = partId,
            idView = idView,
            numberLaps = numberLaps,
            amount = amount,
            duration = duration,
            speeches = speeches,
            exercises = exercises,
        )
    }
}

package com.count_out.framework.room.db.speech

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.Index
import androidx.room.PrimaryKey
import com.count_out.data.models.entity.SpeechDb
import com.count_out.framework.room.db.exercise.ExerciseTb
import com.count_out.framework.room.db.part.PartTb
import com.count_out.framework.room.db.plan.PlanTb
import com.count_out.framework.room.db.ring.RingTb
import com.count_out.framework.room.db.set.SetTb

@Entity(tableName = "speech_tb",
    ignoredColumns = ["addMessage"],
    indices = [
        Index(value = ["idSpeech"], unique = true),
        Index(value = ["planId"]),
        Index(value = ["partId"]),
        Index(value = ["ringId"]),
        Index(value = ["exerciseId"]),
        Index(value = ["setId"]), ],
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
            onUpdate = NO_ACTION),
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
            onUpdate = NO_ACTION),]
)
data class SpeechTb(
    @PrimaryKey(autoGenerate = true) override var idSpeech: Long = 0,
    override var setId: Long? = null,
    override var exerciseId: Long? = null,
    override var ringId: Long? = null,
    override var partId: Long? = null,
    override var planId: Long? = null,
    override var message: String = "",
    override var duration: Long = 0L,
    override var addMessage: String = "",
): SpeechDb{
    companion object{
        fun SpeechDb.toTb() = SpeechTb(
            idSpeech = this.idSpeech,
            setId = this.setId,
            exerciseId = this.exerciseId,
            ringId = this.ringId,
            partId = this.partId,
            planId = this.planId,
            message = this.message,
            duration = this.duration,
            addMessage = this.addMessage,
        )
    }
}

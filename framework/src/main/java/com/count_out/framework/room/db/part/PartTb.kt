package com.count_out.framework.room.db.part

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.Index
import com.count_out.data.models.PartDb
import com.count_out.framework.room.db.plan.PlanTb
import com.count_out.framework.room.db.ring.RingTb
import com.count_out.framework.room.db.speech.SpeechTb

@Entity(
    tableName = "part_tb",
    primaryKeys = ["idPart"],
    ignoredColumns = ["speeches","rings"],
    indices = [
        Index(value = ["idPart"], unique = true),
        Index(value = ["speechId"], unique = true),
        Index(value = ["planId"])],
    foreignKeys = [
        ForeignKey(
            entity = PlanTb::class,
            parentColumns = ["idPlan"],
            childColumns = ["planId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = NO_ACTION)])
data class PartTb(
    override var idPart: Long = 0L,
    override var planId: Long = 0,
    override var speechId: Long? = null,
    override var amount: Int = 0,
    override var duration: Double = 0.0,
    override var speeches: List<SpeechTb> = emptyList(),
    override var rings: List<RingTb> = emptyList(),
): PartDb
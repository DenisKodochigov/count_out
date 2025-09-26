package com.count_out.framework.room.db.plan

import androidx.room.Entity
import androidx.room.Index
import com.count_out.data.models.PlanDb
import com.count_out.framework.room.db.part.PartTb
import com.count_out.framework.room.db.speech.SpeechTb

@Entity(
    tableName = "plan_tb",
    primaryKeys = ["idPlan"],
    ignoredColumns = ["speeches","parts"],
    indices = [Index(value = ["idPlan"], unique = true), Index(value = ["speechId"], unique = true)],
)
data class PlanTb(
    override var idPlan: Long = 0L,
    override var speechId: Long = 0L,
    override var name: String = "",
    override var amountActivity: Int = 0,
    override var speeches: List<SpeechTb> = emptyList(),
    override var parts: List<PartTb> = emptyList(),
): PlanDb
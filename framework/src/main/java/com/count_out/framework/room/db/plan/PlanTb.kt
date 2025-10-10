package com.count_out.framework.room.db.plan

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.count_out.data.models.Data
import com.count_out.data.models.PlanDb
import com.count_out.data.models.throwable.ResultData
import com.count_out.framework.room.db.part.PartTb
import com.count_out.framework.room.db.speech.SpeechTb

@Entity(
    tableName = "plan_tb",
    ignoredColumns = ["speeches","parts"],
    indices = [Index(value = ["idPlan"], unique = true)],
)
data class PlanTb(
    @PrimaryKey(autoGenerate = true) override var idPlan: Long = 0L,
    override var name: String = "",
    override var amountActivity: Int = 0,
    override var speeches: List<SpeechTb> = emptyList(),
    override var parts: List<PartTb> = emptyList(),
): PlanDb()
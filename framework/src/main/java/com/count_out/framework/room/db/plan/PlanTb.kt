package com.count_out.framework.room.db.plan

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.count_out.data.models.entity.PlanDb
import com.count_out.framework.room.db.part.PartTb
import com.count_out.framework.room.db.part.PartTb.Companion.toTb
import com.count_out.framework.room.db.speech.SpeechTb
import com.count_out.framework.room.db.speech.SpeechTb.Companion.toTb

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
): PlanDb{
    companion object{
        fun PlanDb.toTb() = PlanTb(
            idPlan = this.idPlan,
            name = this.name,
            amountActivity = this.amountActivity,
            speeches = this.speeches.map { it.toTb() },
            parts = this.parts.map { it.toTb() },
        )
    }
}
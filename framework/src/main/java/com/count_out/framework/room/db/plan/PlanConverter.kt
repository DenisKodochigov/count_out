package com.count_out.framework.room.db.plan

import androidx.room.TypeConverter
import com.count_out.framework.room.db.part.PartRel
import com.count_out.framework.room.db.part.PartTb

class PlanConverter {
    @TypeConverter
    fun toTable(relation: PlanRel): PlanTb {
        relation.parentTb.speeches = relation.speeches
        relation.parentTb.parts = relation.parts
        return relation.parentTb
    }
}
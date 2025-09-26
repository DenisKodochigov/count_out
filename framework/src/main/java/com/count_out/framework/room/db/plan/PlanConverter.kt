package com.count_out.framework.room.db.plan

import androidx.room.TypeConverter

class PlanConverter {
    @TypeConverter
    fun toTable(relation: PlanRel): PlanTb {
        relation.parentTb.speeches = relation.speeches
        relation.parentTb.parts = relation.parts
        return relation.parentTb
    }
}
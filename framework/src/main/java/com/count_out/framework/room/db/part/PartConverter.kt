package com.count_out.framework.room.db.part

import androidx.room.TypeConverter

class PartConverter {
    @TypeConverter
    fun toTable(relation: PartRel): PartTb {
        relation.parentTb.speeches = relation.speeches
        relation.parentTb.rings = relation.rings
        return relation.parentTb
    }
}
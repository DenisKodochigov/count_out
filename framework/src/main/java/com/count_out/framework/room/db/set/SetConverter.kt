package com.count_out.framework.room.db.set

import androidx.room.TypeConverter

class SetConverter {
    @TypeConverter
    fun toTable(relation: SetRel): SetTb {
        relation.parentTb.speeches = relation.speeches
        return relation.parentTb
    }
}
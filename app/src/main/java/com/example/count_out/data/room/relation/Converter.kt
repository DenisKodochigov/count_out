package com.example.count_out.data.room.relation

import androidx.room.TypeConverter
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.Zone

class ConverterZone
{
    @TypeConverter
    fun fromZone(priority: Zone): String { return priority.name }
    @TypeConverter
    fun toZone(priority: String): Zone { return Zone.valueOf(priority) }
}
class ConverterGoalSet
{
    @TypeConverter
    fun fromGoalSet(priority: GoalSet): String { return priority.name }
    @TypeConverter
    fun toGoalSet(priority: String): GoalSet { return GoalSet.valueOf(priority) }
}
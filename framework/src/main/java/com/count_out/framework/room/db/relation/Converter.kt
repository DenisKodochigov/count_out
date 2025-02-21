package com.count_out.framework.room.db.relation

import androidx.room.TypeConverter
import com.count_out.framework.room.entity.DistanceE
import com.count_out.framework.room.entity.GoalSet
import com.count_out.framework.room.entity.TimeE
import com.count_out.framework.room.entity.WeightE
import com.count_out.framework.room.entity.Zone

class ConverterZone {
    @TypeConverter
    fun fromZone(priority: Zone): String { return priority.name }
    @TypeConverter
    fun toZone(priority: String): Zone { return Zone.valueOf(priority) }
}
class ConverterGoalSet {
    @TypeConverter
    fun fromGoalSet(priority: GoalSet): String { return priority.name }
    @TypeConverter
    fun toGoalSet(priority: String): GoalSet { return GoalSet.valueOf(priority) }
}
class ConverterDistanceE {
    @TypeConverter
    fun fromDistanceE(priority: DistanceE): String { return priority.name }
    @TypeConverter
    fun toDistanceE(priority: String): DistanceE { return DistanceE.valueOf(priority) }
}
class ConverterTimeE {
    @TypeConverter
    fun fromTimeE(priority: TimeE): String { return priority.name }
    @TypeConverter
    fun toTimeE(priority: String): TimeE { return TimeE.valueOf(priority) }
}
class ConverterWeightE {
    @TypeConverter
    fun fromWeightE(priority: WeightE): String { return priority.name }
    @TypeConverter
    fun toWeightE(priority: String): WeightE { return WeightE.valueOf(priority) }
}
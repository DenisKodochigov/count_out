package com.count_out.domain.entity.workout

import com.count_out.domain.entity.enums.Units

interface Parameter {
    val value: Double
    val unit: Units
//    abstract fun valueString(stringRes: (Int)->String): String
    companion object{
        val EMPTY = object: Parameter{
            override val value: Double = 0.0
            override val unit: Units = Units.MT
        }
        fun fill(value: Double, unit: Int) = object: Parameter{
            override val value: Double = value
            override val unit: Units = Units.entries[unit]
        }
    }
}
package com.count_out.domain.entity.workout

import com.count_out.domain.entity.discard
import com.count_out.domain.entity.enums.Units
import com.count_out.domain.entity.toDoubleMy

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
        fun fill(value: Double, unit: Units) = object: Parameter{
            override val value: Double = value
            override val unit: Units = unit
        }
        fun changeUnit(parameter: Parameter): Parameter {
            var value = 0.0
            var unit = Units.S
            when(parameter.unit){
                Units.GR -> { value = (parameter.value * 0.001 ).discard(2); unit = Units.KG }
                Units.KG -> { value = (parameter.value *  1000.0).discard(2); unit = Units.GR }
                Units.KM -> { value = (parameter.value *  1000.0).discard(2); unit = Units.MT }
                Units.MT -> { value = (parameter.value *  0.001).discard(2); unit = Units.KM }
                Units.S -> { value = (parameter.value / 60.0).discard(2); unit = Units.M }
                Units.M -> { value = (parameter.value *  60.0).discard(2); unit = Units.S }
                else -> Parameter.EMPTY
            }
            return object: Parameter{
                override val value: Double = value
                override val unit: Units = unit
            }
        }
        fun changeValue(parameter: Parameter, value: String): Parameter {
            return object: Parameter{
                override val value: Double = value.toDoubleMy()
                override val unit: Units = parameter.unit
            }
        }
    }
}
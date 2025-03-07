package com.example.count_out.entity.models

import com.example.count_out.entity.enums.Units
import com.example.count_out.entity.workout.Parameter

data class ParameterImpl(
    override val value: Double,
    override val unit: Units
): Parameter {
//    override fun valueString(stringRes: (Int)->String): String {
//        return when(unit){
//            Units.GR -> "${value.toInt()}/${stringRes(unit.id)}"
//            Units.H -> TODO()
//            Units.M -> TODO()
//            Units.S -> TODO()
//            Units.KM -> TODO()
//            Units.MT -> TODO()
//            Units.KG -> TODO()
//            Units.H -> TODO()
//        }
//    }
}

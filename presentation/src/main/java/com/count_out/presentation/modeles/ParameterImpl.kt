package com.count_out.presentation.modeles

import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.enums.Units

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

package com.example.count_out.presentation.models

import com.example.domain.entity.Parameter
import com.example.domain.entity.enums.Units

data class ParameterImpl(
    override val value: Double,
    override val unit: Units
):Parameter {
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

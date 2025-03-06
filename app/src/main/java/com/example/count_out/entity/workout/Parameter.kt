package com.example.count_out.entity.workout

import com.example.count_out.entity.enums.Units


interface Parameter {
    val value: Double
    val unit: Units
//    abstract fun valueString(stringRes: (Int)->String): String
}
package com.count_out.entity.entity.workout

import com.count_out.entity.enums.Units

interface Parameter {
    val value: Double
    val unit: Units
//    abstract fun valueString(stringRes: (Int)->String): String
}
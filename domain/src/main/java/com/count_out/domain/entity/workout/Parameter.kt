package com.count_out.domain.entity.workout

import com.count_out.domain.entity.enums.Units

interface Parameter {
    val value: Double
    val unit: Units
//    abstract fun valueString(stringRes: (Int)->String): String
}
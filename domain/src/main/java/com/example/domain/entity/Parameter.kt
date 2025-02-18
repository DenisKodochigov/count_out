package com.example.domain.entity

import com.example.domain.entity.enums.Units

interface Parameter {
    val value: Double
    val unit: Units
//    abstract fun valueString(stringRes: (Int)->String): String
}
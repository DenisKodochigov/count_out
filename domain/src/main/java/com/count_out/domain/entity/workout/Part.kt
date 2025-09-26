package com.count_out.domain.entity.workout

import com.count_out.domain.entity.enums.PartName

interface Part: Element  {
    val idPart: Long
    val planId: Long
    val name: PartName
    val rings: List<Ring>
    val amount: Int
    val duration: Parameter
    val speechId: Long
    val speechKit: SpeechKit
}
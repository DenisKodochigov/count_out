package com.count_out.domain.entity.workout

import com.count_out.domain.entity.enums.PartName

interface Part: Domain  {
    val idPart: Long
    val planId: Long
    val name: PartName
    val rings: List<Ring>
    val amount: Int
    val duration: Parameter
    val speechKit: SpeechKit
    companion object{
        val EMPTY = object: Part{
            override val idPart: Long = 0
            override val planId: Long = 0
            override val name: PartName = PartName.WorkDown
            override val rings: List<Ring> = emptyList()
            override val amount: Int = 0
            override val duration: Parameter = Parameter.EMPTY
            override val speechKit: SpeechKit = SpeechKit.EMPTY
        }
    }
}
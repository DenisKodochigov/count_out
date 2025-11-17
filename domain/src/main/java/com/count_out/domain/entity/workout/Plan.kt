package com.count_out.domain.entity.workout

interface Plan: Domain {
    val idPlan: Long
    val idView: Int
    val name: String
    val amountActivity: Int
    val parts: List<Part>
    val speechKit: SpeechKit
    companion object{
        val EMPTY = object: Plan{
            override val idPlan: Long = 0
            override val idView: Int = 0
            override val name: String = ""
            override val amountActivity: Int = 0
            override val parts: List<Part> = emptyList()
            override val speechKit: SpeechKit = SpeechKit.EMPTY
        }
    }
}

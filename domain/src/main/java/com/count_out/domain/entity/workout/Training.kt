package com.count_out.domain.entity.workout

interface Training: Element {
    val idTraining: Long
    val name: String
    val amountActivity: Int
    val rounds: List<Round>
    val rings: List<Ring>
    val isSelected: Boolean
    var speechId: Long
    var speech: SpeechKit?
}

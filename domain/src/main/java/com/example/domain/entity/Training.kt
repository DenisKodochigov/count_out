package com.example.domain.entity

interface Training {
    val idTraining: Long
    val name: String
    val amountActivity: Int
    val rounds: List<Round>
    val isSelected: Boolean
    var speechId: Long
    var speech: SpeechKit?
}

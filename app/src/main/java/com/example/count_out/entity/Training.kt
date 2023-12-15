package com.example.count_out.entity

interface Training {
    val idTraining: Long
    val name: String
    val amountActivity: Int
    val rounds: List<Round>
    val isSelected: Boolean
    val speechId: Long
    val speechActivity: SpeechActivity
}

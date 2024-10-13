package com.example.count_out.entity.workout

import com.example.count_out.entity.speech.SpeechKit

interface Training {
    val idTraining: Long
    val name: String
    val amountActivity: Int
    val rounds: List<Round>
    val isSelected: Boolean
    var speechId: Long
    var speech: SpeechKit
}

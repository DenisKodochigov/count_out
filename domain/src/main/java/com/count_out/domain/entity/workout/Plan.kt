package com.count_out.domain.entity.workout

interface Plan: Domain {
    val idPlan: Long
    val name: String
    val amountActivity: Int
    val parts: List<Part>
    val speechKit: SpeechKit
}

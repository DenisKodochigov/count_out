package com.count_out.domain.entity.workout

data class Training(
    val idTraining: Long = 0L,
    val name: String = "",
    val amountActivity: Int = 0,
    val rounds: List<Round> = emptyList(),
    val rings: List<Ring> = emptyList(),
    val isSelected: Boolean = false,
    var speechId: Long = 0L,
    var speech: SpeechKit? = null,
): Element
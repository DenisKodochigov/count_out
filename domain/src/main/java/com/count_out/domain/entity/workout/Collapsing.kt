package com.count_out.domain.entity.workout

data class Collapsing(
    val sets: List<Long> = emptyList(),
    val rings: List<Long> = emptyList(),
    val rounds: List<Long> = emptyList(),
    val exercises: List<Long> = emptyList(),
    var item: Element? = null
)

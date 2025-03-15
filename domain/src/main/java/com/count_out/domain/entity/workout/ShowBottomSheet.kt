package com.count_out.domain.entity.workout

data class ShowBottomSheet(
    val training: Boolean = false,
    val workUp: Boolean = false,
    val workOut: Boolean = false,
    val workDown: Boolean = false,
    val ring: Boolean = false,
    val exercise: Boolean = false,
    val set: Boolean = false,
    val selectActivity: Boolean = false,
    val element: Element? = null
)

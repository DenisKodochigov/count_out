package com.count_out.domain.entity.workout

data class ShowBottomSheet(
    val plan: Boolean = false,
    val workUp: Boolean = false,
    val workOut: Boolean = false,
    val workDown: Boolean = false,
    val ring: Boolean = false,
    val exercise: Boolean = false,
    val set: Boolean = false,
    val activity: Boolean = false,
    val selectBleDevice: Boolean = false,
    val show: Boolean = false,
    val element: Element? = null
)

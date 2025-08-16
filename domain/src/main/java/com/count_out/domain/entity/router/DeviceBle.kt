package com.count_out.domain.entity.router

import com.count_out.domain.entity.workout.Element

interface DeviceBle: Element {
    val name: String
    val address: String
}

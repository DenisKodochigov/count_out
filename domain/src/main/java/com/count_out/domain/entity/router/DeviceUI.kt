package com.count_out.domain.entity.router

import com.count_out.domain.entity.workout.Element

interface DeviceUI: Element {
    var name: String
    var address: String
}

package com.count_out.domain.entity.bluetooth

import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.workout.Domain

@JvmInline
value class BleDataMap(val item: Map<String, DeviceBle>): Domain
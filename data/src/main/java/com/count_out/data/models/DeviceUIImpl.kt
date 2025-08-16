package com.count_out.data.models

import com.count_out.domain.entity.router.DeviceBle

data class DeviceUIImpl (
    override var name: String = "", override var address: String =""
): DeviceBle
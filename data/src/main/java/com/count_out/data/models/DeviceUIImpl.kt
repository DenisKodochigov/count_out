package com.count_out.data.models

import com.count_out.entity.entity.router.DeviceUI

data class DeviceUIImpl (
    override var name: String = "", override var address: String =""
): DeviceUI
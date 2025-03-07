package com.example.count_out.entity.models

import com.example.count_out.entity.bluetooth.DeviceUI

data class DeviceUIImpl (
    override var name: String = "", override var address: String =""
): DeviceUI
package com.count_out.data.models.types_data

import com.count_out.data.models.Data

interface DeviceBleDb: Data {
    val name: String
    val address: String
}
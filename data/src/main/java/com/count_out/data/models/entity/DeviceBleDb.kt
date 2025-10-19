package com.count_out.data.models.entity

import com.count_out.data.models.Data

interface DeviceBleDb: Data {
    val name: String
    val address: String
}
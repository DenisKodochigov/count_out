package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.domain.entity.router.DeviceBle

interface DeviceBleDb: Data {
    val name: String
    val address: String
    override fun toDomain(ind: Int) = object: DeviceBle {
        override val name: String = this@DeviceBleDb.name
        override val address: String = this@DeviceBleDb.address
    }
    companion object{
        fun create(name: String, address: String) = object: DeviceBleDb {
            override val name: String = name
            override val address: String = address
        }
    }

}
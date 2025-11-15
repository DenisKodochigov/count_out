package com.count_out.domain.entity.router

import com.count_out.domain.entity.workout.Domain

interface DeviceBle: Domain {
    val name: String
    val address: String
    companion object{
        val EMPTY = object: DeviceBle{
            override val name = ""
            override val address = ""
        }
    }
}

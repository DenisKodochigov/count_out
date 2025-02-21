package com.count_out.framework.room.entity

enum class Zone(val id: Int, var maxPulse: Int){
    EXTRA_SLOW( id = 1, maxPulse = 100),
    SLOW(id = 2, maxPulse = 120),
    MEDIUM(id = 3, maxPulse = 135),
    HIGH(id = 4, maxPulse = 160),
    MAX(id = 5, maxPulse = 180)
}
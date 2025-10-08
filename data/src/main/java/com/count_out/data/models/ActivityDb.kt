package com.count_out.data.models

interface ActivityDb:Data {
    val idActivity: Long
    val name: String
    val description: String
    val icon: Int
    val color: Int
    val videoClip: String
    val audioTrack: String
}
package com.example.count_out.entity.workout

interface Activity {
    val idActivity: Long
    val name: String
    val description: String
    val icon: Int
    val color: Int
    val videoClip: String
    val audioTrack: String
}
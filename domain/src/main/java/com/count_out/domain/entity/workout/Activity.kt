package com.count_out.domain.entity.workout

interface Activity: Domain {
    val idActivity: Long
    val name: String
    val description: String
    val icon: Int
    val color: Int
    val videoClip: String
    val audioTrack: String
    companion object{
        val EMPTY = object: Activity{
            override val idActivity: Long = 0
            override val name: String = ""
            override val description: String = ""
            override val icon: Int = 0
            override val color: Int = 0
            override val videoClip: String = ""
            override val audioTrack: String = ""
        }
    }
}
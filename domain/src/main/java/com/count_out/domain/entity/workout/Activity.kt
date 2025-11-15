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
            override val idActivity: Long = 1
            override val name: String = ""
            override val description: String = ""
            override val icon: Int = 0
            override val color: Int = 0
            override val videoClip: String = ""
            override val audioTrack: String = ""
        }
        fun Activity.copy(
            idActivity: Long = this@copy.idActivity,
            name:String = this@copy.name,
            description: String = this@copy.description,
            icon: Int = this@copy.icon,
            color: Int = this@copy.color,
            videoClip: String = this@copy.videoClip,
            audioTrack: String = this@copy.audioTrack,
        ) = object: Activity{
            override val idActivity: Long = idActivity
            override val name: String = name
            override val description: String = description
            override val icon: Int = icon
            override val color: Int = color
            override val videoClip: String = videoClip
            override val audioTrack: String = audioTrack
        }
    }
}
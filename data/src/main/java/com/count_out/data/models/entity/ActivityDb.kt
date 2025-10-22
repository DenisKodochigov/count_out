package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Domain

interface ActivityDb: Data {
    val idActivity: Long
    val name: String
    val description: String
    val icon: Int
    val color: Int
    val videoClip: String
    val audioTrack: String
    override fun toDomain(ind: Int) = object: Activity {
        override val idActivity: Long = this@ActivityDb.idActivity
        override val name: String = this@ActivityDb.name
        override val description: String = this@ActivityDb.description
        override val icon: Int = this@ActivityDb.icon
        override val color: Int = this@ActivityDb.color
        override val videoClip: String = this@ActivityDb.videoClip
        override val audioTrack: String = this@ActivityDb.audioTrack
    }

    companion object {
        fun fromDomain(domain: Domain): ActivityDb {
            return when (domain) {
                is Activity -> object: ActivityDb{
                    override val idActivity: Long = domain.idActivity
                    override val name: String = domain.name
                    override val description: String = domain.description
                    override val icon: Int = domain.icon
                    override val color: Int = domain.color
                    override val videoClip: String = domain.videoClip
                    override val audioTrack: String = domain.audioTrack
                }
                else -> EMPTY
            }
        }
        val EMPTY = object: ActivityDb{
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
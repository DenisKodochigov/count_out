package com.count_out.data.models

import com.count_out.data.models.throwable.ResultData
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Domain

abstract class ActivityDb:Data {
    abstract val idActivity: Long
    abstract val name: String
    abstract val description: String
    abstract val icon: Int
    abstract val color: Int
    abstract val videoClip: String
    abstract val audioTrack: String
    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
    override fun toDomain(ind: Int) = object: Activity{
        override val idActivity: Long = this@ActivityDb.idActivity
        override val name: String = this@ActivityDb.name
        override val description: String = this@ActivityDb.description
        override val icon: Int = this@ActivityDb.icon
        override val color: Int = this@ActivityDb.color
        override val videoClip: String = this@ActivityDb.videoClip
        override val audioTrack: String = this@ActivityDb.audioTrack
    }
}
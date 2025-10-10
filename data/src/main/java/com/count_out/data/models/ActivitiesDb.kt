package com.count_out.data.models

import com.count_out.data.models.throwable.ResultData
import com.count_out.domain.entity.workout.Activities
import com.count_out.domain.entity.workout.Activity

abstract class ActivitiesDb:Data {
    abstract val activities: List<ActivityDb>
    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
    override fun toDomain(ind: Int) = object: Activities{
        override val activities: List<Activity> = this@ActivitiesDb.activities.map { it.toDomain(0) }
    }
}
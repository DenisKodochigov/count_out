package com.count_out.data.models.types_data

import com.count_out.data.models.entity.ActivityDb
import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.domain.entity.workout.Activities
import com.count_out.domain.entity.workout.Activity

abstract class ActivitiesDb: Data {
    abstract val activities: List<ActivityDb>
    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
    override fun toDomain(ind: Int) = object: Activities {
        override val activities: List<Activity> = this@ActivitiesDb.activities.map { it.toDomain(0) }
    }
}
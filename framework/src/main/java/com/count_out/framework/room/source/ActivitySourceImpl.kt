package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.entity.ActivitiesDb
import com.count_out.data.models.entity.ActivityDb
import com.count_out.data.models.entity.LongDb
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.source.room.ActivitySource
import com.count_out.framework.room.db.activity.ActivityDao
import com.count_out.framework.room.db.activity.ActivityTb.Companion.toTb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ActivitySourceImpl @Inject constructor(private val dao: ActivityDao): ActivitySource, PrimeSource() {

    override fun gets(): Flow<ResultData<Data>> =
        dao.gets().map { list->
            if(list.isNotEmpty()) ResultData.Success(object: ActivitiesDb{
                override val activities: List<ActivityDb> = list})
            else ResultData.Error(ThrowableDS.ErrorActivities())
        }

    override fun get(activity: Data): Flow<ResultData<Data>> {
        return activity.safeUseFlow<ActivityDb, ActivityDb> { dao.get(it.idActivity) } }

    override fun copy(activity: Data): ResultData<Data> =
        activity.safeUse<ActivityDb, Long> { item-> dao.insert(item.toTb()) }

    override fun update(activity: Data): ResultData<Data> =
        activity.safeUse<ActivityDb, Long> { item-> dao.update(item.toTb()).toLong() }

    override fun del(idActivity: Data): ResultData<Data>{
        return idActivity.safeUse<LongDb, Long> { id->
            if( dao.checkExerciseWithActivity(id.item) == null)
                dao.del(id.item).toLong()
            else 0
        }
    }
}

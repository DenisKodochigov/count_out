package com.count_out.framework.room.source

import com.count_out.data.models.ActivityImpl
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.source.SourceData
import com.count_out.data.source.room.ActivitySource
import com.count_out.framework.room.db.activity.ActivityDao
import com.count_out.framework.room.db.activity.ActivityTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ActivitySourceImpl @Inject constructor(private val dao: ActivityDao): ActivitySource, SourceData() {
    override fun gets(): Flow<ResultSource<List<ActivityImpl>>> =
        dao.gets().map { list-> list.map { it.toActivity() }}.resultSource()

    override fun get(id: Long): Flow<ResultSource<ActivityImpl>> =
        dao.get(id).map { it.toActivity() }.resultSource()

    override fun copy(activity: ActivityImpl): Flow<ResultSource<Long>> =
        flow { emit(ResultSource.Success(data = dao.add(ActivityTable(activity)))) }

    override fun update(activity: ActivityImpl): Flow<ResultSource<ActivityImpl>> {
        return dao.update(ActivityTable(activity))?.let {
            dao.get(activity.idActivity).map { it.toActivity() }.resultSource()
        } ?: flow { emit (resultNullException()) }
    }

    override fun del(id: Long): Flow<ResultSource<Int>> =
        flow { emit (getResult{ dao.del(id) })}

    override fun delWithCheck(id: Long) {
        if(dao.checkExerciseWithActivity(id) == null) { dao.del(id) }
    }
}
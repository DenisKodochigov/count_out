package com.count_out.framework.room.source

import com.count_out.data.models.ActivityImpl
import com.count_out.data.source.room.ActivitySource
import com.count_out.framework.room.db.activity.ActivityDao
import com.count_out.framework.room.db.activity.ActivityTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ActivitySourceImpl @Inject constructor(private val dao: ActivityDao): ActivitySource {
    override fun gets(): Flow<List<ActivityImpl>> = dao.gets().map { list-> list.map { it.toActivity() }}

    override fun get(id: Long): Flow<ActivityImpl> = dao.get(id).map { it.toActivity() }

    override fun copy(activity: ActivityImpl): Long = dao.add(toActivityTable(activity))

    override fun update(activity: ActivityImpl) { dao.update( toActivityTable(activity)) }

    override fun del(id: Long) { dao.del(id) }

    override fun delWithCheck(id: Long) {
        if(dao.checkExerciseWithActivity(id) == null) { dao.del(id) }
    }

    private fun toActivityTable( item: ActivityImpl) = ActivityTable (
        idActivity = item.idActivity,
        name = item.name,
        description = item.description,
        icon = item.icon,
        color = item.color,
        videoClip = item.videoClip,
        audioTrack = item.audioTrack
    )
}
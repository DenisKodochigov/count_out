package com.count_out.data.repository

import com.count_out.data.models.ActivityImpl
import com.count_out.data.source.room.ActivitySource
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.repository.trainings.ActivityRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ActivityRepoImpl @Inject constructor(private val activitySource: ActivitySource): ActivityRepo {
    override fun gets(): Flow<List<Activity>> = activitySource.gets()
    override fun get(id: Long): Flow<Activity> = activitySource.get(id)
    override fun del(activity: Activity): Flow<Boolean> {
        activitySource.del(Activity.idActivity)
        return flow { emit(true) }
    }
    override fun copy(activity: Activity): Flow<Activity> = activitySource.copy(activity as ActivityImpl)
    override fun add(activity: Activity): Flow<Activity> = activitySource.add(activity as ActivityImpl)
    override fun update(activity: Activity): Flow<Activity>  = activitySource.update(activity as ActivityImpl)
}
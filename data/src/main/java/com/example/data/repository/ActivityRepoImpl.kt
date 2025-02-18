package com.example.data.repository

import com.example.data.entity.ActivityImpl
import com.example.data.source.room.ActivitySource
import com.example.domain.entity.Activity
import com.example.domain.repository.trainings.ActivityRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActivityRepoImpl @Inject constructor(private val activitySource: ActivitySource): ActivityRepo {
    override fun gets(): Flow<List<Activity>> = activitySource.gets()

    override fun get(id: Long): Flow<Activity> = activitySource.get(id)

    override fun del(id: Long) { activitySource.delWithCheck(id) }

    override fun copy(activity: Activity): Flow<Activity> = activitySource.copy(activity as ActivityImpl)

    override fun add(activity: Activity): Flow<Activity>  = activitySource.add(activity as ActivityImpl)

    override fun update(activity: Activity): Flow<Activity>  = activitySource.update(activity as ActivityImpl)
}
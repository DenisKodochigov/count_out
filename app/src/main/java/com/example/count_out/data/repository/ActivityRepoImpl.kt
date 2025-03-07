package com.example.count_out.data.repository

import com.example.count_out.domain.repository.trainings.ActivityRepo
import com.example.count_out.data.source.room.ActivitySource
import com.example.count_out.entity.models.ActivityImpl
import com.example.count_out.entity.workout.Activity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ActivityRepoImpl @Inject constructor(private val source: ActivitySource): ActivityRepo {
    override fun gets(): Flow<List<Activity>> = source.gets()
    override fun get(id: Long): Flow<Activity> = source.get(id)
    override fun del(activity: Activity): Flow<Boolean> {
        source.del(activity.idActivity)
        return flow { emit(true) }
    }
    override fun copy(activity: Activity): Flow<Activity> = source.copy(activity as ActivityImpl)
    override fun add(activity: Activity): Flow<Activity> = source.add(activity as ActivityImpl)
    override fun update(activity: Activity): Flow<Activity>  = source.update(activity as ActivityImpl)
}
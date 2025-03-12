package com.count_out.domain.repository.trainings

import com.count_out.domain.entity.workout.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityRepo{
    fun gets(): Flow<List<Activity>>
    fun get(id: Long): Flow<Activity>
    fun del(activity: Activity): Flow<Boolean>
    fun copy(activity: Activity): Flow<Activity>
    fun update(activity: Activity): Flow<Activity>
}
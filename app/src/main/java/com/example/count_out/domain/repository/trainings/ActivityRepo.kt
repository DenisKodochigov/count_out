package com.example.count_out.domain.repository.trainings

import com.example.count_out.entity.workout.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityRepo{
    fun gets(): Flow<List<Activity>>
    fun get(id: Long): Flow<Activity>
    fun del(activity: Activity): Flow<Boolean>
    fun copy(activity: Activity): Flow<Activity>
    fun add(activity: Activity): Flow<Activity>
    fun update(activity: Activity): Flow<Activity>
}
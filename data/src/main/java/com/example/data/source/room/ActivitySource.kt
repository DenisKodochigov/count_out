package com.example.data.source.room

import com.example.data.entity.ActivityImpl
import kotlinx.coroutines.flow.Flow

interface ActivitySource {
    fun gets(): Flow<List<ActivityImpl>>
    fun get(id: Long): Flow<ActivityImpl>
    fun add(activity: ActivityImpl): Flow<ActivityImpl>
    fun copy(activity: ActivityImpl): Flow<ActivityImpl>
    fun update(activity: ActivityImpl): Flow<ActivityImpl>
    fun del(id: Long)
    fun delWithCheck(id: Long)
}
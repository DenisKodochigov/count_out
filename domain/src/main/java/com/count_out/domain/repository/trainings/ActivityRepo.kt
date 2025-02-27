package com.count_out.domain.repository.trainings

import com.count_out.domain.entity.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityRepo{
    fun gets(): Flow<List<Activity>>
    fun get(id: Long): Flow<Activity>
    fun del(activity: Activity): Flow<Boolean>
    fun copy(activity: Activity): Flow<Activity>
    fun add(activity: Activity): Flow<Activity>
    fun update(activity: Activity): Flow<Activity>


//    override fun <Activity> gets(): Flow<List<Activity>>
//    override fun <Activity> get(): Flow<Activity>
//    override fun <Activity> add(item: Activity): Flow<Activity>
//    override fun <Activity> copy(id: Long): Flow<Activity>
//    override fun <Activity> update(item: Activity): Flow<Activity>
//    override fun del(id: Long)
}
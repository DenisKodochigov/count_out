package com.count_out.data.repository

import com.count_out.data.source.room.ActivitySource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.repository.plans.ActivityRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActivityRepoImpl @Inject constructor( private val source: ActivitySource): ActivityRepo, PrimeRepo() {
    override fun gets(): Flow<ResultUC<TypeRepo>> = source.gets().convertor()
    override fun get(activity: TypeRepo): Flow<ResultUC<TypeRepo>> =
        source.get(toTypeSource(activity)).convertor()

    override fun del(activity: TypeRepo): Flow<ResultUC<TypeRepo>> =
        wrapFlow(source.del(toTypeSource(activity)))

    override fun copy(activity: TypeRepo): Flow<ResultUC<TypeRepo>> =
        wrapFlow(source.copy(toTypeSource(activity)))

    override fun update(activity: TypeRepo): Flow<ResultUC<TypeRepo>> =
        wrapFlow(source.update(toTypeSource(activity)))
}
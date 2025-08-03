package com.count_out.data.repository

import com.count_out.data.source.room.ActivitySource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.repository.plans.ActivityRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActivityRepoImpl @Inject constructor(
//    private val converter: ConverterResult,
    private val source: ActivitySource
): ActivityRepo, PrimeRepo() {
    override fun gets(): Flow<ResultUC<TypeRepo>> = source.gets().convertor()
    override fun get(id: TypeRepo): Flow<ResultUC<TypeRepo>> = source.get(toTypeSource(id)).convertor()
    override fun del(activity: TypeRepo): Flow<ResultUC<TypeRepo>> {
        val typeSource = toTypeSource(activity)
        return source.del(typeSource).nextActionOk { source.get(typeSource) }
    }
    override fun copy(activity: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.copy(toTypeSource(activity)).nextAction{ source.gets()}}
    override fun update(activity: TypeRepo): Flow<ResultUC<TypeRepo>> {
        val typeSource = toTypeSource(activity)
        return source.update(typeSource).nextAction{ source.get(typeSource)}}
}
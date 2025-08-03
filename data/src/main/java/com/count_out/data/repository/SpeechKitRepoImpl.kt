package com.count_out.data.repository

import com.count_out.data.source.room.SpeechKitSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.repository.plans.SpeechKitRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SpeechKitRepoImpl @Inject constructor(
    private val source: SpeechKitSource): SpeechKitRepo, PrimeRepo()
{
    override fun get(speechKit: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return source.get(toTypeSource(speechKit)).convertor() }

    override fun del(speechKit: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.del(toTypeSource(speechKit)).nextAction{ source.get(it)}
    }

    override fun copy(speechKit: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.copy(toTypeSource(speechKit)).nextAction{ source.get(it)}
    }

    override fun update(speechKit: TypeRepo): Flow<ResultUC<TypeRepo>> {
        val convertorType = toTypeSource(speechKit)
        return source.update(convertorType).nextActionOk { source.get(convertorType) }
    }
}
package com.count_out.data.repository

import com.count_out.data.models.Data.Companion.toData
import com.count_out.data.models.ResultData.Companion.convertor
import com.count_out.data.source.room.SpeechSource
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.SpeechRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SpeechRepoImpl @Inject constructor(
    private val source: SpeechSource): SpeechRepo
{
    override fun update(speech: Domain): Flow<ResultDomain<Domain>> {
        return source.update(toData(speech)).convertor() }
}


//override fun get(speech: Speech): Flow<ResultUC<Domain>> =
//    source.get(SpeechImplD(speech).idSpeech).map{ converter.execute(it) }
//override fun get(speech: Domain): Flow<ResultUC<Domain>> =
//    source.get(convertorType(speech)).convertor()
//
//override fun copy(speech: Domain): Flow<ResultUC<Domain>> =
//    source.copy(convertorType(speech)).convertor()
//
//override fun update(speech: Domain): Flow<ResultUC<Domain>> =
//    source.update(convertorType(speech)).convertor()
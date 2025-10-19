package com.count_out.data.repository

import com.count_out.data.models.ResultData.Companion.convertor
import com.count_out.data.models.entity.SpeechDb
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
        return source.update(SpeechDb.fromDomain(speech)).convertor() }
}

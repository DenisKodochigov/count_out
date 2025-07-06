package com.count_out.data.repository

import com.count_out.data.entity.ConverterResult
import com.count_out.data.models.SpeechImplD
import com.count_out.data.source.room.SpeechSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Speech
import com.count_out.domain.repository.plans.SpeechRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpeechRepoImpl @Inject constructor(
    private val converter: ConverterResult,
    private val source: SpeechSource): SpeechRepo
{
    override fun get(speech: Speech): Flow<ResultUC<Speech>> =
        source.get(SpeechImplD(speech)).map{ converter.execute(it) }

    override fun copy(speech: Speech): Flow<ResultUC<Speech>> =
        source.copy(SpeechImplD(speech)).map{ converter.execute(it) }

    override fun update(speech: Speech): Flow<ResultUC<Speech>> =
        source.update(SpeechImplD(speech)).map{ converter.execute(it) }

}



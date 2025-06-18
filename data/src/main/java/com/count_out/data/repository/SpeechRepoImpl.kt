package com.count_out.data.repository

import com.count_out.data.entity.ConverterResult
import com.count_out.data.models.SpeechImplD
import com.count_out.data.source.room.SpeechSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Speech
import com.count_out.domain.repository.trainings.SpeechRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpeechRepoImpl @Inject constructor(
    private val converterResult: ConverterResult,
    private val speechSource: SpeechSource): SpeechRepo
{
    override fun get(speech: Speech): Flow<ResultUC<Speech>> =
        speechSource.get(SpeechImplD(speech)).map{ converterResult.execute(it) }

    override fun copy(speech: Speech): Flow<ResultUC<Speech>> =
        speechSource.copy(SpeechImplD(speech)).map{ converterResult.execute(it) }

    override fun update(speech: Speech): Flow<ResultUC<Speech>> =
        speechSource.update(SpeechImplD(speech)).map{ converterResult.execute(it) }

}



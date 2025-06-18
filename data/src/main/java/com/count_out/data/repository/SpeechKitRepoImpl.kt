package com.count_out.data.repository

import com.count_out.data.entity.ConverterResult
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.repository.trainings.SpeechKitRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpeechKitRepoImpl @Inject constructor(
    private val converterResult: ConverterResult,
    private val speechKitSource: SpeechKitSource): SpeechKitRepo
{
    override fun get(speechKit: SpeechKit): Flow<ResultUC<SpeechKit>> {
        return speechKitSource.get(SpeechKitImplD(speechKit)).map{ converterResult.execute(it) }
    }

    override fun del(speechKit: SpeechKit): Flow<ResultUC<Long>> {
        return speechKitSource.del(SpeechKitImplD(speechKit)).map{ converterResult.execute(it) }
    }

    override fun copy(speechKit: SpeechKit): Flow<ResultUC<SpeechKit>> {
        return speechKitSource.copy(SpeechKitImplD(speechKit)).map{ converterResult.execute(it) }
    }

    override fun update(speechKit: SpeechKit): Flow<ResultUC<SpeechKit>> {
        speechKitSource.update(SpeechKitImplD(speechKit))
        return get(speechKit)
    }

}
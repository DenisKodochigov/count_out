package com.count_out.domain.repository.trainings

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.SpeechKit
import kotlinx.coroutines.flow.Flow

interface TestSpeechRepo {
    fun get(id: Long): Flow<ResultUC<SpeechKit>>
    fun del(speechKit: SpeechKit): Flow<ResultUC<SpeechKit>>
    fun add(speechKit: SpeechKit?): Flow<ResultUC<SpeechKit>>
    fun update(speechKit: SpeechKit): Flow<ResultUC<SpeechKit>>
}
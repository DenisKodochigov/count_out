package com.count_out.domain.repository.plans

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.SpeechKit
import kotlinx.coroutines.flow.Flow

interface SpeechKitRepo {
    fun get(speechKit: SpeechKit): Flow<ResultUC<SpeechKit>>
    fun del(speechKit: SpeechKit): Flow<ResultUC<Long>>
    fun copy(speechKit: SpeechKit): Flow<ResultUC<SpeechKit>>
    fun update(speechKit: SpeechKit): Flow<ResultUC<SpeechKit>>
}
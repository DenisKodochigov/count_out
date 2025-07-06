package com.count_out.domain.repository.plans

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Speech
import kotlinx.coroutines.flow.Flow

interface SpeechRepo {
    fun get(speech: Speech): Flow<ResultUC<Speech>>
    fun copy(speech: Speech): Flow<ResultUC<Speech>>
    fun update(speech: Speech): Flow<ResultUC<Speech>>
}
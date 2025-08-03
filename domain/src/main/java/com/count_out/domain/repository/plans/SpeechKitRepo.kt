package com.count_out.domain.repository.plans

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.repository.TypeRepo
import kotlinx.coroutines.flow.Flow

interface SpeechKitRepo {
    fun get(speechKit: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun del(speechKit: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun copy(speechKit: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun update(speechKit: TypeRepo): Flow<ResultUC<TypeRepo>>
}
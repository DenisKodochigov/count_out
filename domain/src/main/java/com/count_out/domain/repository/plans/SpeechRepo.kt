package com.count_out.domain.repository.plans

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import kotlinx.coroutines.flow.Flow

interface SpeechRepo {
    fun get(speech: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun copy(speech: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun update(speech: TypeRepo): Flow<ResultUC<TypeRepo>>
}
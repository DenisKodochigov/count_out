package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.plans.SpeechRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SpeechCore @Inject constructor(private val repo: SpeechRepo): Core() {
//    fun get(speech: TypeRepo): Flow<ResultUC<TypeRepo>>{
//        return repo.get(speech) }
//    fun copy(speech: TypeRepo): Flow<ResultUC<TypeRepo>>{
//        return repo.copy(speech) }
    fun update(speech: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.update(speech) }
}
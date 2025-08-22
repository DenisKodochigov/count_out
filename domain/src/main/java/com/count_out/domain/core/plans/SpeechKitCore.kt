package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.repository.plans.ActivityRepo
import com.count_out.domain.repository.plans.SpeechKitRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SpeechKitCore @Inject constructor(private val repo: SpeechKitRepo): Core() {
    fun get(speechKit: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.get(speechKit) }
    fun del(speechKit: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.del(speechKit) }
    fun copy(speechKit: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.copy(speechKit) }
    fun update(speechKit: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.update(speechKit) }
}
package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.repository.plans.ActivityRepo
import com.count_out.domain.repository.plans.RingRepo
import com.count_out.domain.repository.plans.RoundRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoundCore @Inject constructor(private val repo: RoundRepo): Core() {
    fun get(round: TypeRepo): Flow<ResultUC<TypeRepo>>  {
        return repo.get(round) }
    fun gets(trainingId: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return repo.gets(trainingId) }
    fun update(round: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return repo.update(round) }
    fun del(round: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return repo.del(round) }
}
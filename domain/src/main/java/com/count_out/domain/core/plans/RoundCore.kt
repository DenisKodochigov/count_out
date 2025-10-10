package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.repository.plans.RoundRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoundCore @Inject constructor(private val repo: RoundRepo): Core() {
    fun update(round: TypeRepo): Flow<ResultDomain<TypeRepo>> {
        return repo.update(round) }
    fun del(round: TypeRepo): Flow<ResultDomain<TypeRepo>> {
        return repo.del(round) }
}

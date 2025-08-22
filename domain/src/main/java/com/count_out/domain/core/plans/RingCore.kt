package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.repository.plans.ActivityRepo
import com.count_out.domain.repository.plans.RingRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RingCore @Inject constructor(private val repo: RingRepo): Core() {
    fun get(ring: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.get(ring)
    }
    fun gets(trainingId: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.gets(trainingId)
    }
    fun del(ring: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.del(ring)
    }
    fun copy(ring: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.copy(ring)
    }
    fun update(ring: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.update(ring)
    }
}
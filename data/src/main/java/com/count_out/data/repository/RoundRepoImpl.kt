package com.count_out.data.repository

import com.count_out.data.source.room.RoundSource
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.plans.RoundRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoundRepoImpl @Inject constructor(private val source: RoundSource): RoundRepo, PrimeRepo() {

    override fun update(round: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return wrapFlow(source.update(toTypeSource(round)))
    }

    override fun del(round: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return wrapFlow(source.del(toTypeSource(round)))
    }
}
//    override fun get(round: TypeRepo): Flow<ResultUC<TypeRepo>>{
//        return source.get(toTypeSource(round)).convertor()
//    }
//
//    override fun gets(trainingId: TypeRepo): Flow<ResultUC<TypeRepo>> {
//        return source.gets(toTypeSource(trainingId)).convertor()
//    }
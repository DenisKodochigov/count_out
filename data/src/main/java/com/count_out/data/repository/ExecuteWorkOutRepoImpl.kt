package com.count_out.data.repository

import com.count_out.data.models.LongDb
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.room.PlanSource
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.ExecuteWorkOutRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ExecuteWorkOutRepoImpl @Inject constructor(
    private val source: PlanSource,): ExecuteWorkOutRepo, PrimeRepo()
{
    override fun start(): Flow<ResultUC<TypeRepo>> {
        return flowOf(ResultUC.Success(TypeRepo.BooleanT(item = true)) )}
    override fun stop(): Flow<ResultUC<TypeRepo>> {
        return flowOf(ResultUC.Success(TypeRepo.BooleanT(item = true)) )}
    override fun pause(): Flow<ResultUC<TypeRepo>> {
        return flowOf(ResultUC.Success(TypeRepo.BooleanT(item = true)) )}
    override fun save(): Flow<ResultUC<TypeRepo>> {
        return flowOf(ResultUC.Success(TypeRepo.BooleanT(item = true)) )}
    override fun upInterval(): Flow<ResultUC<TypeRepo>> {
        return flowOf(ResultUC.Success(TypeRepo.BooleanT(item = true)) )}
    override fun downInterval(): Flow<ResultUC<TypeRepo>> {
        return flowOf(ResultUC.Success(TypeRepo.BooleanT(item = true)) )}

    override fun getPlan(): Flow<ResultUC<TypeRepo>> {
        return source.getId(idPlan = TypeSource.LongT(1L)).convertor()
    }
    override fun getPlan1(): Flow<ResultUC<Domain>> {
        return source.getId1(idPlan = LongDb(1L)).convertor1()
    }
}
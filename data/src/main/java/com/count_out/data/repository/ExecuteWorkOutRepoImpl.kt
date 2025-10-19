package com.count_out.data.repository

import com.count_out.data.models.ResultData.Companion.convertorFlow
import com.count_out.data.models.entity.LongDb
import com.count_out.data.source.room.PlanSource
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.types_domai.BooleanDm
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.ExecuteWorkOutRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ExecuteWorkOutRepoImpl @Inject constructor(
    private val source: PlanSource,): ExecuteWorkOutRepo
{
    override fun start(): Flow<ResultDomain<Domain>> {
        return flowOf(ResultDomain.Success(BooleanDm(item = true)) )}
    override fun stop(): Flow<ResultDomain<Domain>> {
        return flowOf(ResultDomain.Success(BooleanDm(item = true)) )}
    override fun pause(): Flow<ResultDomain<Domain>> {
        return flowOf(ResultDomain.Success(BooleanDm(item = true)) )}
    override fun save(): Flow<ResultDomain<Domain>> {
        return flowOf(ResultDomain.Success(BooleanDm(item = true)) )}
    override fun upInterval(): Flow<ResultDomain<Domain>> {
        return flowOf(ResultDomain.Success(BooleanDm(item = true)) )}
    override fun downInterval(): Flow<ResultDomain<Domain>> {
        return flowOf(ResultDomain.Success(BooleanDm(item = true)) )}

    override fun getPlan(): Flow<ResultDomain<Domain>> {
        return source.getId(idPlan = LongDb(1L)).convertorFlow()
    }
}
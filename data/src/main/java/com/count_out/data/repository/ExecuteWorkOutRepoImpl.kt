package com.count_out.data.repository

import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.room.TrainingSource
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.ExecuteWorkOutRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExecuteWorkOutRepoImpl @Inject constructor(
    private val source: TrainingSource,): ExecuteWorkOutRepo, PrimeRepo()
{
    override fun start(): Flow<ResultUC<TypeRepo>> {
        return flow { emit(ResultUC.Success(TypeRepo.BooleanT(item = true)) )}}
    override fun stop(): Flow<ResultUC<TypeRepo>> {
        return flow { emit(ResultUC.Success(TypeRepo.BooleanT(item = true)) )}}
    override fun pause(): Flow<ResultUC<TypeRepo>> {
        return flow { emit(ResultUC.Success(TypeRepo.BooleanT(item = true)) )}}
    override fun save(): Flow<ResultUC<TypeRepo>> {
        return flow { emit(ResultUC.Success(TypeRepo.BooleanT(item = true)) )}}
    override fun upInterval(): Flow<ResultUC<TypeRepo>> {
        return flow { emit(ResultUC.Success(TypeRepo.BooleanT(item = true)) )}}
    override fun downInterval(): Flow<ResultUC<TypeRepo>> {
        return flow { emit(ResultUC.Success(TypeRepo.BooleanT(item = true)) )}}

    override fun getPlan(): Flow<ResultUC<TypeRepo>> = source.getId(id = TypeSource.LongT(1L)).convertor()

}
package com.count_out.domain.core

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.ExecuteWorkOutRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExecuteWorkOutCore @Inject constructor(private val repo: ExecuteWorkOutRepo): Core() {
    fun start(): Flow<ResultDomain<Domain>>{
        return repo.start() }
    fun stop(): Flow<ResultDomain<Domain>>{
        return repo.stop() }
    fun pause(): Flow<ResultDomain<Domain>>{
        return repo.pause() }
    fun save(): Flow<ResultDomain<Domain>>{
        return repo.save() }
    fun upInterval(): Flow<ResultDomain<Domain>>{
        return repo.upInterval() }
    fun downInterval(): Flow<ResultDomain<Domain>>{
        return repo.downInterval() }
    fun getPlan(): Flow<ResultDomain<Domain>>{
        return repo.getPlan() }
}
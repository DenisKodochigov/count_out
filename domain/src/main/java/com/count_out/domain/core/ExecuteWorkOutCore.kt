package com.count_out.domain.core

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.ExecuteWorkOutRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExecuteWorkOutCore @Inject constructor(private val repo: ExecuteWorkOutRepo): Core() {
    //запускается сервис. На входе у него:
    // 1. stepPlan.mutable
    // 2. command on state service
    // 3. New settings
    // на выходе:
    // 1. текущее состояние индекса stepPlana
    // 2. текущее время.
    // 3. текущий остчет
    // 4. текущее растояние
    // 5. Возможность изменять интервал
    // 6. Возможность остановить


    fun start(inputData: Domain): Flow<ResultDomain<Domain>>{
        return repo.start(inputData) }
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
package com.count_out.domain.core

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.ExecuteWorkOutRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExecuteWorkOutCore @Inject constructor(private val repo: ExecuteWorkOutRepo): Core() {
    fun start(): Flow<ResultUC<TypeRepo>>{
        return repo.start() }
    fun stop(): Flow<ResultUC<TypeRepo>>{
        return repo.stop() }
    fun pause(): Flow<ResultUC<TypeRepo>>{
        return repo.pause() }
    fun save(): Flow<ResultUC<TypeRepo>>{
        return repo.save() }
    fun upInterval(): Flow<ResultUC<TypeRepo>>{
        return repo.upInterval() }
    fun downInterval(): Flow<ResultUC<TypeRepo>>{
        return repo.downInterval() }
    fun getPlan(): Flow<ResultUC<TypeRepo>>{
        return repo.getPlan() }
}
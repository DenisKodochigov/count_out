package com.count_out.domain.repository

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Training
import kotlinx.coroutines.flow.Flow

interface ExecuteWorkOutRepo {
    fun start()
    fun stop()
    fun pause()
    fun save()
    fun upInterval()
    fun downInterval()
    fun getPlan(): Flow<ResultUC<Training>>
}
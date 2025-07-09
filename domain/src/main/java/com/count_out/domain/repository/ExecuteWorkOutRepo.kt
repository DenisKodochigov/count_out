package com.count_out.domain.repository

import com.count_out.domain.entity.StepTraining
import com.count_out.domain.entity.throwable.ResultUC
import kotlinx.coroutines.flow.Flow

interface ExecuteWorkOutRepo {
    fun start()
    fun stop()
    fun pause()
    fun save()
    fun upInterval()
    fun downInterval()
    fun getStepPlan(): Flow<ResultUC<StepTraining>>
}
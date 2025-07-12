package com.count_out.data.repository

import com.count_out.data.entity.ConverterResult
import com.count_out.data.source.room.TrainingSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.ExecuteWorkOutRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExecuteWorkOutRepoImpl @Inject constructor(
    private val source: TrainingSource,
    private val converter: ConverterResult): ExecuteWorkOutRepo
{
    override fun start() {}
    override fun stop() {}
    override fun pause() {}
    override fun save() {}
    override fun upInterval() {}
    override fun downInterval() {}

    override fun getPlan(): Flow<ResultUC<Training>> =
        source.get2(id = 1L).map { converter.execute(it) }

}
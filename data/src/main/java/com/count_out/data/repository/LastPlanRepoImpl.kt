package com.count_out.data.repository

import com.count_out.data.entity.ConverterResult
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.source.local.LastPlanSource
import com.count_out.data.source.room.TrainingSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.LastPlanRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LastPlanRepoImpl @Inject constructor(
    private val converter: ConverterResult,
    private val sourceTraining: TrainingSource,
    private val source: LastPlanSource): LastPlanRepo
{
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getLastUsedPlan(): Flow<ResultUC<Training>> {
        return source.getLastPlan().flatMapConcat { resultDataSource->
            when(resultDataSource){
                is ResultSource.Success-> {
                    sourceTraining.get2( resultDataSource.data).map {
                        converter.execute(it) }}
                is ResultSource.Error -> flow {
                    emit(converter.execute(resultDataSource)) }
            }
        }
    }

    override fun saveLastUsedPlan(id: Long): Flow<ResultUC<Boolean>> =
        source.saveLastPlan(id).map { converter.execute(it) }

}
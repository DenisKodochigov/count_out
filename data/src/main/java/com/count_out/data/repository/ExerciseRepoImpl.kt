package com.count_out.data.repository

import com.count_out.data.source.room.ExerciseSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.repository.plans.ExerciseRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExerciseRepoImpl @Inject constructor(
    private val source: ExerciseSource): ExerciseRepo, PrimeRepo() {
    override fun get(exercise: TypeRepo): Flow<ResultUC<TypeRepo>> =
        source.get(toTypeSource(exercise)).convertor()

    override fun del(exercise: TypeRepo): Flow<ResultUC<TypeRepo>> {
        val typeSource = toTypeSource(exercise)
        return source.del(typeSource).nextActionOk { source.get(typeSource) }
    }
    override fun copy(exercise: TypeRepo): Flow<ResultUC<TypeRepo>> {
        val typeSource = toTypeSource(exercise)
        return source.copy(typeSource) .nextAction{ source.get(it)}
    }
    override fun update(exercise: TypeRepo): Flow<ResultUC<TypeRepo>> {
        val typeSource = toTypeSource(exercise)
        return source.update(typeSource).nextActionOk { source.get(typeSource) }
    }
    override fun getForRound(id: TypeRepo): Flow<ResultUC<TypeRepo>> =
        source.getForRound(toTypeSource(id)).convertor()
    override fun getForRing(id: TypeRepo): Flow<ResultUC<TypeRepo>> =
        source.getForRing(toTypeSource(id)).convertor()
    override fun getFilter(list: TypeRepo): Flow<ResultUC<TypeRepo>> =
        source.getFilter(toTypeSource(list)).convertor()

    override fun changeSequenceExercise(item: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return flow { emit(ResultUC.Success(TypeRepo.NullT)) }
    }
//    fun getExercise(exercise: TypeRepo): Flow<ResultSource<TypeSource>>{
//        return if (exercise is TypeRepo.ExerciseT){
//            if (exercise.item.roundId != 0L)
//                exerciseSource.getForRound(TypeSource.LongT(item = exercise.item.roundId))
//            else if (exercise.item.ringId != 0L)
//                exerciseSource.getForRing(TypeSource.LongT(item = exercise.item.roundId))
//            else flow { emit(ResultSource.Error(ThrowableDS.extract(Exception("return null")))) }
//        } else flow { emit(ResultSource.Error(ThrowableUC.extract(Exception("return null")))) }
//    }
}
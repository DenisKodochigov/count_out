package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.repository.plans.ActivityRepo
import com.count_out.domain.repository.plans.ExerciseRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseCore @Inject constructor(private val repo: ExerciseRepo): Core()  {
    fun get(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.get(exercise)
    }
    fun del(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.del(exercise)
    }
    fun copy(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.copy(exercise)
    }
    fun update(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.update(exercise)
    }
    fun changeSequenceExercise(item: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.changeSequenceExercise(item)
    }
    fun getForRound(id: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.getForRound(id)
    }
    fun getForRing(id: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.getForRing(id)
    }
    fun getFilter(list: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.getFilter(list)
    }
//    fun delRound(idRound: Long)
//    fun delRing(idRing: Long) roundId: Long = 0, ringId: Long = 0
}
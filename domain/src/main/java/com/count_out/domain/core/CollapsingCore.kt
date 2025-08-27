package com.count_out.domain.core

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Round
import com.count_out.domain.entity.workout.Set
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CollapsingCore: Core()  {
    fun get(request: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return flow { emit(ResultUC.Success(execute(request)))} }

    fun execute(item: TypeRepo): TypeRepo{
        return if (item is TypeRepo.CollapsingT)
            TypeRepo.CollapsingT(executeCollapsing(item.item) )
        else TypeRepo.NullT
    }
    fun executeCollapsing(item: Collapsing): Collapsing{
        return when(item.item){
            is Set -> {item.copy(sets =
                editList(item.sets, (item.item as Set).idSet))}
            is Ring-> {item.copy(rings =
                editList(item.rings, (item.item as Ring).idRing))}
            is Round-> {item.copy(rounds =
                editList(item.rounds, (item.item as Round).idRound))}
            is Exercise-> {item.copy(exercises =
                editList(item.exercises, (item.item as Exercise).idExercise))}
            is Activity -> {item.copy(activities =
                editList(item.activities, (item.item as Activity).idActivity))}
            else -> {item}
        }
    }
    fun editList( listCollapsing: List<Long>, id: Long): List<Long>{
        val list = listCollapsing.toMutableList()
        list.find { it == id }?.let { list.remove(it) } ?: list.add(id)
        return list
    }
}
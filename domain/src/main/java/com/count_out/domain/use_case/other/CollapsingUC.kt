package com.count_out.domain.use_case.other

import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Round
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CollapsingUC @Inject constructor(configuration: Configuration
): UseCase<CollapsingUC.Request, CollapsingUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        flow { emit( Response(executeCollapsing(input.request)) ) }
    data class Request(val request: Collapsing) : UseCase.Request
    data class Response(val result: Collapsing) : UseCase.Response

    fun executeCollapsing(item: Collapsing): Collapsing{
        when(item.item){
            is Set -> {item.copy(sets = editList(item.sets, (item.item as Set).idSet))}
            is Ring-> {item.copy(rings = editList(item.rings, (item.item as Ring).idRing))}
            is Round-> {item.copy(rounds = editList(item.rounds, (item.item as Round).idRound))}
            is Exercise-> {item.copy(exercises = editList(item.exercises, (item.item as Exercise).idExercise))}
        }
        return item
    }
    fun editList( listCollapsing: List<Long>, id: Long): List<Long>{
        val list = listCollapsing.toMutableList()
        list.find { it == id }?.let { list.remove(it) } ?: list.add(id)
        return list
    }
}
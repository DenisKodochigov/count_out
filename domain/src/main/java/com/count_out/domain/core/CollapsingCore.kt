package com.count_out.domain.core

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Set
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CollapsingCore: Core()  {
    fun get(request: Domain): Flow<ResultDomain<Domain>>{
        return flow { emit(ResultDomain.Success(execute(request)))} }

    fun execute(item: Domain): Domain {
        return if (item is Collapsing) executeCollapsing(item)
        else object: Domain{}
    }
    fun executeCollapsing(item: Collapsing): Collapsing{
        return when(item.item){
            is Set -> {item.copy(sets =
                editList(item.sets, (item.item as Set).idSet))}
            is Ring -> {item.copy(rounds =
                editList(item.rounds, (item.item as Ring).idRing))}
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
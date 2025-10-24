package com.count_out.domain.core

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.types_domai.BooleanDm
import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Selecting
import com.count_out.domain.entity.workout.Set
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SelectingCore @Inject constructor(): Core()  {

    fun get(request: Domain): Flow<ResultDomain<Domain>>{
        return flow { emit(ResultDomain.Success(execute(request)))} }

    fun execute(item: Domain): Domain {
        return if (item is Selecting) executeSelecting(item)
        else object: Domain{}
    }
    fun executeSelecting(item: Selecting): Selecting{
        return when(item.item){
            is Part -> {item.copy(parts =
                editList(item.parts, (item.item as Part).idPart,item.listOwner))}
            is Ring -> {item.copy(rings =
                editList(item.rings, (item.item as Ring).idRing,item.listOwner))}
            is Exercise-> {item.copy(exercises =
                editList(item.exercises, (item.item as Exercise).idExercise,item.listOwner))}
            is Set -> {item.copy(sets =
                editList(item.sets, (item.item as Set).idSet,item.listOwner))}
            is Activity -> {item.copy(activities =
                editList(item.activities, (item.item as Activity).idActivity,item.listOwner))}
            else -> {item}
        }
    }
    fun editList( list: List<Long>, id: Long, items: List<Domain>): List<Long>{
        val list = list.toMutableList()
        items.forEach { if (it is LongDm){ list.remove(it.item) } }
        list.add(id)
        return list
    }
}
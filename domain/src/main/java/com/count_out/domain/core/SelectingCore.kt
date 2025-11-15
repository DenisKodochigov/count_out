package com.count_out.domain.core

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Selecting
import com.count_out.domain.entity.workout.Set
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SelectingCore @Inject constructor(): Core()  {

    fun get(request: Domain): Flow<ResultDomain<Domain>>{
        return flow { emit(ResultDomain.Success(execute(request)))} }

    fun execute(sel: Domain): Domain {
        return if (sel !is Selecting) sel else {
            val item = sel.item ?: return sel
            when (item) {
                is Plan     -> initList(item, sel)
                is Part     -> sel.copy(parts = editList(sel.parts,item.idPart,sel.listOwner))
                is Ring     -> sel.copy(rings = editList(sel.rings,item.idRing,sel.listOwner))
                is Exercise -> sel.copy(exercises = editList(sel.exercises,item.idExercise,sel.listOwner))
                is Set      -> sel.copy(sets = editList(sel.sets,item.idSet,sel.listOwner))
                is Activity -> sel.copy(activities= editList(sel.activities,item.idActivity,sel.listOwner))
                else        -> sel
            }
        }
//            executeSelecting(item)
//        return if (item is Selecting) executeSelecting(item)
//        else object: Domain{}
    }

    fun editList( list: List<Long>, id: Long?, items: List<Domain>): List<Long>{
        if (id == null) return list
        val removeIds = items.filterIsInstance<LongDm>().map { it.item }
        return (list - removeIds + id)
    }

    fun initList(plan: Plan?, select: Selecting): Selecting {
        val exercises = select.exercises.ifEmpty {
            plan?.parts?.flatMap { it.rings }?.mapNotNull { ring ->
                ring.exercises.firstOrNull()?.idExercise }.orEmpty() }
        val sets = select.sets.ifEmpty {
            plan?.parts?.flatMap { it.rings }?.flatMap { ring ->
                ring.exercises }?.mapNotNull { ex -> ex.sets.firstOrNull()?.idSet }.orEmpty()}

        return select.copy(exercises = exercises, sets = sets)
    }
}
//    fun executeSelecting(item: Selecting): Selecting{
//        return when(item.item){
//            is Plan -> { initList((item.item as? Plan), item)}
//            is Part -> { item.copy(parts =
//                editList(item.parts, (item.item as? Part)?.idPart,item.listOwner))}
//            is Ring -> {item.copy(rings =
//                editList(item.rings, (item.item as? Ring)?.idRing,item.listOwner))}
//            is Exercise-> {item.copy(exercises =
//                editList(item.exercises, (item.item as? Exercise)?.idExercise,item.listOwner))}
//            is Set -> {item.copy(sets =
//                editList(item.sets, (item.item as? Set)?.idSet,item.listOwner))}
//            is Activity -> {item.copy(activities =
//                editList(item.activities, (item.item as? Activity)?.idActivity,item.listOwner))}
//            else -> {item}
//        }
//    }
//    fun initList(planN: Plan?, select: Selecting): Selecting {
//        val exerciseList = mutableListOf<Long>()
//        val setList = mutableListOf<Long>()
//        planN?.let{ plan->
//            plan.parts.forEach { part->
//                part.rings.forEach { ring->
//                    if (ring.exercises.isNotEmpty()) exerciseList.add( ring.exercises[0].idExercise)
//                    ring.exercises.forEach { exercise->
//                        if (exercise.sets.isNotEmpty()) setList.add(exercise.sets[0].idSet)
//                    }
//                }
//            }
//        }
//        return select.copy(exercises = exerciseList, sets = setList)
//    }
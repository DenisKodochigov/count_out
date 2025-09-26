package com.count_out.domain.core

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.ShowBottomSheet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ShowBSCore: Core()  {

    fun get(request: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return flow { emit(ResultUC.Success(execute(request)))} }

    fun execute(item: TypeRepo): TypeRepo{
        return if (item is TypeRepo.ShowBottomSheetT)
            TypeRepo.ShowBottomSheetT(calculate(item.item) )
        else TypeRepo.NullT
    }

    fun calculate(item: ShowBottomSheet): ShowBottomSheet{
        return when(item.element){
            is Set -> {item.copy(set = !item.set)}
            is Activity-> { item.copy(activity = !item.activity)}
            is Exercise-> { item.copy(exercise = !item.exercise)}
            is Ring -> { item.copy(ring = !item.ring) }
            is Plan-> {item.copy(plan = !item.plan)}
            is DeviceBle-> {item.copy(selectBleDevice = !item.selectBleDevice)}
            else -> {item}
        }
    }
//    fun calculateRound( item: ShowBottomSheet): ShowBottomSheet{
//        return when ((item.element as Ring).roundType){
//            RoundType.WorkUp -> { item.copy(workUp = !item.workUp) }
//            RoundType.WorkOut -> { item.copy(workOut = !item.workOut) }
//            RoundType.WorkDown -> { item.copy(workDown = !item.workDown) }
//        }
//    }
}
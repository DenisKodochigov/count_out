package com.count_out.domain.use_case.other

import com.count_out.domain.entity.enums.RoundType
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Round
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShowBottomSheetUC @Inject constructor(configuration: Configuration
): UseCase<ShowBottomSheetUC.Request, ShowBottomSheetUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        flow { emit( Response(calculate(input.request)) ) }
    data class Request(val request: ShowBottomSheet) : UseCase.Request
    data class Response(val result: ShowBottomSheet) : UseCase.Response


    fun calculate(item: ShowBottomSheet): ShowBottomSheet{
        when(item.element){
            is Set -> {item.copy(set = !item.set)}
            is Ring-> {item.copy(ring = !item.ring)}
            is Exercise-> {item.copy(exercise = !item.exercise)}
            is Round-> {calculateRound(item) }
        }
        return item
    }
    fun calculateRound( item: ShowBottomSheet): ShowBottomSheet{
        when ((item.element as Round).roundType){
            RoundType.WorkUp -> { item.copy(workUp = !item.workUp) }
            RoundType.WorkOut -> { item.copy(workOut = !item.workOut) }
            RoundType.WorkDown -> { item.copy(workDown = !item.workDown) }
        }
        return item
    }
}
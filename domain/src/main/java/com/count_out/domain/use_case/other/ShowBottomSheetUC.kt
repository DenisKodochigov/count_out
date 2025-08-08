package com.count_out.domain.use_case.other

import com.count_out.domain.entity.enums.RoundType
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Round
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShowBottomSheetUC @Inject constructor(configuration: Configuration
): UseCase<ShowBottomSheetUC.Request, ShowBottomSheetUC.Response>(configuration)  {

    override fun methodRepo(request: Request): Flow<ResultUC<TypeRepo>> {
        return flow { emit(ResultUC.Success(
                TypeRepo.ShowBottomSheetT(item = calculate(request.show))))}
    }

    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)

    data class Request(val show: ShowBottomSheet) : UseCase.Request
    data class Response(val show: TypeRepo) : UseCase.Response

    fun calculate(item: ShowBottomSheet): ShowBottomSheet{
        return when(item.element){
            is Set -> {item.copy(set = !item.set)}
            is Ring-> {item.copy(ring = !item.ring)}
            is Exercise-> {item.copy(exercise = !item.exercise)}
            is Training-> {item.copy(training = !item.training)}
            is Activity-> {item.copy(activity = !item.activity)}
            is Round-> {calculateRound(item) }
            else -> {item}
        }
    }
    fun calculateRound( item: ShowBottomSheet): ShowBottomSheet{
        return when ((item.element as Round).roundType){
            RoundType.WorkUp -> { item.copy(workUp = !item.workUp) }
            RoundType.WorkOut -> { item.copy(workOut = !item.workOut) }
            RoundType.WorkDown -> { item.copy(workDown = !item.workDown) }
        }
    }
}
//
//    fun implementation(request: Request): Flow<ResultUC<Response>> =
//        flow { emit(
//            ResultUC.Success(
//                Response(
//                    TypeRepo.ShowBottomSheetMy(item = calculate(request.show)))))}
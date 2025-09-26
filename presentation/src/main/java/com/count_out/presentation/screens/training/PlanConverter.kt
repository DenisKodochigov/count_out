package com.count_out.presentation.screens.training

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.other.CollapsingUC
import com.count_out.domain.use_case.other.ShowBottomSheetUC
import com.count_out.domain.use_case.plans.GetPlanUC
import com.count_out.domain.use_case.plans.activity.GetActivitiesUC
import com.count_out.presentation.models.ActivityImplP
import com.count_out.presentation.screens.prime.PrimeConvertor
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlanConverter @Inject constructor(): PrimeConvertor<UseCase.Response, PlanState>() {
    override fun makeSuccess(resultData: UseCase.Response, state: MutableStateFlow<PlanState>): PlanState {
        return when(resultData){
            is GetPlanUC.Response-> converterLocal(resultData, state)
            is GetActivitiesUC.Response-> converterLocal(resultData, state)
            is ShowBottomSheetUC.Response-> converterLocal(resultData, state)
            is CollapsingUC.Response-> converterLocal(resultData, state)
            else -> converterOther(state)
        }
    }

    private fun converterLocal(data: GetPlanUC.Response, state: MutableStateFlow<PlanState>): PlanState {
        if (data.plan is TypeRepo.PlanT) {
            state.value = state.value.copy(plan = (data.plan as TypeRepo.PlanT).item) }
        return state.value
    }
    private fun converterLocal(data: GetActivitiesUC.Response, state: MutableStateFlow<PlanState>): PlanState {
        if (data.activity is TypeRepo.ActivitiesT) {
            state.value = state.value.copy(
                activities = (data.activity as TypeRepo.ActivitiesT).item.map { it as ActivityImplP}) }
        return state.value
    }

    private fun converterLocal(data: ShowBottomSheetUC.Response, state: MutableStateFlow<PlanState>): PlanState {
        if (data.show is TypeRepo.ShowBottomSheetT)
            state.value = state.value.copy(showBS = (data.show as TypeRepo.ShowBottomSheetT).item)
        return state.value
    }
    private fun converterLocal(data: CollapsingUC.Response, state: MutableStateFlow<PlanState>): PlanState {
        if (data.collaps is TypeRepo.CollapsingT)
            state.value = state.value.copy( collapsing = (data.collaps as TypeRepo.CollapsingT).item)
        return state.value
    }
    private fun converterOther( state: MutableStateFlow<PlanState>): PlanState {
        return state.value
    }
}

//            is UpdateTrainingUC.Response-> state.value
//            is CopyExerciseUC.Response-> converterLocal(resultData, state)
//            is DeleteExerciseUC.Response-> converterLocal(resultData, state)
//            is ChangeSequenceExerciseUC.Response-> converterLocal(resultData, state)
//            is CopySetUC.Response-> converterLocal(resultData, state)
//            is DeleteSetUC.Response-> converterLocal(resultData, state)
//            is UpdateSetUC.Response-> converterLocal(resultData, state)
//
//    private fun converterLocal(data: CopyExerciseUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
//        return state.value
//    }
//    private fun converterLocal(data: DeleteExerciseUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
//        return state.value
//    }
//    private fun converterLocal(data: ChangeSequenceExerciseUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
//        return state.value
//    }
//    private fun converterLocal(data: CopySetUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
//        return state.value
//    }
//    private fun converterLocal(data: DeleteSetUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
//        return state.value
//    }
//    private fun converterLocal(data: UpdateSetUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
//        return state.value
//    }
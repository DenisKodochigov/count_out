package com.count_out.presentation.screens.plans.model

import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.types_domai.PlansDm
import com.count_out.domain.entity.workout.Activities
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.LauncherBS
import com.count_out.domain.entity.workout.Selecting
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.other.CollapsingUC
import com.count_out.domain.use_case.other.LauncherBSUC
import com.count_out.domain.use_case.plans.GetPlansUC
import com.count_out.domain.use_case.plans.RunPlanUC
import com.count_out.domain.use_case.plans.SelectingUC
import com.count_out.domain.use_case.plans.activity.GetActivitiesUC
import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.screens.prime.PrimeConvertor
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlansConvertor @Inject constructor(): PrimeConvertor<UseCase.Response, PlansState>() {

    override fun makeSuccess(resultData: UseCase.Response, state: MutableStateFlow<PlansState>): PlansState {
        return when(resultData){
            is GetPlansUC.Response-> converterGetTrainings(resultData, state)
            is RunPlanUC.Response-> converterSelectTraining(resultData, state)
            is GetActivitiesUC.Response-> converterLocal(resultData, state)
            is CollapsingUC.Response-> converterLocal(resultData, state)
            is SelectingUC.Response-> converterLocal(resultData, state)
            is LauncherBSUC.Response-> converterLocal(resultData, state)
            else -> converterOther(state)
        }
    }
    private fun converterGetTrainings(data: GetPlansUC.Response, state: MutableStateFlow<PlansState>): PlansState {
        if (data.plans is PlansDm) {
            state.value = state.value.copy(plans = (data.plans as PlansDm).item) }
        return state.value
    }

    private fun converterSelectTraining(data: RunPlanUC.Response, state: MutableStateFlow<PlansState>): PlansState {
        if (data.selectedTraining is LongDm)
            state.value = state.value.copy( selectedId = (data.selectedTraining as LongDm).item)
        return state.value
    }
    private fun converterLocal(data: LauncherBSUC.Response, state: MutableStateFlow<PlansState>): PlansState {
        if (data.launcher is LauncherBS<*>) {
            state.value = state.value.copy(launcherBS = data.launcher as LauncherBSp) }
        return state.value
    }
//    private fun converterLocal(data: GetPlanUC.Response, state: MutableStateFlow<PlansState>): PlansState {
//        if (data.plan is Plan) { state.value = state.value.copy(plan = data.plan as Plan) }
//        return state.value
//    }
    private fun converterLocal(data: GetActivitiesUC.Response, state: MutableStateFlow<PlansState>): PlansState {
        if (data.activity is Activities) {
            state.value = state.value.copy(list = (data.activity as Activities).activities) }
        return state.value
    }

    //    private fun converterLocal(data: ShowBottomSheetUC.Response, state: MutableStateFlow<PlansState>): PlansState {
//        if (data.show is ShowBottomSheet)
//            state.value = state.value.copy(showBS = data.show as ShowBottomSheet)
//        return state.value
//    }
    private fun converterLocal(data: CollapsingUC.Response, state: MutableStateFlow<PlansState>): PlansState {
        if (data.collaps is Collapsing)
            state.value = state.value.copy( collapsing = data.collaps as Collapsing)
        return state.value
    }
    private fun converterLocal(data: SelectingUC.Response, state: MutableStateFlow<PlansState>): PlansState {
        if (data.item is Selecting) state.value = state.value.copy( selecting = data.item as Selecting)
        return state.value
    }
    private fun converterOther( state: MutableStateFlow<PlansState>): PlansState {
        return state.value
    }
}
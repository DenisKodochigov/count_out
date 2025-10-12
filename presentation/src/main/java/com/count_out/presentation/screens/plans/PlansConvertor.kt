package com.count_out.presentation.screens.plans

import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.types_domai.PlansDm
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.plans.CopyPlanUC
import com.count_out.domain.use_case.plans.DeletePlanUC
import com.count_out.domain.use_case.plans.GetPlansUC
import com.count_out.domain.use_case.plans.SelectPlanUC
import com.count_out.domain.use_case.plans.UpdatesTrainingUC
import com.count_out.presentation.screens.prime.PrimeConvertor
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlansConvertor @Inject constructor(): PrimeConvertor<UseCase.Response, PlansState>() {

    override fun makeSuccess(resultData: UseCase.Response, state: MutableStateFlow<PlansState>): PlansState {
        return when(resultData){
            is GetPlansUC.Response-> converterGetTrainings(resultData, state)
            is CopyPlanUC.Response-> converterCopyTraining(resultData, state)
            is DeletePlanUC.Response-> converterDeleteTraining(resultData, state)
            is UpdatesTrainingUC.Response-> converterUpdatesTraining(resultData, state)
            is SelectPlanUC.Response-> converterSelectTraining(resultData, state)
            else -> converterOther(state)
        }
    }
    private fun converterGetTrainings(data: GetPlansUC.Response, state: MutableStateFlow<PlansState>): PlansState {
        if (data.plans is PlansDm) {
            state.value = state.value.copy(plans = (data.plans as PlansDm).item) }
        return state.value
    }
    private fun converterCopyTraining(data: CopyPlanUC.Response, state: MutableStateFlow<PlansState>): PlansState {
//        if (data.plans is TypeRepo.PlansT)
//            state.value = state.value.copy( plans = (data.plans as TypeRepo.PlansT).item)
        return state.value
    }
    private fun converterDeleteTraining(data: DeletePlanUC.Response, state: MutableStateFlow<PlansState>): PlansState {
//        if (data.plans is TypeRepo.PlansT)
//            state.value = state.value.copy( plans = (data.plans as TypeRepo.PlansT).item)
        return state.value
    }
    private fun converterUpdatesTraining(data: UpdatesTrainingUC.Response, state: MutableStateFlow<PlansState>): PlansState {
//        if (data.plan is TypeRepo.PlansT)
//            state.value = state.value.copy( plans = (data.plan as TypeRepo.PlansT).item)
        return state.value
    }
    private fun converterSelectTraining(data: SelectPlanUC.Response, state: MutableStateFlow<PlansState>): PlansState {
        if (data.selectedTraining is LongDm)
            state.value = state.value.copy( selectedId = (data.selectedTraining as LongDm).item)
        return state.value
}
    private fun converterOther(state: MutableStateFlow<PlansState>): PlansState {
        return state.value.copy(plans = emptyList())
    }
}
package com.count_out.presentation.screens.plans

import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.types_domai.PlansDm
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.plans.GetPlansUC
import com.count_out.domain.use_case.plans.SelectPlanUC
import com.count_out.presentation.screens.prime.PrimeConvertor
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlansConvertor @Inject constructor(): PrimeConvertor<UseCase.Response, PlansState>() {

    override fun makeSuccess(resultData: UseCase.Response, state: MutableStateFlow<PlansState>): PlansState {
        return when(resultData){
            is GetPlansUC.Response-> converterGetTrainings(resultData, state)
            is SelectPlanUC.Response-> converterSelectTraining(resultData, state)
            else -> converterOther(state)
        }
    }
    private fun converterGetTrainings(data: GetPlansUC.Response, state: MutableStateFlow<PlansState>): PlansState {
        if (data.plans is PlansDm) {
            state.value = state.value.copy(plans = (data.plans as PlansDm).item) }
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

package com.count_out.presentation.screens.history

import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.plans.SelectPlanUC
import com.count_out.presentation.screens.plans.PlansState
import com.count_out.presentation.screens.prime.PrimeConvertor
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class HistoryConvertor @Inject constructor(): PrimeConvertor<UseCase.Response, HistoryState>() {

    override fun makeSuccess(resultData: UseCase.Response, state: MutableStateFlow<HistoryState>): HistoryState {
        return when(resultData){
            else -> converterOther(state)
        }
    }

    private fun converterSelectTraining(data: SelectPlanUC.Response, state: MutableStateFlow<PlansState>): PlansState {
        return state.value
}
    private fun converterOther(state: MutableStateFlow<HistoryState>): HistoryState {
        return state.value
    }
}
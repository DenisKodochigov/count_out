package com.count_out.presentation.screens.plans

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.plans.CopyTrainingUC
import com.count_out.domain.use_case.plans.DeleteTrainingUC
import com.count_out.domain.use_case.plans.GetTrainingsUC
import com.count_out.domain.use_case.plans.SelectTrainingUC
import com.count_out.domain.use_case.plans.UpdatesTrainingUC
import com.count_out.presentation.screens.prime.PrimeConvertor
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlansConvertor @Inject constructor(): PrimeConvertor<UseCase.Response, PlansState>() {

    override fun makeSuccess(resultData: UseCase.Response, state: MutableStateFlow<PlansState>): PlansState {
        return when(resultData){
            is GetTrainingsUC.Response-> converterGetTrainings(resultData, state)
            is CopyTrainingUC.Response-> converterCopyTraining(resultData, state)
            is DeleteTrainingUC.Response-> converterDeleteTraining(resultData, state)
            is UpdatesTrainingUC.Response-> converterUpdatesTraining(resultData, state)
            is SelectTrainingUC.Response-> converterSelectTraining(resultData, state)
            else -> converterOther(state)
        }
    }
    private fun converterGetTrainings(data: GetTrainingsUC.Response, state: MutableStateFlow<PlansState>): PlansState {
        if (data.plans is TypeRepo.PlansT) {
            state.value = state.value.copy(plans = (data.plans as TypeRepo.PlansT).item) }
        return state.value
    }
    private fun converterCopyTraining(data: CopyTrainingUC.Response, state: MutableStateFlow<PlansState>): PlansState {
        if (data.plans is TypeRepo.PlansT)
            state.value = state.value.copy( plans = (data.plans as TypeRepo.PlansT).item)
        return state.value
    }
    private fun converterDeleteTraining(data: DeleteTrainingUC.Response, state: MutableStateFlow<PlansState>): PlansState {
        if (data.plans is TypeRepo.PlansT)
            state.value = state.value.copy( plans = (data.plans as TypeRepo.PlansT).item)
        return state.value
    }
    private fun converterUpdatesTraining(data: UpdatesTrainingUC.Response, state: MutableStateFlow<PlansState>): PlansState {
        if (data.plan is TypeRepo.PlansT)
            state.value = state.value.copy( plans = (data.plan as TypeRepo.PlansT).item)
        return state.value
    }
    private fun converterSelectTraining(data: SelectTrainingUC.Response, state: MutableStateFlow<PlansState>): PlansState {
        if (data.selectedTraining is TypeRepo.LongT)
            state.value = state.value.copy( selectedId = (data.selectedTraining as TypeRepo.LongT).item)
        return state.value
}
    private fun converterOther(state: MutableStateFlow<PlansState>): PlansState {
        return state.value.copy(plans = emptyList())
    }
}
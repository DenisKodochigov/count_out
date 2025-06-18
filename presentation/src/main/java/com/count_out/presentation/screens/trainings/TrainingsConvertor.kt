package com.count_out.presentation.screens.trainings

import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.trainings.CopyTrainingUC
import com.count_out.domain.use_case.trainings.DeleteTrainingUC
import com.count_out.domain.use_case.trainings.GetTrainingsUC
import com.count_out.domain.use_case.trainings.SelectTrainingUC
import com.count_out.domain.use_case.trainings.UpdateTrainingUC
import com.count_out.presentation.screens.prime.PrimeConvertor
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class TrainingsConvertor @Inject constructor(): PrimeConvertor<UseCase.Response, TrainingsState>() {

    override fun convertSuccess(data: UseCase.Response, state: MutableStateFlow<TrainingsState>): TrainingsState {
        return when(data){
            is GetTrainingsUC.Response-> converterGetTrainings(data, state)
            is CopyTrainingUC.Response-> converterCopyTraining(data, state)
            is DeleteTrainingUC.Response-> converterDeleteTraining(data, state)
            is UpdateTrainingUC.Response-> converterUpdateTraining(data, state)
            is SelectTrainingUC.Response-> converterSelectTraining(data, state)
            else -> converterOther(state)
        }
    }
    private fun converterGetTrainings(data: GetTrainingsUC.Response, state: MutableStateFlow<TrainingsState>): TrainingsState {
        return state.value.copy(trainings = data.trainings)
    }
    private fun converterCopyTraining(data: CopyTrainingUC.Response, state: MutableStateFlow<TrainingsState>): TrainingsState {
        return state.value.copy(trainings = data.trainings)
    }
    private fun converterDeleteTraining(data: DeleteTrainingUC.Response, state: MutableStateFlow<TrainingsState>): TrainingsState {
        return state.value.copy(trainings = data.trainings)
    }
    private fun converterUpdateTraining(data: UpdateTrainingUC.Response, state: MutableStateFlow<TrainingsState>): TrainingsState {
        return state.value
    }
    private fun converterSelectTraining(data: SelectTrainingUC.Response, state: MutableStateFlow<TrainingsState>): TrainingsState {
        return state.value.copy(selectedId = data.selectedTraining)
}
    private fun converterOther(state: MutableStateFlow<TrainingsState>): TrainingsState {
        return state.value.copy(trainings = emptyList())
    }
}
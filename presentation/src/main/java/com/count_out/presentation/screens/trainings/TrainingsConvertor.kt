package com.count_out.presentation.screens.trainings

import androidx.compose.runtime.mutableStateOf
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.trainings.AddTrainingUC
import com.count_out.domain.use_case.trainings.CopyTrainingUC
import com.count_out.domain.use_case.trainings.DeleteTrainingUC
import com.count_out.domain.use_case.trainings.GetTrainingsUC
import com.count_out.domain.use_case.trainings.SelectTrainingUC
import com.count_out.presentation.screens.prime.PrimeConvertor
import javax.inject.Inject

class TrainingsConvertor @Inject constructor(): PrimeConvertor<UseCase.Response, TrainingsState>() {
    override fun convertSuccess(data: UseCase.Response): TrainingsState {
        return when(data){
            is GetTrainingsUC.Response-> converterGetTrainings(data)
            is AddTrainingUC.Response-> converterAddTraining(data)
            is CopyTrainingUC.Response-> converterCopyTraining(data)
            is DeleteTrainingUC.Response-> converterDeleteTraining(data)
//            is UpdateTrainingUC.Response-> converterUpdateTraining(data)
            is SelectTrainingUC.Response-> converterSelectTraining(data)
            else -> converterOther()
        }
    }
    private fun converterGetTrainings(data: GetTrainingsUC.Response): TrainingsState {
        return TrainingsState(
            trainings = data .trainings, selectedId = mutableStateOf(0),)
    }
    private fun converterAddTraining(data: AddTrainingUC.Response): TrainingsState {
        return TrainingsState(
            trainings = data.trainings, selectedId = mutableStateOf(0),)
    }
    private fun converterCopyTraining(data: CopyTrainingUC.Response): TrainingsState {
        return TrainingsState(
            trainings = data .trainings, selectedId = mutableStateOf(0),)
    }
    private fun converterDeleteTraining(data: DeleteTrainingUC.Response): TrainingsState {
        return TrainingsState(
            trainings = data .trainings, selectedId = mutableStateOf(0),)
    }
//    private fun converterUpdateTraining(data: UpdateTrainingUC.Response): TrainingsState {
//        return TrainingsState(
//            trainings = data.trainings, selectedId = mutableStateOf(0),)
//    }
    private fun converterSelectTraining(data: SelectTrainingUC.Response): TrainingsState {
        return TrainingsState(
            trainings = data .trainings, selectedId = mutableStateOf(0),)
    }
    private fun converterOther(): TrainingsState {
        return TrainingsState(trainings = emptyList(), selectedId = mutableStateOf(0),) }
}

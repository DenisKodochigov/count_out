package com.count_out.presentation.screens.training

import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.exercise.ChangeSequenceExerciseUC
import com.count_out.domain.use_case.exercise.CopyExerciseUC
import com.count_out.domain.use_case.exercise.DeleteExerciseUC
import com.count_out.domain.use_case.other.CollapsingUC
import com.count_out.domain.use_case.other.ShowBottomSheetUC
import com.count_out.domain.use_case.set.CopySetUC
import com.count_out.domain.use_case.set.DeleteSetUC
import com.count_out.domain.use_case.set.UpdateSetUC
import com.count_out.domain.use_case.trainings.GetTrainingUC
import com.count_out.domain.use_case.trainings.UpdateTrainingUC
import com.count_out.presentation.models.TrainingImplP
import com.count_out.presentation.screens.prime.PrimeConvertor
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class TrainingConverter @Inject constructor(): PrimeConvertor<UseCase.Response, TrainingState>() {

    override fun convertSuccess(data: UseCase.Response, state: MutableStateFlow<TrainingState>): TrainingState {
        return when(data){
            is GetTrainingUC.Response-> converterLocal(data, state)
            is UpdateTrainingUC.Response-> converterLocal(data, state)
            is CopyExerciseUC.Response-> converterLocal(data, state)
            is DeleteExerciseUC.Response-> converterLocal(data, state)
            is ChangeSequenceExerciseUC.Response-> converterLocal(data, state)
            is CopySetUC.Response-> converterLocal(data, state)
            is DeleteSetUC.Response-> converterLocal(data, state)
            is UpdateSetUC.Response-> converterLocal(data, state)
            is ShowBottomSheetUC.Response-> converterLocal(data, state)
            is CollapsingUC.Response-> converterLocal(data, state)
            else -> converterOther(state)
        }
    }
    private fun converterLocal(data: GetTrainingUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
        state.value = state.value.copy(training = data.training,)
        return state.value
    }
    private fun converterLocal(data: UpdateTrainingUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
        state.value = state.value.copy(training = data.trainings,)
        return state.value
    }
    private fun converterLocal(data: CopyExerciseUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
        return state.value
    }
    private fun converterLocal(data: DeleteExerciseUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
        return state.value
    }
    private fun converterLocal(data: ChangeSequenceExerciseUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
        return state.value
    }
    private fun converterLocal(data: CopySetUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
        return state.value
    }
    private fun converterLocal(data: DeleteSetUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
        return state.value
    }
    private fun converterLocal(data: UpdateSetUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
        return state.value
    }
    private fun converterLocal(data: ShowBottomSheetUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
        state.value = state.value.copy(showBS = data.result,)
        return state.value
    }
    private fun converterLocal(data: CollapsingUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
        state.value = state.value.copy(collapsing = data.result,)
        return state.value
    }

//    private fun converterLocal(data: ShowBSSpeechTrainingUC.Response): TrainingState {
//        return state.copy(showSpeechTraining = data.result,)
//    }
//    private fun converterLocal(data: ShowBSSpeechWorkUpUC.Response): TrainingState {
//        return state.copy(showSpeechWorkUp = data.result,)
//    }
//    private fun converterLocal(data: ShowBSSpeechWorkOutUC.Response): TrainingState {
//        return state.copy(showSpeechWorkOut = data.result,)
//    }
//    private fun converterLocal(data: ShowBSSpeechWorkDownUC.Response): TrainingState {
//        return state.copy(showSpeechWorkDown = data.result,)
//    }
//    private fun converterLocal(data: ShowBSSpeechExerciseUC.Response): TrainingState {
//        return state.copy(showSpeechExercise = data.result,)
//    }
//    private fun converterLocal(data: ShowBSSpeechSetUC.Response): TrainingState {
//        return state.copy(showSpeechSet = data.result,)
//    }
//    private fun converterLocal(data: ShowBSSelectActivityUC.Response): TrainingState {
//        return state.copy(showSelectActivity = data.result,)
//    }
    private fun converterOther( state: MutableStateFlow<TrainingState>): TrainingState {
        return state.value.copy(training = TrainingImplP()) }
}

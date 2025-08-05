package com.count_out.presentation.screens.training

import android.R.attr.data
import android.util.Log
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.other.CollapsingUC
import com.count_out.domain.use_case.other.ShowBottomSheetUC
import com.count_out.domain.use_case.plans.GetTrainingUC
import com.count_out.domain.use_case.plans.UpdateTrainingUC
import com.count_out.domain.use_case.plans.exercise.ChangeSequenceExerciseUC
import com.count_out.domain.use_case.plans.exercise.CopyExerciseUC
import com.count_out.domain.use_case.plans.exercise.DeleteExerciseUC
import com.count_out.domain.use_case.plans.set.CopySetUC
import com.count_out.domain.use_case.plans.set.DeleteSetUC
import com.count_out.domain.use_case.plans.set.UpdateSetUC
import com.count_out.presentation.models.TrainingImplP
import com.count_out.presentation.screens.prime.PrimeConvertor
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class TrainingConverter @Inject constructor(): PrimeConvertor<UseCase.Response, TrainingState>() {
    override fun makeSuccess(resultData: UseCase.Response, state: MutableStateFlow<TrainingState>): TrainingState {
        return when(resultData){
            is GetTrainingUC.Response-> converterLocal(resultData, state)
            is UpdateTrainingUC.Response-> converterLocal(resultData, state)
            is CopyExerciseUC.Response-> converterLocal(resultData, state)
            is DeleteExerciseUC.Response-> converterLocal(resultData, state)
            is ChangeSequenceExerciseUC.Response-> converterLocal(resultData, state)
            is CopySetUC.Response-> converterLocal(resultData, state)
            is DeleteSetUC.Response-> converterLocal(resultData, state)
            is UpdateSetUC.Response-> converterLocal(resultData, state)
            is ShowBottomSheetUC.Response-> converterLocal(resultData, state)
            is CollapsingUC.Response-> converterLocal(resultData, state)
            else -> converterOther(state)
        }
    }
    private fun converterLocal(data: GetTrainingUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
        if (data.training is TypeRepo.PlanT) {
            state.value = state.value.copy(training = (data.training as TypeRepo.PlanT).item)
        }
        return state.value
    }
    private fun converterLocal(data: UpdateTrainingUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
        if (data.training is TypeRepo.PlanT)
            state.value = state.value.copy( training = (data.training as TypeRepo.PlanT).item)
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
        if (data.show is TypeRepo.ShowBottomSheetT)
            state.value = state.value.copy( showBS = (data.show as TypeRepo.ShowBottomSheetT).item)
        return state.value
    }
    private fun converterLocal(data: CollapsingUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
        if (data.collaps is TypeRepo.CollapsingT)
            state.value = state.value.copy( collapsing = (data.collaps as TypeRepo.CollapsingT).item)
        return state.value
    }
    private fun converterOther( state: MutableStateFlow<TrainingState>): TrainingState {
        state.value = state.value.copy(training = TrainingImplP())
        return state.value
    }
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
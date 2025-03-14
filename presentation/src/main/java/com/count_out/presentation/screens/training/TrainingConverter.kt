package com.count_out.presentation.screens.training

import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.exercise.ChangeSequenceExerciseUC
import com.count_out.domain.use_case.exercise.CopyExerciseUC
import com.count_out.domain.use_case.exercise.DeleteExerciseUC
import com.count_out.domain.use_case.other.ShowBSSelectActivityUC
import com.count_out.domain.use_case.other.ShowBSSpeechExerciseUC
import com.count_out.domain.use_case.other.ShowBSSpeechSetUC
import com.count_out.domain.use_case.other.ShowBSSpeechTrainingUC
import com.count_out.domain.use_case.other.ShowBSSpeechWorkDownUC
import com.count_out.domain.use_case.other.ShowBSSpeechWorkOutUC
import com.count_out.domain.use_case.other.ShowBSSpeechWorkUpUC
import com.count_out.domain.use_case.set.CopySetUC
import com.count_out.domain.use_case.set.DeleteSetUC
import com.count_out.domain.use_case.set.UpdateSetUC
import com.count_out.domain.use_case.trainings.GetTrainingUC
import com.count_out.domain.use_case.trainings.UpdateTrainingUC
import com.count_out.presentation.models.TrainingImpl
import com.count_out.presentation.screens.prime.PrimeConvertor
import javax.inject.Inject

class TrainingConverter @Inject constructor(): PrimeConvertor<UseCase.Response, TrainingState>() {

    val state = TrainingState()
    override fun convertSuccess(data: UseCase.Response): TrainingState {
        return when(data){
            is GetTrainingUC.Response-> converterLocal(data)
            is UpdateTrainingUC.Response-> converterLocal(data)
            is CopyExerciseUC.Response-> converterLocal(data)
            is DeleteExerciseUC.Response-> converterLocal(data)
            is ChangeSequenceExerciseUC.Response-> converterLocal(data)
            is CopySetUC.Response-> converterLocal(data)
            is DeleteSetUC.Response-> converterLocal(data)
            is UpdateSetUC.Response-> converterLocal(data)
            is ShowBSSpeechTrainingUC.Response-> converterLocal(data)
            is ShowBSSpeechWorkUpUC.Response-> converterLocal(data)
            is ShowBSSpeechWorkOutUC.Response-> converterLocal(data)
            is ShowBSSpeechWorkDownUC.Response-> converterLocal(data)
            is ShowBSSpeechExerciseUC.Response-> converterLocal(data)
            is ShowBSSpeechSetUC.Response-> converterLocal(data)
            is ShowBSSelectActivityUC.Response-> converterLocal(data)

            else -> converterOther()
        }
    }
    private fun converterLocal(data: GetTrainingUC.Response): TrainingState {
        return state.copy(training = data.training,)
    }
    private fun converterLocal(data: UpdateTrainingUC.Response): TrainingState {
        return state.copy(training = data.trainings,)
    }
    private fun converterLocal(data: CopyExerciseUC.Response): TrainingState {
        return state.copy(training = data.training,)
    }
    private fun converterLocal(data: DeleteExerciseUC.Response): TrainingState {
        return state.copy(training = data.training,)
    }
    private fun converterLocal(data: ChangeSequenceExerciseUC.Response): TrainingState {
        return state.copy(training = data.training,)
    }
    private fun converterLocal(data: CopySetUC.Response): TrainingState {
        return state.copy(training = data.training,)
    }
    private fun converterLocal(data: DeleteSetUC.Response): TrainingState {
        return state.copy(training = data.training,)
    }
    private fun converterLocal(data: UpdateSetUC.Response): TrainingState {
        return state.copy(training = data.training,)
    }
    private fun converterLocal(data: ShowBSSpeechTrainingUC.Response): TrainingState {
        return state.copy(showSpeechTraining = data.result,)
    }
    private fun converterLocal(data: ShowBSSpeechWorkUpUC.Response): TrainingState {
        return state.copy(showSpeechWorkUp = data.result,)
    }
    private fun converterLocal(data: ShowBSSpeechWorkOutUC.Response): TrainingState {
        return state.copy(showSpeechWorkOut = data.result,)
    }
    private fun converterLocal(data: ShowBSSpeechWorkDownUC.Response): TrainingState {
        return state.copy(showSpeechWorkDown = data.result,)
    }
    private fun converterLocal(data: ShowBSSpeechExerciseUC.Response): TrainingState {
        return state.copy(showSpeechExercise = data.result,)
    }
    private fun converterLocal(data: ShowBSSpeechSetUC.Response): TrainingState {
        return state.copy(showSpeechSet = data.result,)
    }
    private fun converterLocal(data: ShowBSSelectActivityUC.Response): TrainingState {
        return state.copy(showSelectActivity = data.result,)
    }
    private fun converterOther(): TrainingState {
        return state.copy(training = TrainingImpl()) }
}

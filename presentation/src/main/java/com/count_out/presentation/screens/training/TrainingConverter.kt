package com.count_out.presentation.screens.training

import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.exercise.ChangeSequenceExerciseUC
import com.count_out.domain.use_case.exercise.CopyExerciseUC
import com.count_out.domain.use_case.exercise.DeleteExerciseUC
import com.count_out.domain.use_case.set.CopySetUC
import com.count_out.domain.use_case.set.DeleteSetUC
import com.count_out.domain.use_case.set.UpdateSetUC
import com.count_out.domain.use_case.trainings.GetTrainingUC
import com.count_out.domain.use_case.trainings.UpdateTrainingUC
import com.count_out.presentation.models.TrainingImpl
import com.count_out.presentation.screens.prime.PrimeConvertor
import javax.inject.Inject

class TrainingConverter @Inject constructor(): PrimeConvertor<UseCase.Response, TrainingState>() {

    override fun convertSuccess(data: UseCase.Response): TrainingState {
        return when(data){
            is GetTrainingUC.Response-> converterGetTraining(data)
            is UpdateTrainingUC.Response-> converterUpdateTraining(data)
            is CopyExerciseUC.Response-> converterCopyExercise(data)
            is DeleteExerciseUC.Response-> converterDelExercise(data)
            is ChangeSequenceExerciseUC.Response-> converterChangeSequenceExercise(data)
            is CopySetUC.Response-> converterCopySet(data)
            is DeleteSetUC.Response-> converterDelSet(data)
            is UpdateSetUC.Response-> converterUpdateSet(data)
            else -> converterOther()
        }
    }
    private fun converterGetTraining(data: GetTrainingUC.Response): TrainingState {
        return TrainingState(training = data.training,)
    }
    private fun converterUpdateTraining(data: UpdateTrainingUC.Response): TrainingState {
        return TrainingState(training = data.trainings,)
    }
    private fun converterCopyExercise(data: CopyExerciseUC.Response): TrainingState {
        return TrainingState(training = data.training,)
    }
    private fun converterDelExercise(data: DeleteExerciseUC.Response): TrainingState {
        return TrainingState(training = data.training,)
    }
    private fun converterChangeSequenceExercise(data: ChangeSequenceExerciseUC.Response): TrainingState {
        return TrainingState(training = data.training,)
    }
    private fun converterCopySet(data: CopySetUC.Response): TrainingState {
        return TrainingState(training = data.training,)
    }
    private fun converterDelSet(data: DeleteSetUC.Response): TrainingState {
        return TrainingState(training = data.training,)
    }
    private fun converterUpdateSet(data: UpdateSetUC.Response): TrainingState {
        return TrainingState(training = data.training,)
    }
    private fun converterOther(): TrainingState {
        return TrainingState(training = TrainingImpl()) }
}

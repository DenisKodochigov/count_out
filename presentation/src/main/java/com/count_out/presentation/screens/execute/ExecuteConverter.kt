package com.count_out.presentation.screens.execute

import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.workout.DownIntervalUC
import com.count_out.domain.use_case.workout.PauseWorkoutUC
import com.count_out.domain.use_case.workout.SaveWorkoutUC
import com.count_out.domain.use_case.workout.StartWorkoutUC
import com.count_out.domain.use_case.workout.StopWorkoutUC
import com.count_out.domain.use_case.workout.UpIntervalUC
import com.count_out.domain.use_case.other.ShowBottomSheetUC
import com.count_out.domain.use_case.plans.GetStepPlanUC
import com.count_out.presentation.screens.prime.PrimeConvertor
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ExecuteConverter @Inject constructor(): PrimeConvertor<UseCase.Response, ExecuteState>() {

    override fun makeSuccess(
        resultData: UseCase.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        return when(resultData){
            is StartWorkoutUC.Response-> makeLocal(resultData, state)
            is StopWorkoutUC.Response-> makeLocal(resultData, state)
            is PauseWorkoutUC.Response-> makeLocal(resultData, state)
            is SaveWorkoutUC.Response-> makeLocal(resultData, state)
            is UpIntervalUC.Response-> makeLocal(resultData, state)
            is DownIntervalUC.Response-> makeLocal(resultData, state)
            is GetStepPlanUC.Response-> makeLocal(resultData, state)
            is ShowBottomSheetUC.Response-> makeLocal(resultData, state)
            else -> converterOther(state)
        }
    }
    private fun makeLocal(data: StartWorkoutUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        return state.value
    }
    private fun makeLocal(data: StopWorkoutUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        return state.value
    }
    private fun makeLocal(data: PauseWorkoutUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        return state.value
    }
    private fun makeLocal(data: SaveWorkoutUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        return state.value
    }
    private fun makeLocal(data: UpIntervalUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        return state.value
    }
    private fun makeLocal(data: DownIntervalUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        return state.value
    }
    private fun makeLocal(data: GetStepPlanUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        if (data.step is TypeRepo.StepPlanT)
            state.value = state.value.copy( stepTraining = (data.step as TypeRepo.StepPlanT).item)
        return state.value
    }
    private fun makeLocal(data: ShowBottomSheetUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        if (data.show is TypeRepo.ShowBottomSheetT)
            state.value = state.value.copy( showBS = (data.show as TypeRepo.ShowBottomSheetT).item)
        return state.value
    }
    private fun converterOther( state: MutableStateFlow<ExecuteState>): ExecuteState {
        return state.value}
}

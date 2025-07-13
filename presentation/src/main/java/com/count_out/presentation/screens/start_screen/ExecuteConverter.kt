package com.count_out.presentation.screens.start_screen

import android.util.Log
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.execute.DownIntervalUC
import com.count_out.domain.use_case.execute.PauseWorkoutUC
import com.count_out.domain.use_case.execute.SaveWorkoutUC
import com.count_out.domain.use_case.execute.StartWorkoutUC
import com.count_out.domain.use_case.execute.StopWorkoutUC
import com.count_out.domain.use_case.execute.UpIntervalUC
import com.count_out.domain.use_case.other.ShowBottomSheetUC
//import com.count_out.domain.use_case.plans.GetPlanUC
import com.count_out.domain.use_case.plans.GetStepPlanUC
import com.count_out.presentation.screens.prime.PrimeConvertor
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ExecuteConverter @Inject constructor(): PrimeConvertor<UseCase.Response, ExecuteState>() {

    override fun convertSuccess(resultData: UseCase.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        return when(resultData){
            is StartWorkoutUC.Response-> converterLocal(resultData, state)
            is StopWorkoutUC.Response-> converterLocal(resultData, state)
            is PauseWorkoutUC.Response-> converterLocal(resultData, state)
            is SaveWorkoutUC.Response-> converterLocal(resultData, state)
            is UpIntervalUC.Response-> converterLocal(resultData, state)
            is DownIntervalUC.Response-> converterLocal(resultData, state)
            is GetStepPlanUC.Response-> converterLocal(resultData, state)
            is ShowBottomSheetUC.Response-> converterLocal(resultData, state)
//            is GetPlanUC.Response-> converterLocal(resultData, state)ShowBottomSheetUC
            else -> converterOther(state)
        }
    }
    private fun converterLocal(data: StartWorkoutUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        return state.value
    }
    private fun converterLocal(data: StopWorkoutUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        return state.value
    }
    private fun converterLocal(data: PauseWorkoutUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        return state.value
    }
    private fun converterLocal(data: SaveWorkoutUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        return state.value
    }
    private fun converterLocal(data: UpIntervalUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        return state.value
    }
    private fun converterLocal(data: DownIntervalUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        return state.value
    }
    private fun converterLocal(data: GetStepPlanUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        return state.value.copy( stepTraining = data.step)
    }
    private fun converterLocal(data: ShowBottomSheetUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        return state.value.copy( showBS = data.show)
    }
//    private fun converterLocal(data: GetPlanUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
//        return state.value.copy(plan = data.training)
//    }
    private fun converterOther( state: MutableStateFlow<ExecuteState>): ExecuteState {
        return state.value}
}

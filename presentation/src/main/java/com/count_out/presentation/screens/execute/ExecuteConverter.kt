package com.count_out.presentation.screens.execute

import com.count_out.domain.entity.StepPlan
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.lg
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.workout.LauncherBS
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.bluetooth.GetConnectionStateUC
import com.count_out.domain.use_case.bluetooth.GetHeartRateUC
import com.count_out.domain.use_case.bluetooth.LastBleDeviceUC
import com.count_out.domain.use_case.other.LauncherBottomSheetUC
import com.count_out.domain.use_case.plans.GetStepPlanUC
import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.screens.prime.PrimeConvertor
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ExecuteConverter @Inject constructor(): PrimeConvertor<UseCase.Response, ExecuteState>() {

    override fun makeSuccess(
        resultData: UseCase.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        return when(resultData){
            is GetStepPlanUC.Response-> converterLocal(resultData, state)
            is GetHeartRateUC.Response-> converterLocal(resultData, state)
            is LastBleDeviceUC.Response-> converterLocal(resultData, state)
            is GetConnectionStateUC.Response-> converterLocal(resultData, state)
            is LauncherBottomSheetUC.Response-> converterLocal(resultData, state)
            else -> converterOther(state)
        }
    }
    private fun converterLocal(data: LauncherBottomSheetUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        if (data.launcher is LauncherBS<*>) {
            state.value = state.value.copy(launcherBS = data.launcher as LauncherBSp) }
        return state.value
    }
    private fun converterLocal(data: GetStepPlanUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        state.value = state.value.copy( stepPlan = data.step as StepPlan?)
        return state.value
    }
    private fun converterLocal(data: GetConnectionStateUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        if (data.result is ConnectState)
            state.value = state.value.copy( bleConnectState = data.result as ConnectState)
        return state.value
    }
    private fun converterLocal(data: GetHeartRateUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        if (data.result is LongDm)
            state.value = state.value.copy( heartRate = (data.result as LongDm).item.toInt())
        return state.value
    }
    private fun converterLocal(data: LastBleDeviceUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
        if (data.result is DeviceBle)
            state.value = state.value.copy( lastConnectHearthRateDevice = data.result as DeviceBle)
        return state.value
    }
    private fun converterOther( state: MutableStateFlow<ExecuteState>): ExecuteState {
        return state.value}
}
//            is StartWorkoutUC.Response-> makeLocal(resultData, state)
//            is StopWorkoutUC.Response-> makeLocal(resultData, state)
//            is PauseWorkoutUC.Response-> makeLocal(resultData, state)
//            is SaveWorkoutUC.Response-> makeLocal(resultData, state)
//            is UpIntervalUC.Response-> makeLocal(resultData, state)
//            is DownIntervalUC.Response-> makeLocal(resultData, state)
//    private fun makeLocal(data: StartWorkoutUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
//        return state.value
//    }
//    private fun makeLocal(data: StopWorkoutUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
//        return state.value
//    }
//    private fun makeLocal(data: PauseWorkoutUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
//        return state.value
//    }
//    private fun makeLocal(data: SaveWorkoutUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
//        return state.value
//    }
//    private fun makeLocal(data: UpIntervalUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
//        return state.value
//    }
//    private fun makeLocal(data: DownIntervalUC.Response, state: MutableStateFlow<ExecuteState>): ExecuteState {
//        return state.value
//    }
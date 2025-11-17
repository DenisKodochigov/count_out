package com.count_out.presentation.screens.execute

import androidx.lifecycle.SavedStateHandle
import com.count_out.domain.use_case.bluetooth.ConnectDeviceHrUC
import com.count_out.domain.use_case.bluetooth.GetConnectionStateUC
import com.count_out.domain.use_case.bluetooth.GetHeartRateUC
import com.count_out.domain.use_case.other.LauncherBottomSheetUC
import com.count_out.domain.use_case.plans.GetStepPlanUC
import com.count_out.domain.use_case.workout.DownIntervalUC
import com.count_out.domain.use_case.workout.PauseWorkoutUC
import com.count_out.domain.use_case.workout.SaveWorkoutUC
import com.count_out.domain.use_case.workout.StartWorkoutUC
import com.count_out.domain.use_case.workout.StopWorkoutUC
import com.count_out.domain.use_case.workout.UpIntervalUC
import com.count_out.presentation.models.DataForServImpl
import com.count_out.presentation.models.Internet
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeViewModel
import com.count_out.presentation.screens.prime.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExecuteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val startWorkoutUC: StartWorkoutUC,
    private val stopWorkoutUC: StopWorkoutUC,
    private val pauseWorkoutUC: PauseWorkoutUC,
    private val saveWorkoutUC: SaveWorkoutUC,
    private val upIntervalUC: UpIntervalUC,
    private val downIntervalUC: DownIntervalUC,
    private val getStepPlanUC: GetStepPlanUC,
    private val launcherBSUC: LauncherBottomSheetUC,
    private val connectDeviceHr: ConnectDeviceHrUC,
    private val subscribeHeartRate: GetHeartRateUC,
    private val getConnectionState: GetConnectionStateUC,
    private val internet: Internet,
): PrimeViewModel<ExecuteState, ExecuteConverter>() {

    override fun initScreenState(): ScreenState<ExecuteState> = ScreenState.Loading
    override fun initDataState(): ExecuteState = ExecuteState(event = { submitEvent(it)})
    override fun convertor(): ExecuteConverter = ExecuteConverter()

    override fun routeEvent(event: Event) {
        when (event) {
            is ExecuteEvent.Start -> { run(startWorkoutUC,StartWorkoutUC.Request)}
            is ExecuteEvent.Stop -> { run(stopWorkoutUC,StopWorkoutUC.Request)}
            is ExecuteEvent.Pause -> { run(pauseWorkoutUC,PauseWorkoutUC.Request)}
            is ExecuteEvent.Save -> { run(saveWorkoutUC,SaveWorkoutUC.Request)}
            is ExecuteEvent.UpInterval -> { run(upIntervalUC,UpIntervalUC.Request)}
            is ExecuteEvent.DownInterval -> { run(downIntervalUC,DownIntervalUC.Request)}
            is ExecuteEvent.Launcher -> { run(launcherBSUC, LauncherBottomSheetUC.Request(event.item)) }
        }
    }
    init {
        template{ getStepPlanUC.execute(GetStepPlanUC.Request)}
        template{ getConnectionState.execute(GetConnectionStateUC.Request) }
        template{ subscribeHeartRate.execute(GetHeartRateUC.Request)}
        template{ connectDeviceHr.execute(ConnectDeviceHrUC.Request) }
    }

    private val dataForServ = DataForServImpl()
}

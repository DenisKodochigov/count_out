package com.count_out.presentation.screens.settings

import androidx.lifecycle.SavedStateHandle
import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.use_case.bluetooth.ClearCacheBleUC
import com.count_out.domain.use_case.bluetooth.ConnectDeviceHrUC
import com.count_out.domain.use_case.bluetooth.GetConnectionStateUC
import com.count_out.domain.use_case.bluetooth.GetHeartRateUC
import com.count_out.domain.use_case.bluetooth.LastBleDeviceUC
import com.count_out.domain.use_case.bluetooth.SelectDeviceBleUC
import com.count_out.domain.use_case.bluetooth.StartScanBleUC
import com.count_out.domain.use_case.bluetooth.StopScanBleUC
import com.count_out.domain.use_case.other.CollapsingUC
import com.count_out.domain.use_case.other.LauncherBottomSheetUC
import com.count_out.domain.use_case.plans.activity.AddActivityUC
import com.count_out.domain.use_case.plans.activity.DeleteActivityUC
import com.count_out.domain.use_case.plans.activity.GetActivitiesUC
import com.count_out.domain.use_case.plans.activity.UpdateActivityUC
import com.count_out.domain.use_case.settings.GetSettingsUC
import com.count_out.domain.use_case.settings.UpdateSettingUC
import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.screens.plan.PlanEvent
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeViewModel
import com.count_out.presentation.screens.prime.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val addActivity: AddActivityUC,
    private val delActivity: DeleteActivityUC,
    private val getsActivity: GetActivitiesUC,
    private val updateActivity: UpdateActivityUC,
    private val clearCacheBle: ClearCacheBleUC,
    private val startScanBle: StartScanBleUC,
    private val stopScanBle: StopScanBleUC,
    private val selectDeviceBle: SelectDeviceBleUC,
    private val getSettings: GetSettingsUC,
    private val updateSetting: UpdateSettingUC,
//    private val showBottomSheetUC: ShowBottomSheetUC,
    private val collapsingSetUC: CollapsingUC,
    private val getConnectionState: GetConnectionStateUC,
    private val subscribeHeartRate: GetHeartRateUC,
    private val getLastBleDevice: LastBleDeviceUC,
    private val connectDeviceHr: ConnectDeviceHrUC,
    private val launcherBSUC: LauncherBottomSheetUC,
): PrimeViewModel<SettingsState, SettingsConvertor>() {

    override fun initScreenState(): ScreenState<SettingsState> = ScreenState.Loading
    override fun initDataState(): SettingsState = SettingsState(event = { submitEvent(it) })
    override fun convertor(): SettingsConvertor = SettingsConvertor()
    override fun routeEvent(event: Event) {
        when (event) {
//            is SettingsEvent.BackScreen -> { navigate.backStack() }
            is SettingsEvent.AddActivity -> { addActivity(event.activity) }
            is SettingsEvent.DeleteActivity -> { delActivity(event.activity) }
            is SettingsEvent.UpdateActivity -> { updateActivity(event.activity) }
            is SettingsEvent.UpdateSetting -> { updateSetting(event.setting) }
            is SettingsEvent.GetSettings -> { getSettings() }
            is SettingsEvent.StartScanBLE -> { startScanBle() }
            is SettingsEvent.StopScanBLE -> { stopScanBle() }
            is SettingsEvent.SelectDevice -> { selectDeviceBle(event.device) }
            is SettingsEvent.ClearCacheBLE -> { clearCacheBle() }
//            is SettingsEvent.ShowBS -> { showBottomSheet(event.item) }
            is SettingsEvent.SetCollapsing -> { collapsingSet(event.item) }
            is PlanEvent.Launcher -> { launcherBS(event.item) }
//            is SettingsEvent.Init -> { init() }
        }
    }
    init {
        getSettings()
        getsActivity()
        getConnectionState()
        subscribeHeartRate()
        connectDeviceHr()
        getLastBleDevice()
    }
    private fun launcherBS(manager: LauncherBSp){
        template{ launcherBSUC.execute( LauncherBottomSheetUC.Request(manager))}}
    private fun connectDeviceHr() {
        template{connectDeviceHr.execute(ConnectDeviceHrUC.Request) } }
    private fun getConnectionState() {
        template{getConnectionState.execute(GetConnectionStateUC.Request)} }
    private fun subscribeHeartRate() {
        template{subscribeHeartRate.execute(GetHeartRateUC.Request) } }
    private fun getLastBleDevice() {
        template{getLastBleDevice.execute(LastBleDeviceUC.Request) } }
    private fun addActivity(activity: Activity) {
        template{addActivity.execute(AddActivityUC.Request(activity)) } }
    private fun getsActivity() {
        template{getsActivity.execute(GetActivitiesUC.Request) } }
    private fun delActivity(activity: Activity) {
        template{delActivity.execute(DeleteActivityUC.Request(activity))}}
    private fun updateActivity(activity: Activity) {
        template{updateActivity.execute(UpdateActivityUC.Request(activity)) } }
    private fun getSettings() {
        template{getSettings.execute(GetSettingsUC.Request) } }
    private fun updateSetting(setting: Settings) {
        template{updateSetting.execute(UpdateSettingUC.Request(setting))} }
    private fun startScanBle() {
        template{startScanBle.execute(StartScanBleUC.Request)} }
    private fun stopScanBle() {
        template{stopScanBle.execute(StopScanBleUC.Request)} }
    private fun clearCacheBle() {
        template{clearCacheBle.execute(ClearCacheBleUC.Request)} }
    private fun selectDeviceBle(device: DeviceBle) {
        template{selectDeviceBle.execute(SelectDeviceBleUC.Request(device)) } }
//    private fun showBottomSheet(show: ShowBottomSheet){
//        template{showBottomSheetUC.execute( ShowBottomSheetUC.Request(show)) } }
    private fun collapsingSet(collaps: Collapsing){
        template{collapsingSetUC.execute( CollapsingUC.Request(collaps)) } }
}
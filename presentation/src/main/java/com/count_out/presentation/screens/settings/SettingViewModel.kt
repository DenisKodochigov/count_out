package com.count_out.presentation.screens.settings

import androidx.lifecycle.viewModelScope
import com.count_out.domain.entity.Setting
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.domain.use_case.bluetooth.ClearCacheBleUC
import com.count_out.domain.use_case.bluetooth.GetConnectionStateUC
import com.count_out.domain.use_case.bluetooth.GetHeartRateUC
import com.count_out.domain.use_case.bluetooth.LastBleDeviceUC
import com.count_out.domain.use_case.bluetooth.SelectDeviceBleUC
import com.count_out.domain.use_case.bluetooth.StartScanBleUC
import com.count_out.domain.use_case.bluetooth.StopScanBleUC
import com.count_out.domain.use_case.other.CollapsingUC
import com.count_out.domain.use_case.other.ShowBottomSheetUC
import com.count_out.domain.use_case.plans.activity.AddActivityUC
import com.count_out.domain.use_case.plans.activity.DeleteActivityUC
import com.count_out.domain.use_case.plans.activity.GetActivitiesUC
import com.count_out.domain.use_case.plans.activity.UpdateActivityUC
import com.count_out.domain.use_case.settings.GetSettingsUC
import com.count_out.domain.use_case.settings.UpdateSettingUC
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeViewModel
import com.count_out.presentation.screens.prime.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
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
    private val showBottomSheetUC: ShowBottomSheetUC,
    private val collapsingSetUC: CollapsingUC,
    private val getConnectionState: GetConnectionStateUC,
    private val getHeartRate: GetHeartRateUC,
    private val getLastBleDevice: LastBleDeviceUC,
): PrimeViewModel<SettingsState, SettingsConvertor>() {

    override fun initScreenState(): ScreenState<SettingsState> = ScreenState.Loading
    override fun initDataState(): SettingsState = SettingsState(event = { submitEvent(it) })
    override fun convertor(): SettingsConvertor = SettingsConvertor()
    override fun routeEvent(event: Event) {
        when (event) {
            is SettingsEvent.BackScreen -> { navigate.backStack() }
            is SettingsEvent.AddActivity -> { addActivity(event.activity) }
            is SettingsEvent.DeleteActivity -> { delActivity(event.activity) }
            is SettingsEvent.UpdateActivity -> { updateActivity(event.activity) }
            is SettingsEvent.UpdateSetting -> { updateSetting(event.setting) }
            is SettingsEvent.GetSettings -> { getSettings() }
            is SettingsEvent.StartScanBLE -> { startScanBle() }
            is SettingsEvent.StopScanBLE -> { stopScanBle() }
            is SettingsEvent.SelectDevice -> { selectDeviceBle(event.device) }
            is SettingsEvent.ClearCacheBLE -> { clearCacheBle() }
            is SettingsEvent.ShowBS -> { showBottomSheet(event.item) }
            is SettingsEvent.SetCollapsing -> { collapsingSet(event.item) }
            is SettingsEvent.Init -> { init() }
        }
    }
    fun init() {
        getSettings()
        getsActivity()
        getConnectionState()
        getHeartRate()
        getLastBleDevice()
    }
    private fun getConnectionState() {
        viewModelScope.launch(Dispatchers.IO) {
            getConnectionState.execute(AddActivityUC.Request(activity))
                .collect { submitState( it ) } } }
    private fun getHeartRate() {
        viewModelScope.launch(Dispatchers.IO) {
            getHeartRate.execute(AddActivityUC.Request(activity))
                .collect { submitState( it ) } } }
    private fun getLastBleDevice() {
        viewModelScope.launch(Dispatchers.IO) {
            getLastBleDevice.execute(LastBleDeviceUC.Request)
                .collect { submitState( it ) } } }
    private fun addActivity(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            addActivity.execute(AddActivityUC.Request(activity))
                .collect { submitState( it ) } } }
    private fun getsActivity() {
        viewModelScope.launch(Dispatchers.IO) {
            getsActivity.execute(GetActivitiesUC.Request).collect { submitState( it ) } } }
    private fun delActivity(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            delActivity.execute(DeleteActivityUC.Request(activity))
                .collect { submitState( it ) } } }
    private fun updateActivity(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            updateActivity.execute(UpdateActivityUC.Request(activity))
                .collect { submitState( it ) } } }
    private fun getSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            getSettings.execute(GetSettingsUC.Request).collect { submitState( it ) } } }
    private fun updateSetting(setting: Setting) {
        viewModelScope.launch(Dispatchers.IO) {
            updateSetting.execute(UpdateSettingUC.Request(setting)).collect { submitState( it ) } } }
    private fun startScanBle() {
        viewModelScope.launch(Dispatchers.IO) {
            startScanBle.execute(StartScanBleUC.Request).collect { submitState( it ) } } }
    private fun stopScanBle() {
        viewModelScope.launch(Dispatchers.IO) {
            stopScanBle.execute(StopScanBleUC.Request).collect { submitState( it ) } } }
    private fun clearCacheBle() {
        viewModelScope.launch(Dispatchers.IO) {
            clearCacheBle.execute(ClearCacheBleUC.Request).collect { submitState( it ) } } }
    private fun selectDeviceBle(device: DeviceBle) {
        viewModelScope.launch(Dispatchers.IO) {
            selectDeviceBle.execute(SelectDeviceBleUC.Request(device))
                .collect { submitState( it ) } } }
    private fun showBottomSheet(show: ShowBottomSheet){
        viewModelScope.launch(Dispatchers.IO) {
            showBottomSheetUC.execute( ShowBottomSheetUC.Request(show))
                .collect { submitState( it ) } } }
    private fun collapsingSet(collaps: Collapsing){
        viewModelScope.launch(Dispatchers.IO) {
            collapsingSetUC.execute( CollapsingUC.Request(collaps))
                .collect { submitState( it ) } } }
}
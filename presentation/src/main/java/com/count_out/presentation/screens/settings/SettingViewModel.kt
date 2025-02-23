package com.count_out.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.count_out.app.data.DataRepository
import com.count_out.app.device.bluetooth.modules.BleDevSerializable
import com.count_out.app.entity.bluetooth.DeviceUI
import com.count_out.app.data.room.tables.SettingDB
import com.count_out.app.entity.CommandService
import com.count_out.app.entity.MessageApp
import com.count_out.presentation.models.DataForServImpl
import com.count_out.presentation.models.DataForUI
import com.count_out.app.services.count_out.CountOutServiceBind
import com.count_out.app.ui.view_components.lg
import com.count_out.domain.entity.Activity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val messageApp: MessageApp,
    private val serviceBind: CountOutServiceBind,
    private val dataRepository: DataRepository
): ViewModel() {
    private val _settingScreenState = MutableStateFlow(
        SettingScreenState(
            settings = emptyList(),
            heartRate = 0,
            onAddActivity = { activity-> onAddActivity( activity )},
            onUpdateActivity = { activity-> onUpdateActivity( activity )},
            onDeleteActivity = { activityId-> onDeleteActivity( activityId )},
            onUpdateSetting = { setting-> updateSetting( setting )},
            onGetSettings = { getSettings()},
            onStartScanBLE = { commandService(CommandService.START_SCANNING) },
            onStopScanBLE = { commandService(CommandService.STOP_SCANNING) },
            onSelectDevice = { address-> selectDevice(address) },
            onClearCacheBLE = {
                clearStoredDevice()
                commandService(CommandService.CLEAR_CACHE_BLE) },
            onSetColorActivity = { activityId, color ->
                onSetColorActivityForSettings( activityId = activityId, color = color) },
        ))
    val settingScreenState: StateFlow<SettingScreenState> = _settingScreenState.asStateFlow()

    private val dataForServ = DataForServImpl()

    init {
        startBle()
        getSettings()
        templateActivity {dataRepository.getActivities()}
    }

    private fun clearStoredDevice(){
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.storeSelectBleDev( BleDevSerializable())}
    }
    private fun startBle() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { serviceBind.service.startBle(dataForServ) }.fold(
                onSuccess = { receiveFormBle( it ) },
                onFailure ={}
            )
        }
    }

    private fun commandService(command: CommandService){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { serviceBind.service.commandService( command) }.fold(
                onSuccess = { },
                onFailure = { messageApp.errorApi("initServiceApp ${it.message ?: ""}") }
            )
        }
    }
    private fun onAddActivity(activity: Activity){
        templateActivity{
            if (activity.idActivity > 0) dataRepository.onUpdateActivity(activity)
            else dataRepository.onAddActivity(activity) } }
    private fun onUpdateActivity(activity: Activity){
        templateActivity{ dataRepository.onUpdateActivity(activity) } }
    private fun onDeleteActivity(activityId: Long){
        templateActivity{ dataRepository.onDeleteActivity(activityId) } }
    private fun onSetColorActivityForSettings(activityId: Long, color: Int){
        templateActivity{ dataRepository.onSetColorActivityForSettings(activityId, color) } }
    private fun updateSetting( setting: SettingDB ){
        templateSetting {dataRepository.updateSetting(setting)} }

    private fun selectDevice(device: DeviceUI) {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.storeSelectBleDev( BleDevSerializable( address = device.address, name = device.name))
            dataForServ.addressForSearch = device.address
            commandService(CommandService.CONNECT_DEVICE)
        }
    }

    private fun receiveFormBle(dataForUI: DataForUI) { //
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.getBleDevStoreFlow().collect { device ->
                lg("SettingViewModel device:${device} ")
                if (device.address.isNotEmpty()) {
                    _settingScreenState.update { state -> state.copy(lastConnectHearthRateDevice = device) }
                    dataForServ.addressForSearch = device.address
                    commandService(CommandService.CONNECT_DEVICE)
                }
            }
        } //lastConnectHearthRateDevice
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.bleConnectState.collect { bleState ->
                _settingScreenState.update { state -> state.copy( connectingState = bleState) }
            }
        } //connectingState
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.heartRate.collect { hr ->
                _settingScreenState.update { state -> state.copy(heartRate = hr) }
            }
        } //heartRate
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.scannedBle.collect { scannedBle ->
                _settingScreenState.update { state -> state.copy(scannedBle = scannedBle) }
            }
        } //scannedBle
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.foundDevices.collect { foundDevices ->
                lg("SettingViewModel foundDevices:$foundDevices")
                _settingScreenState.update { state -> state.copy(devicesUI = foundDevices) }
            }
        } //devicesUI
    }
    private fun getSettings(){
        templateSetting { dataRepository.getSettings() } }
    private fun templateSetting( funDataRepository:() -> List<SettingDB> ){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = { _settingScreenState.update { state -> state.copy( settings = it ) } },
                onFailure = { messageApp.errorApi(it.message ?: "") }
            )
        }
    }
    private fun templateActivity(funDataRepository:() -> List<Activity> ){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = { _settingScreenState.update { currentState ->
                    currentState.copy( activities = it ) } },
                onFailure = { messageApp.errorApi(it.message ?: "") }
            )
        }
    }

    override fun onCleared(){
        lg("#################################### SettingViewModel cleared ######################################")
        commandService(CommandService.STOP_SCANNING)
    }
}
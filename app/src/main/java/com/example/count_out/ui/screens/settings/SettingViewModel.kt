package com.example.count_out.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.data.room.tables.SettingDB
import com.example.count_out.entity.Activity
import com.example.count_out.entity.MessageApp
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToService
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.bluetooth.BleDevSerializable
import com.example.count_out.service_count_out.CountOutServiceBind
import com.example.count_out.ui.view_components.lg
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
            onStartScanBLE = { onStartScanBLE() },
            onStopScanBLE = { onStopScanBLE() },
            onClearCacheBLE = { onClearCacheBLE() },
            onSelectDevice = { address-> selectDevice(address) },
            onSetColorActivity = { activityId, color ->
                onSetColorActivityForSettings( activityId = activityId, color = color) },
        ))
    val settingScreenState: StateFlow<SettingScreenState> = _settingScreenState.asStateFlow()

    private var sendToService = SendToService()

    init {
        initServiceApp()
        getSettings()
        templateMy {dataRepository.getActivities()}
    }
    private fun initServiceApp(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                if ( !serviceBind.isBound.value ) {
                    serviceBind.bindService()
                    serviceBind.service.startCountOutService(sendToService)
                } else null
            }.fold(
                onSuccess = { it?.let { senUI -> receiveState(senUI)}},
                onFailure = { messageApp.errorApi("initServiceApp ${it.message ?: ""}") }
            )
        }
    }
    private fun onAddActivity(activity: Activity){
        templateMy{
            if (activity.idActivity > 0) dataRepository.onUpdateActivity(activity)
            else dataRepository.onAddActivity(activity) } }
    private fun onUpdateActivity(activity: Activity){
        templateMy{ dataRepository.onUpdateActivity(activity) } }
    private fun onDeleteActivity(activityId: Long){
        templateMy{ dataRepository.onDeleteActivity(activityId) } }
    private fun onSetColorActivityForSettings(activityId: Long, color: Int){
        templateMy{ dataRepository.onSetColorActivityForSettings(activityId, color) } }
    private fun getSettings(){
        templateSetting {dataRepository.getSettings()} }
    private fun updateSetting( setting: SettingDB ){
        templateSetting {dataRepository.updateSetting(setting)} }

    private fun onStartScanBLE(){
        lg("SettingViewModel.onStartScanBLE")
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { serviceBind.service.startScanningBle() }.fold(
                onSuccess = {  },
                onFailure = { messageApp.errorApi( it.message ?: "") }
            )
        }
    }

    private fun onStopScanBLE(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { serviceBind.service.stopScanningBle() }.fold(
                onSuccess = { },
                onFailure = { messageApp.errorApi(it.message ?: "") }
            )
        }
    }

    private fun onClearCacheBLE(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { serviceBind.service.onClearCacheBLE() }.fold(
                onSuccess = { },
                onFailure = { messageApp.errorApi(it.message  ?: "") }
            )
        }
    }

    private fun selectDevice(address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.storeSelectBleDev( BleDevSerializable( address = address))
            sendToService.addressForSearch = address
            serviceBind.service.connectDevice()
        }
    }

    private fun connectToStoredBleDev() {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.getBleDevStoreFlow().collect{
                if (it.address.isNotEmpty()) {
                    sendToService.addressForSearch = it.address
                    serviceBind.service.connectDevice()
                }
            }
        }
    }
    private fun receiveState(sendToUI: SendToUI) { //

        viewModelScope.launch(Dispatchers.IO) {
            serviceBind.isBound.collect { if (it) connectToStoredBleDev() }
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.lastConnectHearthRateDeviceF.collect { lastHR ->
                if (sendToUI.runningState.value == RunningState.Stopped) return@collect
                _settingScreenState.update { currentState ->
                    currentState.copy(lastConnectHearthRateDevice = lastHR) }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.connectingStateF.collect { state ->
                if (sendToUI.runningState.value == RunningState.Stopped) return@collect
                _settingScreenState.update { currentState ->
                    currentState.copy( connectingState = state,) }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.heartRateF.collect { hr ->
                if (sendToUI.runningState.value == RunningState.Stopped) return@collect
                _settingScreenState.update { currentState ->
                    currentState.copy(heartRate = hr) }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.scannedBleF.collect { scannedBle ->
                if (sendToUI.runningState.value == RunningState.Stopped) return@collect
                _settingScreenState.update { currentState ->
                    currentState.copy(scannedBle = scannedBle) }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.foundDevicesF.collect { foundDevices ->
                if (sendToUI.runningState.value == RunningState.Stopped) return@collect
                _settingScreenState.update { currentState ->
                    currentState.copy(devicesUI = foundDevices) }
            }
        }
    }

    private fun templateSetting( funDataRepository:() -> List<SettingDB> ){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = { _settingScreenState.update { currentState ->
                    currentState.copy( settings = it ) } },
                onFailure = { messageApp.errorApi(it.message ?: "") }
            )
        }
    }

    private fun templateMy( funDataRepository:() -> List<Activity> ){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = { _settingScreenState.update { currentState ->
                    currentState.copy( activities = it ) } },
                onFailure = { messageApp.errorApi(it.message ?: "") }
            )
        }
    }

    override fun onCleared(){
        lg("SettingViewModel cleared")
        lg("##########################################################################")
        serviceBind.service.stopScanningBle()
    }
}
package com.example.count_out.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.data.room.tables.SettingDB
import com.example.count_out.entity.Activity
import com.example.count_out.entity.ErrorApp
import com.example.count_out.entity.bluetooth.BleDevSerializable
import com.example.count_out.entity.bluetooth.ReceiveFromUI
import com.example.count_out.entity.bluetooth.SendToUI
import com.example.count_out.service.bluetooth.BleManager
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
    private val errorApp: ErrorApp,
    private val bleManager: BleManager,
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

    private val receiveFromUI = ReceiveFromUI()

    init {
        startBleService()
        getSettings()
        connectToStoredBleDev()
        templateMy {dataRepository.getActivities()}
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

    private fun startBleService(){
        lg("SettingViewModel.startBleService")
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { bleManager.startBleService(receiveFromUI) }.fold(
                onSuccess = { receiveDevicesUIBleService(it)},
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }

    private suspend fun receiveDevicesUIBleService(sendToUI: MutableStateFlow<SendToUI>){
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.collect { send ->
                _settingScreenState.update { currentState ->
                    currentState.copy(
                        lastConnectHearthRateDevice = send.lastConnectHearthRateDevice,
                        scannedBle = send.scannedBle,
                        devicesUI = send.foundDevices,
                        heartRate = send.heartRate,)
                }
            }
        }
    }

    private fun onStartScanBLE(){
        lg("SettingViewModel.onStartScanBLE")
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { bleManager.startScannerBLEDevices() }.fold(
                onSuccess = {  },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }

    private fun onStopScanBLE(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { bleManager.stopScannerBLEDevices() }.fold(
                onSuccess = { },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }

    private fun onClearCacheBLE(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { bleManager.onClearCacheBLE() }.fold(
                onSuccess = { },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }

    private fun selectDevice(address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.storeSelectBleDev( BleDevSerializable( address = address))
            receiveFromUI.addressForSearch = address
            bleManager.connectDevice()
        }
    }

    private fun connectToStoredBleDev() {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.getBleDevStoreFlow().collect{
                if (it.address.isNotEmpty()) {
                    receiveFromUI.addressForSearch = it.address
                    bleManager.connectDevice()
                }
            }
        }
    }

    private fun templateSetting( funDataRepository:() -> List<SettingDB> ){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = { _settingScreenState.update { currentState ->
                    currentState.copy( settings = it ) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }

    private fun templateMy( funDataRepository:() -> List<Activity> ){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = { _settingScreenState.update { currentState ->
                    currentState.copy( activities = it ) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }

    override fun onCleared(){
        lg("SettingViewModel cleared")
        lg("##########################################################################")
        bleManager.stopScannerBLEDevices()
        bleManager.stopServiceBle()
    }
}
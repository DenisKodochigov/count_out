package com.example.count_out.ui.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.data.room.tables.SettingDB
import com.example.count_out.entity.Activity
import com.example.count_out.entity.ErrorApp
import com.example.count_out.entity.bluetooth.BleDevSerializable
import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.entity.bluetooth.ReceiveFromUI
import com.example.count_out.entity.bluetooth.SendToUI
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service.bluetooth.BleManager
import com.example.count_out.ui.view_components.lg
import com.example.count_out.ui.view_components.log
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
    private val dataRepository: DataRepository,
    private val permissionApp: PermissionApp
): ViewModel() {
    private val _settingScreenState = MutableStateFlow(
        SettingScreenState(
            settings =mutableStateOf(emptyList()),
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
    private var doConnecting = false

    init {
        connectingToServiceBle()
        getSettings()
        getStoredBleDev()
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
        log(true, "onSetColorActivityForSettings: $this")
        templateMy{ dataRepository.onSetColorActivityForSettings(activityId, color) } }

    private fun getSettings(){
        templateSetting {dataRepository.getSettings()} }

    private fun updateSetting( setting: SettingDB ){
        templateSetting {dataRepository.updateSetting(setting)} }

    private fun connectingToServiceBle(){
        lg("SettingViewModel.connectingToServiceBle")
        doConnecting = false
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { bleManager.connectingToServiceBle(receiveFromUI) }.fold(
                onSuccess = { receiveValOutBleService(it) },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }

    private suspend fun receiveValOutBleService(sendToUI: SendToUI){
        sendToUI.foundDevices.collect{ listDevice->
//            lg("SettingViewModel.receiveBluetoothDevices valOut.listDevice: $listDevice")
            _settingScreenState.update { currentState ->
                currentState.copy( devicesUI = mutableStateOf( listDevice)) }
        }
    }

    private fun onStartScanBLE(){
        lg("SettingViewModel.onStartScanBLE")
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { bleManager.startScannerBLEDevices() }.fold(
                onSuccess = { },
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

    private fun getStoredBleDev() {
        viewModelScope.launch(Dispatchers.IO) {
            doConnecting = true
            dataRepository.getBleDevStoreFlow().collect{
                if (it.address.isNotEmpty()) bleManager.startScannerBleDeviceByMac(it as DeviceUI)
                lg("SettingViewModel.getStoredBleDev mac: ${it.address}")
            }
        }
    }

    private fun selectDevice(address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            bleManager.stopScannerBLEDevices()
            dataRepository.storeSelectBleDev( BleDevSerializable( address = address))
            receiveFromUI.addressForSearch = address
            bleManager.connectDevice()
        }
    }

    private fun templateSetting( funDataRepository:() -> List<SettingDB> ){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = { _settingScreenState.update { currentState ->
                    currentState.copy(settings = mutableStateOf(it) ) } },
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
    //
//    private suspend fun receiveListTest(valOutBleService: ValOutBleService){
//        valOutBleService.list.collect{ list->
//            lg("SettingViewModel.receiveListTest valOut.list: $list")
//        }
//    }
//    private fun receiveBluetoothDevices(){
//        viewModelScope.launch(Dispatchers.IO) {
//            valOutServiceBle.listDevice.collect { listDevice ->
//                _settingScreenState.update { currentState ->
//                    currentState.copy(bluetoothDevices = mutableStateOf(listDevice))
//                }
//                if (listDevice.isNotEmpty()) {
//                    lg("SettingViewModel.receiveBluetoothDevices valOut.listDevice: ${listDevice[0].address}")
//                }
//            }
//        }
//    }
//    private fun receiveBluetoothDevices(){
//        viewModelScope.launch(Dispatchers.IO) {
//            bleManager.getDevices().collect{
//                _settingScreenState.update { currentState ->
//                    currentState.copy(bluetoothDevices = mutableStateOf(it)) } }
//        }
//    }
//    private fun receiveBluetoothDeviceByMac(){
//        lg("SettingViewModel.receiveBluetoothDeviceByMac")
//        viewModelScope.launch(Dispatchers.IO) {
//            bleManager.getDeviceByMac().collect{ listDeviceBle ->
//                if (listDeviceBle.isNotEmpty()) {
//                    listDeviceBle[0].let { device ->
//                        lg("SettingViewModel.receiveBluetoothDeviceByMac ${device.address}")
//                        bleManager.stopScannerBLEDevicesByMac()
//                        bleManager.connectDevice()
//                        _settingScreenState.update { currentState ->
//                            currentState.copy(lastConnectHearthRate = mutableStateOf(device))
//                        }
//                    }
//                }
//            }
//        }
//    }
//    private fun onStartScanBLE(){
//        lg("SettingViewModel onStartScanBLE")
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { bleManager.startScannerBLEDevices() }.fold(
//                onSuccess = { _settingScreenState.update { currentState ->
//                    currentState.copy(bluetoothDevices = mutableStateOf(it)) }},
//                onFailure = { errorApp.errorApi(it.message!!) }
//            )
//        }
////        receiveBluetoothDevices()
//    }
}
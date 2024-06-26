package com.example.count_out.ui.screens.settings

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.data.room.tables.SettingDB
import com.example.count_out.entity.Activity
import com.example.count_out.entity.BleDev
import com.example.count_out.entity.BluetoothDeviceApp
import com.example.count_out.entity.ErrorApp
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service.bluetooth.BleApp
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
    private val bleApp: BleApp,
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
            onSelectDevice = { device-> selectDevice(device) },
            onSetColorActivity = { activityId, color ->
                onSetColorActivityForSettings( activityId = activityId, color = color) },
        ))
    val settingScreenState: StateFlow<SettingScreenState> = _settingScreenState.asStateFlow()
    val currentBleDevice: MutableStateFlow<BluetoothDeviceApp> = MutableStateFlow(BluetoothDeviceApp())

    init {
        getSettings()
        getStoreBleDev()
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
//    fun getWorkouts(){ templateMy { dataRepository.getWorkouts() } }
//    fun changeNameWorkout(workout: Workout){ templateMy { dataRepository.changeNameWorkout(workout) } }
//    fun deleteWorkout(id: Long){ templateMy { dataRepository.deleteWorkout(id) } }
//    fun addWorkout(name: String){ templateMy { dataRepository.addWorkout(name) } }

    private fun getSettings(){
        templateSetting {dataRepository.getSettings()} }
    private fun updateSetting( setting: SettingDB ){
        templateSetting {dataRepository.updateSetting(setting)} }


    private fun onStartScanBLE(){
        lg("SettingViewModel onStartScanBLE")
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { bleApp.startScannerBLEDevices() }.fold(
                onSuccess = { },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
        receiveBluetooth()
    }
    private fun onStopScanBLE(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { bleApp.stopScannerBLEDevices() }.fold(
                onSuccess = { },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
    private fun onClearCacheBLE(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { bleApp.onClearCacheBLE() }.fold(
                onSuccess = { },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
        receiveBluetooth()
    }
    private fun getStoreBleDev() {
        lg("SettingViewModel getStoreBleDev")
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.getBleDevStoreFlow().collect{
                bleApp.startScannerBleDeviceByMac(it.mac)
            }
        }
        receiveBluetoothDeviceByMac()
    }
    @SuppressLint("MissingPermission")
    private fun selectDevice(device: BluetoothDevice) {
        val nameDevice= permissionApp.checkBleScan { device.name } as String
        viewModelScope.launch(Dispatchers.IO) {
            bleApp.stopScannerBLEDevices()
            dataRepository.storeSelectBleDev(BleDev( name = nameDevice, mac = device.address)) }
        bleApp.connectDevice(device)
    }
    private fun receiveBluetooth(){
        viewModelScope.launch(Dispatchers.IO) {
            bleApp.getDevices().collect{
                _settingScreenState.update { currentState ->
                    currentState.copy(bluetoothDevices = mutableStateOf(it)) } }
        }
    }
    private fun receiveBluetoothDeviceByMac(){
        lg("SettingViewModel.receiveBluetoothDeviceByMac")
        viewModelScope.launch(Dispatchers.IO) {
            bleApp.getDeviceByMac().collect{ listDeviceBle ->
                if (listDeviceBle.isNotEmpty()) {
                    listDeviceBle[0].let { device ->
                        lg("SettingViewModel.receiveBluetoothDeviceByMac ${device.address}")
                        bleApp.stopScannerBLEDevicesByMac()
                        bleApp.connectDevice(device)
                        _settingScreenState.update { currentState ->
                            currentState.copy(lastConnectHearthRate = mutableStateOf(device))
                        }
                    }
                }
            }
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
                    currentState.copy(activities = it ) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
    override fun onCleared(){
        lg("SettingViewModel cleared")
        bleApp.stopScannerBLEDevices()
    }
}
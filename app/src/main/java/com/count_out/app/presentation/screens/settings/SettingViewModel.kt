package com.count_out.app.presentation.screens.settings

import androidx.lifecycle.viewModelScope
import com.count_out.app.presentation.prime.Event
import com.count_out.app.presentation.prime.PrimeViewModel
import com.count_out.app.presentation.prime.ScreenState
import com.count_out.domain.entity.SettingRecord
import com.count_out.domain.use_case.activity.AddActivityUC
import com.count_out.domain.use_case.activity.DeleteActivityUC
import com.count_out.domain.use_case.activity.GetsActivityUC
import com.count_out.domain.use_case.activity.SetColorActivityUC
import com.count_out.domain.use_case.activity.UpdateActivityUC
import com.count_out.domain.use_case.bluetooth.ClearCacheBleUC
import com.count_out.domain.use_case.bluetooth.SelectDeviceBleUC
import com.count_out.domain.use_case.bluetooth.StartScanBleUC
import com.count_out.domain.use_case.bluetooth.StopScanBleUC
import com.count_out.domain.use_case.settings.GetSettingUC
import com.count_out.domain.use_case.settings.GetSettingsUC
import com.count_out.domain.use_case.settings.UpdateSettingUC
import com.count_out.domain.entity.router.DeviceUI
import com.count_out.domain.entity.workout.Activity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val converter: SettingsConvertor,
    private val addActivity: AddActivityUC,
    private val delActivity: DeleteActivityUC,
    private val getsActivity: GetsActivityUC,
    private val updateActivity: UpdateActivityUC,
    private val setColorActivity: SetColorActivityUC,
    private val clearCacheBle: ClearCacheBleUC,
    private val startScanBle: StartScanBleUC,
    private val stopScanBle: StopScanBleUC,
    private val selectDeviceBle: SelectDeviceBleUC,
    private val getSetting: GetSettingUC,
    private val getSettings: GetSettingsUC,
    private val updateSetting: UpdateSettingUC,
//    private val serviceBind: CountOutServiceBind,
//    private val dataRepository: DataRepository
): PrimeViewModel<SettingsState, ScreenState<SettingsState>>() {
    override fun initState(): ScreenState<SettingsState> = ScreenState.Loading
    override fun routeEvent(event: Event) {
        when (event) {
            is SettingsEvent.BackScreen -> { navigate.backStack()}
            is SettingsEvent.AddActivity -> { addActivity(event.activity) }
            is SettingsEvent.DeleteActivity -> { delActivity(event.activity) }
            is SettingsEvent.UpdateActivity -> { updateActivity(event.activity) }
            is SettingsEvent.SetColorActivity -> { setColorActivity(event.activity) }
            is SettingsEvent.UpdateSetting -> { updateSetting(event.setting) }
            is SettingsEvent.GetSetting -> { getSetting(event.setting) }
            is SettingsEvent.GetSettings -> { getSettings() }
            is SettingsEvent.StartScanBLE -> { startScanBle() }
            is SettingsEvent.StopScanBLE -> { stopScanBle() }
            is SettingsEvent.SelectDevice -> { selectDeviceBle(event.device) }
            is SettingsEvent.ClearCacheBLE -> { clearCacheBle() }
            is SettingsEvent.Init -> { init() }
        }
    }
    private fun addActivity(activity: Activity){
        viewModelScope.launch {
            addActivity.execute( AddActivityUC.Request(activity))
                .map { converter.convert(it) }.collect { submitState(it) }
    } }
    private fun getsActivity(){
        viewModelScope.launch {
            getsActivity.execute( GetsActivityUC.Request)
                .map { converter.convert(it) }.collect { submitState(it) }
        } }
    private fun delActivity(activity: Activity){
        viewModelScope.launch {
            delActivity.execute( DeleteActivityUC.Request(activity))
                .map { converter.convert(it) }.collect { submitState(it) }
        } }
    private fun updateActivity(activity: Activity){
        viewModelScope.launch {
            updateActivity.execute( UpdateActivityUC.Request(activity))
                .map { converter.convert(it) }.collect { submitState(it) }
        } }
    private fun setColorActivity(activity: Activity){
        viewModelScope.launch {
            setColorActivity.execute( SetColorActivityUC.Request(activity))
                .map { converter.convert(it) }.collect { submitState(it) }
        } }
    private fun updateSetting(setting: SettingRecord){
        viewModelScope.launch {
            updateSetting.execute( UpdateSettingUC.Request(setting))
                .map { converter.convert(it) }.collect { submitState(it) }
        } }
    private fun getSetting(setting: SettingRecord){
        viewModelScope.launch {
            getSetting.execute( GetSettingUC.Request(setting))
                .map { converter.convert(it) }.collect { submitState(it) }
        } }
    private fun getSettings(){
        viewModelScope.launch {
            getSettings.execute( GetSettingsUC.Request)
                .map { converter.convert(it) }.collect { submitState(it) }
        } }
    private fun startScanBle(){
        viewModelScope.launch {
            startScanBle.execute( StartScanBleUC.Request)
                .map { converter.convert(it) }.collect { submitState(it) }
        } }
    private fun stopScanBle(){
        viewModelScope.launch {
            stopScanBle.execute( StopScanBleUC.Request)
                .map { converter.convert(it) }.collect { submitState(it) }
        } }
    private fun clearCacheBle(){
        viewModelScope.launch {
            clearCacheBle.execute( ClearCacheBleUC.Request)
                .map { converter.convert(it) }.collect { submitState(it) }
        } }
    private fun selectDeviceBle(device: DeviceUI){
        viewModelScope.launch {
            selectDeviceBle.execute( SelectDeviceBleUC.Request(device))
                .map { converter.convert(it) }.collect { submitState(it) }
        } }

    fun init(){
        getSettings()
        getsActivity()
    }
    fun getDevBleStored(){}
    fun getStateScanningBle(){}
    fun getStateConnectingBle(){}
    fun getHearthRate(){}
//    private val _settingScreenState = MutableStateFlow(
//        SettingScreenState(
//            settings = emptyList(),
//            heartRate = 0,
//            onAddActivity = { activity-> onAddActivity( activity )},
//            onUpdateActivity = { activity-> onUpdateActivity( activity )},
//            onDeleteActivity = { activityId-> onDeleteActivity( activityId )},
//            onUpdateSetting = { setting-> updateSetting( setting )},
//            onGetSettings = { getSettings()},
//            onStartScanBLE = { commandService(CommandService.START_SCANNING) },
//            onStopScanBLE = { commandService(CommandService.STOP_SCANNING) },
//            onSelectDevice = { address-> selectDevice(address) },
//            onClearCacheBLE = {
//                clearStoredDevice()
//                commandService(CommandService.CLEAR_CACHE_BLE) },
//            onSetColorActivity = { activityId, color ->
//                onSetColorActivityForSettings( activityId = activityId, color = color) },
//        ))
//    val settingScreenState: StateFlow<SettingScreenState> = _settingScreenState.asStateFlow()
//
//    private val dataForServ = DataForServImpl()
//
//    init {
//        startBle()
//        getSettings()
//        templateActivity {dataRepository.getActivities()}
//    }
//
//    private fun clearStoredDevice(){
//        viewModelScope.launch(Dispatchers.IO) {
//            dataRepository.storeSelectBleDev( BleDevSerializable())}
//    }
//    private fun startBle() {
//        viewModelScope.launch(Dispatchers.IO) {
//            runCatching { serviceBind.service.startBle(dataForServ) }.fold(
//                onSuccess = { receiveFormBle( it ) },
//                onFailure ={}
//            )
//        }
//    }
//
//    private fun commandService(command: CommandService){
//        viewModelScope.launch(Dispatchers.IO) {
//            runCatching { serviceBind.service.commandService(command) }.fold(
//                onSuccess = { },
//                onFailure = { messageApp.errorApi("initServiceApp ${it.message ?: ""}") }
//            )
//        }
//    }
//    private fun onAddActivity(activity: Activity){
//        templateActivity{
//            if (activity.idActivity > 0) dataRepository.onUpdateActivity(activity)
//            else dataRepository.onAddActivity(activity) } }
//    private fun onUpdateActivity(activity: Activity){
//        templateActivity{ dataRepository.onUpdateActivity(activity) } }
//    private fun onDeleteActivity(activityId: Long){
//        templateActivity{ dataRepository.onDeleteActivity(activityId) } }
//    private fun onSetColorActivityForSettings(activityId: Long, color: Int){
//        templateActivity{ dataRepository.onSetColorActivityForSettings(activityId, color) } }
//    private fun updateSetting( setting: SettingDB ){
//        templateSetting {dataRepository.updateSetting(setting)} }
//
//    private fun selectDevice(device: DeviceUI) {
//        viewModelScope.launch(Dispatchers.IO) {
//            dataRepository.storeSelectBleDev( BleDevSerializable( address = device.address, name = device.name))
//            dataForServ.addressForSearch = device.address
//            commandService(CommandService.CONNECT_DEVICE)
//        }
//    }
//
//    private fun receiveFormBle(dataForUI: DataForUIImpl) { //
//        viewModelScope.launch(Dispatchers.IO) {
//            dataRepository.getBleDevStoreFlow().collect { device ->
//                lg("SettingViewModel device:${device} ")
//                if (device.address.isNotEmpty()) {
//                    _settingScreenState.update { state -> state.copy(lastConnectHearthRateDevice = device) }
//                    dataForServ.addressForSearch = device.address
//                    commandService(CommandService.CONNECT_DEVICE)
//                }
//            }
//        } //lastConnectHearthRateDevice
//        viewModelScope.launch(Dispatchers.IO) {
//            dataForUI.bleConnectState.collect { bleState ->
//                _settingScreenState.update { state -> state.copy( connectingState = bleState) }
//            }
//        } //connectingState
//        viewModelScope.launch(Dispatchers.IO) {
//            dataForUI.heartRate.collect { hr ->
//                _settingScreenState.update { state -> state.copy(heartRate = hr) }
//            }
//        } //heartRate
//        viewModelScope.launch(Dispatchers.IO) {
//            dataForUI.scannedBle.collect { scannedBle ->
//                _settingScreenState.update { state -> state.copy(scannedBle = scannedBle) }
//            }
//        } //scannedBle
//        viewModelScope.launch(Dispatchers.IO) {
//            dataForUI.foundDevices.collect { foundDevices ->
//                lg("SettingViewModel foundDevices:$foundDevices")
//                _settingScreenState.update { state -> state.copy(devicesUI = foundDevices) }
//            }
//        } //devicesUI
//    }
//    private fun getSettings(){
//        templateSetting { dataRepository.getSettings() } }
//    private fun templateSetting( funDataRepository:() -> List<SettingDB> ){
//        viewModelScope.launch(Dispatchers.IO) {
//            runCatching { funDataRepository() }.fold(
//                onSuccess = { _settingScreenState.update { state -> state.copy( settings = it ) } },
//                onFailure = { messageApp.errorApi(it.message ?: "") }
//            )
//        }
//    }
//    private fun templateActivity(funDataRepository:() -> List<Activity> ){
//        viewModelScope.launch(Dispatchers.IO) {
//            runCatching { funDataRepository() }.fold(
//                onSuccess = { _settingScreenState.update { currentState ->
//                    currentState.copy( activities = it ) } },
//                onFailure = { messageApp.errorApi(it.message ?: "") }
//            )
//        }
//    }
//
//    override fun onCleared(){
//        lg("#################################### SettingViewModel cleared ######################################")
//        commandService(CommandService.STOP_SCANNING)
//    }
}
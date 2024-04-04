package com.example.count_out.ui.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.data.room.tables.SettingDB
import com.example.count_out.entity.Activity
import com.example.count_out.entity.ErrorApp
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
    private val dataRepository: DataRepository
): ViewModel() {
    private val _settingScreenState = MutableStateFlow(
        SettingScreenState(
            onAddActivity = { activity-> onAddActivity( activity )},
            onUpdateActivity = { activity-> onUpdateActivity( activity )},
            onDeleteActivity = { activityId-> onDeleteActivity( activityId )},
            onUpdateSetting = { setting-> updateSetting( setting )},
            onGetSettings = { getSettings()},
            onSetColorActivity = { activityId, color ->
                onSetColorActivityForSettings( activityId = activityId, color = color) },
        ))
    val settingScreenState: StateFlow<SettingScreenState> = _settingScreenState.asStateFlow()

    fun init(){
        getSettings()
        templateMy{dataRepository.getActivities()} }

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
    private fun templateMy( funDataRepository:() -> List<Activity> ){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = { _settingScreenState.update { currentState ->
                    currentState.copy(activities = it ) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
    private fun getSettings(){
        templateSetting {dataRepository.getSettings()} }
    private fun updateSetting( setting: SettingDB ){
        templateSetting {dataRepository.updateSetting(setting)} }
    private fun templateSetting( funDataRepository:() -> List<SettingDB> ){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = { _settingScreenState.update { currentState ->
                    currentState.copy(settings = mutableStateOf(it) ) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
}
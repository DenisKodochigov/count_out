package com.example.count_out.ui.screens.trainings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.entity.ErrorApp
import com.example.count_out.entity.Training
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingsViewModel @Inject constructor(
    private val errorApp: ErrorApp,
    private val dataRepository: DataRepository
): ViewModel() {
    private val _trainingsScreenState = MutableStateFlow(
        TrainingsScreenState(
            trainings = emptyList(),
            deleteTraining = { trainingId -> deleteTraining(trainingId) },
            onCopyTraining = { trainingId -> copyTraining(trainingId) },
        )
    )
    val trainingsScreenState: StateFlow<TrainingsScreenState> = _trainingsScreenState.asStateFlow()

    fun getTrainings(){ templateMy { dataRepository.getTrainings() } }
    fun deleteTraining(id: Long){ templateMy { dataRepository.deleteTraining(id) } }
    fun copyTraining(id: Long){ templateMy { dataRepository.copyTraining(id) } }
    private fun templateMy( funDataRepository:() -> List<Training> ){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = {  _trainingsScreenState.update {
                    currentState -> currentState.copy( trainings = it ) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
}
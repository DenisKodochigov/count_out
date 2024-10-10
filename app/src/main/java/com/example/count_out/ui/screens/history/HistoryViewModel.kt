package com.example.count_out.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.entity.MessageApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel  @Inject constructor(
    private val messageApp: MessageApp,
    private val dataRepository: DataRepository
): ViewModel() {
    private val _historyScreenState = MutableStateFlow(
        HistoryScreenState(
            getTraining = { getTraining( )},
            getTrainings = { getTrainings()},
        )
    )
    val historyScreenState: StateFlow<HistoryScreenState> = _historyScreenState.asStateFlow()
    private fun getTraining(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {  }.fold(
                onSuccess = { },
                onFailure = { messageApp.errorApi("initServiceApp ${it.message ?: ""}") }
            )
        }
    }
    private fun getTrainings(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {  }.fold(
                onSuccess = { },
                onFailure = { messageApp.errorApi("initServiceApp ${it.message ?: ""}") }
            )
        }
    }
}
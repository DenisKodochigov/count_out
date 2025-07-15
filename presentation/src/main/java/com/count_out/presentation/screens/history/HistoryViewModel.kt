package com.count_out.presentation.screens.history

import androidx.lifecycle.viewModelScope
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeViewModel
import com.count_out.presentation.screens.prime.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel  @Inject constructor(): PrimeViewModel<HistoryState, HistoryConvertor>() {
    private val _historyState = MutableStateFlow(
        HistoryState(
            getTraining = { },
            getTrainings = { },
            event = { submitEvent(it)}
        )
    )
    val historyState: StateFlow<HistoryState> = _historyState.asStateFlow()
    private fun getTraining(){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {  }.fold(
                onSuccess = { },
                onFailure = {
//                    messageApp.errorApi("initServiceApp ${it.message ?: ""}")
                }
            )
        }
    }
    private fun getTrainings(){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {  }.fold(
                onSuccess = { },
                onFailure = {
//                    messageApp.errorApi("initServiceApp ${it.message ?: ""}")
                }
            )
        }
    }

    override fun initScreenState(): ScreenState<HistoryState>  = ScreenState.Loading

    override fun initDataState(): HistoryState = HistoryState(event = { submitEvent(it)})

    override fun convertor() = HistoryConvertor()

    override fun routeEvent(event: Event) {    }
}
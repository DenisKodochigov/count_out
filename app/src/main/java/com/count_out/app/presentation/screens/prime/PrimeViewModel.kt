package com.count_out.app.presentation.screens.prime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.count_out.app.presentation.navigation.NavigateEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class PrimeViewModel<T: Any, S: ScreenState<T>>: ViewModel() {
    abstract fun initState(): S
    abstract fun routeEvent(event: Event)

    private val eventFlow: MutableSharedFlow<Event> = MutableSharedFlow()
    private val _dataState: MutableStateFlow<S> by lazy { MutableStateFlow(initState()) }
    val dataState: StateFlow<S> = _dataState

    lateinit var navigate: NavigateEvent

    init { viewModelScope.launch { eventFlow.collect { routeEvent(it) } } }

    fun initNavigate(navigateEvent: NavigateEvent) { navigate = navigateEvent}
    fun submitEvent(event: Event) { viewModelScope.launch { eventFlow.emit(event) } }
    fun submitState(state: S) { viewModelScope.launch { _dataState.value = state } }
}
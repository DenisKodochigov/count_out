package com.count_out.presentation.screens.prime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.count_out.domain.entity.NavigateEvent
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class PrimeViewModel<T: Any, C: PrimeConvertor<UseCase.Response,T>>: ViewModel(){
    abstract fun initScreenState(): ScreenState<T>
    abstract fun initDataState(): T
    abstract fun initConvertor(): C
    abstract fun routeEvent(event: Event)

    private val eventFlow: MutableSharedFlow<Event> = MutableSharedFlow()
    val dataState: MutableStateFlow<T> =  MutableStateFlow(initDataState())

    private val _screenState: MutableStateFlow< ScreenState<T>> by lazy { MutableStateFlow(initScreenState()) }
    val screenState: StateFlow< ScreenState<T>> = _screenState

    lateinit var navigate: NavigateEvent

    init { viewModelScope.launch { eventFlow.collect { routeEvent(it) } } }

    fun initNavigate(navigateEvent: NavigateEvent) { navigate = navigateEvent}
    fun submitEvent(event: Event) { viewModelScope.launch { eventFlow.emit(event) } }
    fun convert( result: ResultUC<UseCase.Response>): ScreenState<T> = initConvertor().convert(result, dataState)
    fun submitState(result: ResultUC<UseCase.Response>){ viewModelScope.launch { _screenState.value = convert(result) }}
}
package com.count_out.presentation.screens.prime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class PrimeViewModel<T: Any, C: PrimeConvertor<UseCase.Response,T>>: ViewModel(){
    abstract fun initScreenState(): ScreenState<T>
    abstract fun initDataState(): T
    abstract fun convertor(): C
    abstract fun routeEvent(event: Event)

    val eventFlow: MutableSharedFlow<Event> = MutableSharedFlow()
    val dataState: MutableStateFlow<T> =  MutableStateFlow(initDataState())

    private val _screenState: MutableStateFlow< ScreenState<T>> by lazy {
        MutableStateFlow(initScreenState()) }
    val screenState: StateFlow< ScreenState<T>> = _screenState

//    lateinit var navigate: NavigateEvent
//    fun initNavigate(navigateEvent: NavigateEvent) { navigate = navigateEvent}

    init { viewModelScope.launch { eventFlow.collect { routeEvent(it) } } }

    fun submitEvent(event: Event) { viewModelScope.launch { eventFlow.emit(event) } }
    fun submitState(result: ResultDomain<UseCase.Response>){
        viewModelScope.launch { _screenState.value = convertor().make(result, dataState) }}
}
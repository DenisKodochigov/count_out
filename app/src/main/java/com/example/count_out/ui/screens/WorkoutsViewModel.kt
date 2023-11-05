package com.example.count_out.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.entity.ErrorApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutsViewModel@Inject constructor(
    private val errorApp: ErrorApp,
//    private val dataRepository: DataRepository
): ViewModel()  {
//    private val _basketScreenState = MutableStateFlow(BasketScreenState())
//    val basketScreenState: StateFlow<BasketScreenState> = _basketScreenState.asStateFlow()
//
//    fun getListBasket() {
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { dataRepository.getListBasket() }.fold(
//                onSuccess = { _basketScreenState.update { currentState ->
//                    currentState.copy(baskets = it ) } },
//                onFailure = { errorApp.errorApi(it.message!!) }
//            )
//        }
//    }
}
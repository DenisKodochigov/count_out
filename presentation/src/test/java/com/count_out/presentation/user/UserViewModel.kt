//package com.example.presentation_user.single
//
//import androidx.lifecycle.viewModelScope
//import com.example.domen.usecase.GetUserUseCase
//import com.example.presentation_common.state.MviViewModel
//import com.example.presentation_common.state.UiSingleEvent
//import com.example.presentation_common.state.UiState
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class UserViewModel @Inject constructor(
//    private val userUseCase: GetUserUseCase,
//    private val converter: UserConverter
//) : MviViewModel<UserModel, UiState<UserModel>, UserUiAction, UiSingleEvent>() {
//
//    override fun initState(): UiState<UserModel> = UiState.Loading
//
//    override fun handleAction(action: UserUiAction) {
//        when (action) {
//            is UserUiAction.Load -> {
//                loadUser(action.userId)
//            }
//        }
//    }
//
//    private fun loadUser(userId: Long) {
//        viewModelScope.launch {
//            userUseCase.execute(GetUserUseCase.Request(userId))
//                .map {
//                    converter.convert(it)
//                }
//                .collect {
//                    submitState(it)
//                }
//        }
//    }
//}
package com.example.count_out.ui.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.navigation.ScreenDestination

@SuppressLint("UnrememberedMutableState")
@Composable
fun WorkoutsScreen(
    onClickBasket: (Long) -> Unit,
    screen: ScreenDestination,
    showBottomSheet: MutableState<Boolean>
) {

    val viewModel: WorkoutsViewModel = hiltViewModel()
//    viewModel.getListBasket()
//    BasketScreenCreateView(
//        onClickBasket = onClickBasket,
//        screen = screen,
//        viewModel = viewModel,
//        showBottomSheet = showBottomSheet,
//    )
}
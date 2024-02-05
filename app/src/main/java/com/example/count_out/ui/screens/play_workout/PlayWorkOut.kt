package com.example.count_out.ui.screens.play_workout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.ui.view_components.ButtonApp

@SuppressLint("UnrememberedMutableState")
@Composable
fun PlayWorkoutScreen(
    trainingId: Long,
    onBaskScreen:() -> Unit
){
    val viewModel: PlayWorkoutViewModel = hiltViewModel()

    LaunchedEffect( key1 = true, block = {
        viewModel.getTraining(trainingId) })
    PlayWorkoutScreenCreateView( viewModel = viewModel, onBaskScreen = onBaskScreen)
}
@Composable
fun PlayWorkoutScreenCreateView( viewModel: PlayWorkoutViewModel, onBaskScreen:() -> Unit
){
    val uiState by viewModel.playWorkoutScreenState.collectAsState()
    uiState.onBaskScreen = onBaskScreen
    PlayWorkoutScreenLayout(uiState = uiState)
}

@Composable fun PlayWorkoutScreenLayout( uiState: PlayWorkoutScreenState
){
    Column(
        modifier = Modifier.fillMaxHeight(),
        content = { PlayWorkoutScreenLayoutContent(uiState)}
    )
}
@Composable fun PlayWorkoutScreenLayoutContent( uiState: PlayWorkoutScreenState
){
    Row( horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Bottom,
        modifier = Modifier.fillMaxWidth(),){
        ButtonApp(text = "Start", onClick = { onButtonStart(uiState) }, modifier = Modifier.padding(18.dp))
        ButtonApp(text = "Pause", onClick = { onButtonPause(uiState) }, modifier = Modifier.padding(18.dp))
        ButtonApp(text = "Stop", onClick = { onButtonStop(uiState) }, modifier = Modifier.padding(18.dp))
    }
}

fun onButtonStart(uiState:PlayWorkoutScreenState){
    uiState.training?.let { uiState.startWorkOutService(it) }
}
fun onButtonPause(uiState:PlayWorkoutScreenState){
    uiState.pauseWorkOutService()
}
fun onButtonStop(uiState:PlayWorkoutScreenState){
   uiState.stopWorkOutService()
}
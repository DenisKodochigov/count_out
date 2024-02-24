package com.example.count_out.ui.screens.play_workout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.view_components.ButtonApp
import com.example.count_out.ui.view_components.TextApp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@SuppressLint("UnrememberedMutableState")
@Composable
fun PlayWorkoutScreen(
    trainingId: Long,
    onBaskScreen:() -> Unit
){
    val viewModel: PlayWorkoutViewModel = hiltViewModel()
    LaunchedEffect( key1 = true, block = { viewModel.getTraining(trainingId) })
    PlayWorkoutScreenCreateView( viewModel = viewModel, onBaskScreen = onBaskScreen)
}
@Composable
fun PlayWorkoutScreenCreateView( viewModel: PlayWorkoutViewModel, onBaskScreen:() -> Unit
){
    val uiState by viewModel.playWorkoutScreenState.collectAsState()
    val state by viewModel.stateWorkoutService.collectAsState()
    if (state.state != null)  uiState.stateWorkout.add(state)
    else uiState.startTime = state.time!!
    uiState.onBaskScreen = onBaskScreen
    PlayWorkoutScreenLayout(uiState = uiState)
}

@Composable fun PlayWorkoutScreenLayout( uiState: PlayWorkoutScreenState
){
    Column(
        modifier = Modifier.fillMaxSize(),
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
    ListState(uiState)
}

@Composable fun ListState(uiState: PlayWorkoutScreenState)
{
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center) {
        uiState.stateWorkout.forEach { state ->
            Row (modifier = Modifier.padding(horizontal = 12.dp)){
                TextApp(text = getDuration( startTime = uiState.startTime, time = state.time!!),
                    modifier = Modifier.width(60.dp),
                    style = interLight12)
                Spacer(modifier = Modifier.width(12.dp))
                TextApp(text = state.state.toString(), style = interLight12)
            }
        }
    }
}

fun getDuration(startTime: Long, time: Long): String{
    val duration: Long = time - startTime
    val hour = duration / 1000 / 60 / 60
    val minutes = duration / 1000 / 60
    val seconds = duration / 1000 % 60
    return String.format("%02d:%02d:%02d",hour, minutes, seconds)
}
fun Long.getTime(): String {
    val date = Date(this.toLong())
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    formatter.setTimeZone(TimeZone.getTimeZone("UTC"))
    return formatter.format(date)
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
package com.example.count_out.ui.screens.play_workout

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.view_components.FABStartStopWorkOut
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.log
import kotlinx.coroutines.launch
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
    uiState.onBaskScreen = onBaskScreen
    PlayWorkoutScreenLayout(uiState = uiState)
}

@Composable fun PlayWorkoutScreenLayout( uiState: PlayWorkoutScreenState
){
    Box()
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            content = { PlayWorkoutScreenLayoutContent(uiState)}
        )
        FABStartStopWorkOut(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            switchStartStop = uiState.switchStartStop.value,
            onClickStart = {
                uiState.switchStartStop.value = !uiState.switchStartStop.value
                onButtonStart(uiState)
            },
            onClickStop = {
                uiState.switchStartStop.value = !uiState.switchStartStop.value
                uiState.stateWorkout.clear()
                onButtonStop(uiState)
            },
            onClickPause = { onButtonPause(uiState) },
        )
    }
}
@Composable fun PlayWorkoutScreenLayoutContent( uiState: PlayWorkoutScreenState
){
    ListState(uiState)
}

@SuppressLint("MutableCollectionMutableState")
@Composable fun ListState(uiState: PlayWorkoutScreenState)
{
    val states =  uiState.stateWorkout
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxWidth().animateContentSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ){
        items(items = states ){ item->
            Row (modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)){
                TextApp(text = getDuration( startTime = uiState.startTime, time = item.time!!),
                    modifier = Modifier.width(70.dp),
                    style = interLight12)
                Spacer(modifier = Modifier.width(12.dp))
                TextApp(text = item.state.toString(), style = interLight12)
            }
        }
    }
    LaunchedEffect(listState.canScrollForward){
        if (listState.canScrollForward) {
            coroutineScope.launch {
                log(true, "listState.animateScrollToItem ${if (states.size == 0 ) 0 else states.size-1}")
                listState.animateScrollToItem(index = if (states.size == 0 ) 0 else states.size-1)
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
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
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.domain.toPositive
import com.example.count_out.entity.Set
import com.example.count_out.entity.StateRunning
import com.example.count_out.entity.no_use.MessageWorkOut
import com.example.count_out.ui.theme.interBold48
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.view_components.FABCorrectInterval
import com.example.count_out.ui.view_components.FABStartStopWorkOut
import com.example.count_out.ui.view_components.TextApp
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun PlayWorkoutScreen(trainingId: Long ){
    val viewModel: PlayWorkoutViewModel = hiltViewModel()
    LaunchedEffect( key1 = true, block = { viewModel.getTraining(trainingId) })
    PlayWorkoutScreenCreateView( viewModel = viewModel)
}
@Composable
fun PlayWorkoutScreenCreateView( viewModel: PlayWorkoutViewModel,
){
    val uiState by viewModel.playWorkoutScreenState.collectAsState()
    PlayWorkoutScreenLayout(uiState = uiState)
}
@Composable fun PlayWorkoutScreenLayout( uiState: PlayWorkoutScreenState
){
    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            content = { PlayWorkoutScreenLayoutContent(uiState)}
        )
        val stateRunning: StateRunning
        val playerSet: Set
        try {
            stateRunning = uiState.switchState.value
            playerSet = uiState.playerSet.value
        } catch (_:IllegalStateException){ return}

        FABStartStopWorkOut(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            switchState= stateRunning,
            onClickStart = { onButtonStart(uiState) },
            onClickStop = { onButtonStop(uiState) },
            onClickPause = { onButtonPause(uiState) },
        )
        uiState.findSet(playerSet.idSet)?.let { playSet->
            FABCorrectInterval(
                currentValue = playSet.intervalReps,
                downInterval = {
                    uiState.training?.let {
                        uiState.updateSet( it.idTraining,
                            (playSet as SetDB).copy(
                                intervalReps = (playSet.intervalReps - 0.1).toPositive()))
                    }
                },
                upInterval = {
                    uiState.training?.let {
                        uiState.updateSet(it.idTraining,
                            (playSet as SetDB).copy( intervalReps = playSet.intervalReps + 0.1 ))
                    }
                }
            )
        }
    }
}
@Composable fun PlayWorkoutScreenLayoutContent( uiState: PlayWorkoutScreenState
){
    CountTime(uiState)
    ListState(uiState)
}

@Composable fun CountTime(uiState: PlayWorkoutScreenState){

    val tickTime = uiState.tickTime
    Row (modifier = Modifier.fillMaxWidth() ,horizontalArrangement = Arrangement.Center) {
        TextApp(text = tickTime.hour, style = interBold48)
        TextApp(text = ":", style = interBold48)
        TextApp(text = tickTime.min, style = interBold48)
        TextApp(text = ":", style = interBold48)
        TextApp(text = tickTime.sec, style = interBold48)
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable fun ListState(uiState: PlayWorkoutScreenState)
{
    val coroutineScope = rememberCoroutineScope()
    val lazyState = rememberLazyListState()

    LazyColumn(
        state = lazyState,
        modifier = Modifier.fillMaxWidth().animateContentSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ){
        items(items = uiState.statesWorkout.value ){ item->
            Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)) {
                TextApp(
                    text = getDuration( time = item.time!!),
                    modifier = Modifier.width(70.dp),
                    style = interLight12
                )
                Spacer(modifier = Modifier.width(12.dp))
                TextApp(text = item.message, style = interLight12)
            }
        }
    }
    LaunchedEffect(lazyState.canScrollForward){
        if (lazyState.canScrollForward) {
            coroutineScope.launch {
                lazyState.animateScrollToItem(index = listSize( uiState.statesWorkout.value ))
            }
        }
    }
}
fun listSize(list: List<MessageWorkOut>): Int = if (list.isEmpty()) 0 else list.size - 1

fun getDuration(time: Long): String{
    val duration: Long = time
    val hour = duration / 1000 / 60 / 60
    val minutes = duration / 1000 / 60
    val seconds = duration / 1000 % 60
    return String.format("%02d:%02d:%02d",hour, minutes, seconds)
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
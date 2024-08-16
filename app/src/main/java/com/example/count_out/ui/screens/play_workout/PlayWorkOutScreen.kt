package com.example.count_out.ui.screens.play_workout

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.R
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.domain.Minus
import com.example.count_out.domain.Plus
import com.example.count_out.entity.ListActivityForPlayer
import com.example.count_out.entity.StateRunning
import com.example.count_out.entity.no_use.MessageWorkOut
import com.example.count_out.ui.theme.colors3
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.theme.shapes
import com.example.count_out.ui.theme.typography
import com.example.count_out.ui.view_components.ButtonApp
import com.example.count_out.ui.view_components.IconSingleLarge
import com.example.count_out.ui.view_components.NameScreen
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.launch
import java.math.RoundingMode

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
    if(uiState.listActivity.isEmpty()) { uiState.listActivity = uiState.activityList() }

    uiState.training?.let {
        lg("ScreenLayout ${it.rounds[0].exercise[0].sets[0].intervalReps}  ${it.rounds[0].exercise[1].sets[0].intervalReps}")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        content = {
            NameScreen(id = R.string.runs_treining)
            ListState(uiState)
            Spacer(modifier = Modifier.weight(1f))
            Indication(uiState = uiState)}
    )
}

@SuppressLint("MutableCollectionMutableState")
@Composable fun ListState(uiState: PlayWorkoutScreenState) {
    val coroutineScope = rememberCoroutineScope()
    val lazyState = rememberLazyListState()

    LazyColumn(
        state = lazyState,
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ){
        items(items = uiState.statesWorkout ){ item->
            Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)) {
                TextApp(
                    text = "${item.tickTime.hour}:${item.tickTime.min}:${item.tickTime.sec}",
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
                lazyState.animateScrollToItem(index = listSize( uiState.statesWorkout ))
            }
        }
    }
}

fun listSize(list: List<MessageWorkOut>): Int = if (list.isEmpty()) 0 else list.size - 1

@Composable fun Indication(uiState: PlayWorkoutScreenState){
//    lg(" training ${uiState.training}")
    Column( modifier = Modifier
        .fillMaxWidth()
        .background(color = colors3.surfaceContainer, shape = shapes.extraSmall),) {
        CurrentTimePulse(uiState)
        ListExercise(uiState)
        Spacer(modifier = Modifier.height(24.dp))
        ButtonFasterSlower(uiState)
        ButtonsStartStopWorkOut(
            switchState= uiState.switchState,
            onClickStart = { uiState.training?.let { uiState.startWorkOutService(it) } },
            onClickStop = { uiState.stopWorkOutService() },
            onClickPause = { uiState.pauseWorkOutService() },
        )
    }
}

@Composable fun ButtonFasterSlower(uiState: PlayWorkoutScreenState){

    var downInterval = {}
    var upInterval = {}
    uiState.training?.let { training ->
        uiState.playerSet?.let { set ->
            downInterval = { uiState.updateSet(
                training.idTraining, (set as SetDB).copy(intervalReps = set.intervalReps.Minus())) }
            upInterval = { uiState.updateSet(
                training.idTraining, (set as SetDB).copy(intervalReps = set.intervalReps.Plus())) }
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .fillMaxWidth()){
        ButtonApp( modifier = Modifier.width(150.dp),
            text = stringResource(id = R.string.slower),
            enabled = uiState.playerSet != null,
            onClick = { upInterval() })
        Spacer(modifier = Modifier.weight(1f))
        uiState.playerSet?.intervalReps?.toBigDecimal()?.setScale(1, RoundingMode.UP)?.let {
            TextApp(text = it.toString(), style = typography.titleLarge)}
        Spacer(modifier = Modifier.weight(1f))
        ButtonApp( modifier = Modifier.width(150.dp),
            text = stringResource(id = R.string.faster),
            enabled = uiState.playerSet != null,
            onClick = { downInterval() })
    }
}

@Composable fun CurrentTimePulse(uiState: PlayWorkoutScreenState) {
    Row( modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp, horizontal = 18.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically)
    {
        CountTime(uiState = uiState)
        Spacer(modifier = Modifier.weight(1f))
        HearthRate(uiState)
    }
}
@Composable fun CountTime(uiState: PlayWorkoutScreenState){
    val style = typography.displayLarge
    TextApp(text = uiState.tickTime.hour, style = style)
    TextApp(text = ":", style = style)
    TextApp(text = uiState.tickTime.min, style = style)
    TextApp(text = ":", style = style)
    TextApp(text = uiState.tickTime.sec, style = style)
}
@Composable fun HearthRate(uiState: PlayWorkoutScreenState) {
    TextApp(text = uiState.heartRate.toString(), style = typography.displayLarge)
}

@Composable fun ListExercise(uiState: PlayWorkoutScreenState) {
//    lg("ListExercise ${uiState.playerSet}")
    var roundId = 0
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .fillMaxWidth()
            .height(150.dp)
            .animateContentSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        itemsIndexed(items = uiState.listActivity){ index, item ->
            val roundName = if ( roundId != item.roundNameId) {
                roundId = item.roundNameId
                stringResource(id = item.roundNameId)
            } else ""
            ListExerciseContent(item = item, roundName, index = index)
        }
    }
}

@Composable
fun ListExerciseContent(
    item: ListActivityForPlayer,
    roundName: String,
    index: Int,
) {
    Column(modifier = Modifier.padding(horizontal = 12.dp)) {
        if (roundName.isNotEmpty()){
            Spacer(modifier = Modifier.height(12.dp))
            TextApp(text = roundName, style = typography.bodyLarge, fontWeight = FontWeight.Bold)
        }
        Row {
            TextApp(
                text = item.activityName + " ",
                style = typography.bodyLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 12.dp).weight(1f))

            val setDescription =
                if (index == 0){ "${stringResource(id = R.string.set).lowercase()} " +
                    "${item.currentSet + 1} ${stringResource(id = R.string.from)} ${item.countSet}"
                } else { "${stringResource(id = R.string.setFrom).lowercase()}: ${item.countSet}" }

            Text(text = setDescription, style = typography.bodyLarge)
        }
    }
}

@Composable fun ButtonsStartStopWorkOut(
    onClickStart: () -> Unit,
    onClickStop: () -> Unit,
    onClickPause: () -> Unit,
    switchState: StateRunning,
){
    Row( horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        when (switchState){
            StateRunning.Started -> StartedService(onClickStop, onClickPause)
            StateRunning.Stopped -> StoppedService(onClickStart)
            StateRunning.Created -> StoppedService(onClickStart)
            StateRunning.Paused -> PauseService(onClickStart, onClickStop)
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable fun StartedService(onClickStop: () -> Unit, onClickPause: () -> Unit,){
    IconSingleLarge(Icons.Filled.Pause, onClickPause)
    Spacer(modifier = Modifier.width(12.dp))
    IconSingleLarge(Icons.Filled.Stop, onClickStop )
}
@Composable fun StoppedService(onClickStart: () -> Unit,){
    IconSingleLarge(Icons.Filled.PlayArrow, onClickStart )
}

@Composable fun PauseService(onClickStart: () -> Unit, onClickStop: () -> Unit){
    IconSingleLarge(Icons.Filled.PlayArrow, onClickStart )
    Spacer(modifier = Modifier.width(12.dp))
    IconSingleLarge(Icons.Filled.Stop, onClickStop )
}

@Preview
@Composable fun PreviewPlayWorkoutScreen(){
    PlayWorkoutScreen(0)
}

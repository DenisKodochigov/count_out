package com.example.count_out.ui.screens.executor

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
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
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.ListActivityForExecute
import com.example.count_out.entity.RunningState
import com.example.count_out.ui.theme.alumBodySmall
import com.example.count_out.ui.theme.colors3
import com.example.count_out.ui.theme.shapes
import com.example.count_out.ui.theme.typography
import com.example.count_out.ui.view_components.ButtonApp
import com.example.count_out.ui.view_components.IconSingleLarge
import com.example.count_out.ui.view_components.NameScreen
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.launch
import java.math.RoundingMode

@Composable fun ExecuteWorkoutScreen(trainingId: Long ){
    val viewModel: ExecuteWorkViewModel = hiltViewModel()
    LaunchedEffect( key1 = true, block = { viewModel.getTraining(trainingId) })
    ExecuteWorkoutScreenCreateView( viewModel = viewModel)
}
@Composable fun ExecuteWorkoutScreenCreateView(viewModel: ExecuteWorkViewModel){
    val uiState by viewModel.executeWorkoutScreenState.collectAsState()
    ExecuteWorkoutScreenLayout(uiState = uiState)
}
@Composable fun ExecuteWorkoutScreenLayout( uiState: ExecuteWorkoutScreenState){
    if(uiState.listActivity.isEmpty()) {
        uiState.listActivity = uiState.activityList() }
    Column(
        modifier = Modifier.fillMaxSize(),
        content = {
            NameScreen(id = R.string.runs_treining)
            ListState(uiState, Modifier.weight(1f))
            Indication(uiState = uiState)}
    )
}
@Composable fun ListState(uiState: ExecuteWorkoutScreenState, modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val lazyState = rememberLazyListState()
    LazyColumn(
        state = lazyState,
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ){
        items(items = uiState.messageWorkout ){ item->
            Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)) {
                TextApp(
                    text = "${item.tickTime.hour}:${item.tickTime.min}:${item.tickTime.sec}",
                    modifier = Modifier.width(70.dp),
                    style = typography.bodySmall
                )
                Spacer(modifier = Modifier.width(12.dp))
                TextApp(text = item.message, style = typography.bodySmall)
            }
        }
    }
    LaunchedEffect(lazyState.canScrollForward){
        if (lazyState.canScrollForward) {
            coroutineScope.launch {
                lazyState.animateScrollToItem(index =
                if (uiState.messageWorkout.isEmpty()) 0 else uiState.messageWorkout.size - 1)
            }
        }
    }
}

@Composable fun Indication(uiState: ExecuteWorkoutScreenState){
    Column( modifier = Modifier
        .fillMaxWidth()
        .background(color = colors3.surfaceContainer, shape = shapes.extraSmall),) {
        CurrentTimePulse(uiState)
        ListExercise(uiState)
        Spacer(modifier = Modifier.height(24.dp))
        ButtonFasterSlower(uiState)
        ButtonsStartStopWorkOut(
            switchState= uiState.stateWorkOutService,
            onClickStart = { uiState.training?.let {
                lg("#################### ExecuteWorkoutScreen Start Service ##########################")
                uiState.startWorkOutService(it) } },
            onClickStop = {
                lg("#################### ExecuteWorkoutScreen StoppedService ##########################")
                uiState.stopWorkOutService() },
            onClickPause = { uiState.pauseWorkOutService() },
        )
    }
}
@Composable fun CurrentTimePulse(uiState: ExecuteWorkoutScreenState) {
    Row( modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp, horizontal = 18.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top)
    {
        CountTime(uiState = uiState)
        Spacer(modifier = Modifier.weight(1f))
        HearthRate(uiState)
    }
}
@Composable fun CountTime(uiState: ExecuteWorkoutScreenState){
    val style = typography.displayLarge
    TextApp(text = uiState.tickTime.hour, style = style)
    TextApp(text = ":", style = style)
    TextApp(text = uiState.tickTime.min, style = style)
    TextApp(text = ":", style = style)
    TextApp(text = uiState.tickTime.sec, style = style)
}
@Composable fun HearthRate(uiState: ExecuteWorkoutScreenState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextApp(text = uiState.heartRate.toString(), style = typography.displayLarge)
        TextApp(text = stringResource(uiState.connectingState.strId), style = alumBodySmall)
    }
}

@Composable fun ListExercise(uiState: ExecuteWorkoutScreenState) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .fillMaxWidth()
            .height(150.dp)
            .animateContentSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) { items(items = uiState.listActivity){ item -> ListExerciseContent(item = item) } }
}
@Composable fun ListExerciseContent(item: ListActivityForExecute) {
    Column(modifier = Modifier.padding(horizontal = 12.dp)) {
        val setDescription = when(item.typeDescription){
            true -> "${stringResource(R.string.setFrom).lowercase()}: ${item.countSet}"
            false -> "${stringResource(R.string.set).lowercase()} ${item.currentIndSet + 1} " +
                    "${stringResource(R.string.from)} ${item.countSet}"
            null -> ""
        }
        if (item.roundNameId != 0 && setDescription == ""){
            Spacer(modifier = Modifier.height(12.dp))
            TextApp(text = stringResource(id = item.roundNameId),
                style = typography.bodyLarge, fontWeight = FontWeight.Bold)
        } else {
            Row {
                TextApp(
                    text = item.activityName + " ",
                    style = typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f))
                TextApp(text = setDescription, style = typography.bodyLarge)
            }
        }
    }
}

@Composable fun ButtonFasterSlower(uiState: ExecuteWorkoutScreenState){
    var downInterval = {}
    var upInterval = {}
    uiState.training?.let { training ->
        uiState.speakingSet?.let { set ->
            downInterval = { uiState.updateSet(
                training.idTraining, (set as SetDB).copy(intervalReps = set.intervalReps.Minus())) }
            upInterval = { uiState.updateSet(
                training.idTraining, (set as SetDB).copy(intervalReps = set.intervalReps.Plus())) }
        }
    }
    val enabledButton = uiState.speakingSet != null &&
            ( uiState.speakingSet.goal == GoalSet.COUNT || uiState.speakingSet.goal == GoalSet.COUNT_GROUP)
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .fillMaxWidth()){
        ButtonApp( modifier = Modifier.width(140.dp),
            text = stringResource(id = R.string.slower),
            enabled = enabledButton,
            onClick = { upInterval() })
        Spacer(modifier = Modifier.weight(1f))
        if (enabledButton){
            uiState.speakingSet?.intervalReps?.toBigDecimal()?.setScale(1, RoundingMode.UP)?.let {
                TextApp(text = it.toString(), style = typography.titleLarge)}
        }
        Spacer(modifier = Modifier.weight(1f))
        ButtonApp( modifier = Modifier.width(150.dp),
            text = stringResource(id = R.string.faster),
            enabled = enabledButton,
            onClick = { downInterval() })
    }
}

@Composable fun ButtonsStartStopWorkOut(
    onClickStart:()->Unit, onClickStop:()->Unit, onClickPause:()->Unit, switchState: RunningState){
    Row( horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        when (switchState){
            RunningState.Started -> ButtonStartedService(onClickStop, onClickPause)
            RunningState.Stopped -> ButtonStoppedService(onClickStart)
            RunningState.Paused -> ButtonPauseService(onClickStart, onClickStop)
            else->{}
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}
@Composable fun ButtonStartedService(onClickStop: () -> Unit, onClickPause: () -> Unit){
    IconSingleLarge(Icons.Filled.Pause, onClickPause)
    Spacer(modifier = Modifier.width(12.dp))
    IconSingleLarge(Icons.Filled.Stop, onClickStop )
}
@Composable fun ButtonStoppedService(onClickStart: () -> Unit){
    IconSingleLarge(Icons.Filled.PlayArrow, onClickStart )
}
@Composable fun ButtonPauseService(onClickStart: () -> Unit, onClickStop: () -> Unit){
    IconSingleLarge(Icons.Filled.PlayArrow, onClickStart )
    Spacer(modifier = Modifier.width(12.dp))
    IconSingleLarge(Icons.Filled.Stop, onClickStop )
}

@Preview
@Composable fun PreviewExecuteWorkoutScreen(){
    ExecuteWorkoutScreen(0)
}

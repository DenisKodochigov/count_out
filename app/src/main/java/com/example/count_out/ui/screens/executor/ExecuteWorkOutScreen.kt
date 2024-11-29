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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.outlined.HeartBroken
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.R
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.domain.minus
import com.example.count_out.domain.plus
import com.example.count_out.entity.ConnectState
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.ui.ExecuteSetInfo
import com.example.count_out.ui.bottomsheet.BottomSheetSaveTraining
import com.example.count_out.ui.theme.alumBodySmall
import com.example.count_out.ui.theme.mTypography
import com.example.count_out.ui.theme.shapes
import com.example.count_out.ui.view_components.ButtonApp
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.custom_view.Frame
import com.example.count_out.ui.view_components.custom_view.IconQ
import com.example.count_out.ui.view_components.icons.IconSingleLarge
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.launch
import java.math.RoundingMode

@Composable fun ExecuteWorkoutScreen(trainingId: Long ){
    val viewModel: ExecuteWorkViewModel = hiltViewModel()
    viewModel.getTraining(trainingId)
    ExecuteWorkoutScreenCreateView( viewModel = viewModel)
}
@Composable fun ExecuteWorkoutScreenCreateView(viewModel: ExecuteWorkViewModel){
    val uiState by viewModel.executeWorkoutScreenState.collectAsState()
    ExecuteWorkoutScreenLayout(uiState = uiState)
}
@Composable fun ExecuteWorkoutScreenLayout( uiState: ExecuteWorkoutScreenState){
    if(uiState.listActivity.isEmpty()) { uiState.listActivity = uiState.activityList()}
    if (uiState.showBottomSheetSaveTraining.value) BottomSheetSaveTraining(uiState)
    Column(
        modifier = Modifier.fillMaxSize(),
        content = {
            SensorInfo(uiState)
            ListState(uiState, Modifier.weight(1f))
//            Indication(uiState = uiState)
            ExerciseInfo(uiState)
        }
    )
}

@Composable
fun ExerciseInfo(uiState: ExecuteWorkoutScreenState) {

//    if (uiState.listActivity.isEmpty()) return
    lg("ExerciseInfo ${uiState.executeSetInfo}")
    Frame(){
        Column{
            TextApp(text = uiState.executeSetInfo?.activityName ?: "", style = mTypography.titleLarge)
            when(uiState.speakingSet?.goal ?: GoalSet.COUNT){
                GoalSet.COUNT -> LayoutCount(uiState)
                GoalSet.DISTANCE -> LayoutDistance(uiState)
                GoalSet.DURATION -> LayoutDuration(uiState)
                GoalSet.COUNT_GROUP -> {}
            }

        }
    }
}

@Composable
fun LayoutCount(uiState: ExecuteWorkoutScreenState) {
    val widthValue = 100.dp

    RowSetsInterval(uiState, widthValue)
//    RowInfoExercise(R.string.sets, "${uiState.executeSetInfo?.currentIndexSet ?: ""}/${uiState.executeSetInfo?.countSet ?: ""}", widthValue)
//    RowInfoExercise(R.string.count, "", widthValue)
//    RowInfoExerciseInterval(uiState, R.string.interval, widthValue)
//    RowInfoExercise(R.string.time_to_rest, "", widthValue)
}

@Composable fun LayoutDistance(uiState: ExecuteWorkoutScreenState) {

}
@Composable fun LayoutDuration(uiState: ExecuteWorkoutScreenState) {

}


@Composable fun NextExercise(uiState: ExecuteWorkoutScreenState){
    TextApp(style = mTypography.headlineMedium,
        text = "${R.string.next_exercise}: ${uiState.executeSetInfo?.nextActivityName ?: ""}")
    TextApp(style = mTypography.headlineMedium,
        text = "${R.string.next_exercise}: ${uiState.executeSetInfo?.nextActivityName ?: ""}")
}

@Composable fun RowSetsInterval(uiState: ExecuteWorkoutScreenState, widthValue: Dp){
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp))
    {
        Row(modifier = Modifier.weight(1f)){
            TextApp(style = mTypography.bodyLarge, modifier = Modifier,
                textAlign = TextAlign.End, text = stringResource(R.string.sets))
            TextApp(style = mTypography.bodyLarge, modifier = Modifier.width(widthValue),
                text = "${uiState.executeSetInfo?.currentIndexSet ?: ""}/${uiState.executeSetInfo?.countSet ?: ""}")
        }
        Row(modifier = Modifier.weight(1f)){
            TextApp(style = mTypography.bodyLarge, modifier = Modifier,
                textAlign = TextAlign.End, text = stringResource(R.string.interval))
            ButtonFasterSlower1(uiState = uiState, widthValue)
        }
    }
}

@Composable fun RowInfoExerciseInterval(uiState: ExecuteWorkoutScreenState,idDescription: Int, widthValue: Dp){
    Row (verticalAlignment = Alignment.Bottom) {
        TextApp(style = mTypography.bodyLarge, modifier = Modifier.weight(1f),
            textAlign = TextAlign.End, text = stringResource(idDescription))
        ButtonFasterSlower1(uiState = uiState, widthValue)
    }
}
@Composable fun ButtonFasterSlower1(uiState: ExecuteWorkoutScreenState, widthValue: Dp){
    var downInterval = {}
    var upInterval = {}
    uiState.training?.let { training ->
        uiState.speakingSet?.let { set ->
            downInterval = { uiState.updateSet(
                training.idTraining, (set as SetDB).copy(intervalReps = set.intervalReps.minus())) }
            upInterval = { uiState.updateSet(
                training.idTraining, (set as SetDB).copy(intervalReps = set.intervalReps.plus())) }
        }
    }
    val enabledButton = uiState.speakingSet != null &&
            ( uiState.speakingSet.goal == GoalSet.COUNT || uiState.speakingSet.goal == GoalSet.COUNT_GROUP)
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.width(widthValue).padding(horizontal = 12.dp))
    {
        IconQ.Slower( onClick = { if(enabledButton) upInterval()},
            color = if(!enabledButton) MaterialTheme.colorScheme.surfaceContainerLow
                    else MaterialTheme.colorScheme.outline)
        TextApp(style = mTypography.titleLarge, modifier = Modifier.padding(horizontal = 12.dp),
            text = (uiState.speakingSet?.intervalReps?.toBigDecimal()?.setScale(1, RoundingMode.UP) ?: "  ").toString())
        IconQ.Faster( onClick = { if(enabledButton) downInterval()},
            color = if(!enabledButton) MaterialTheme.colorScheme.surfaceContainerLow
                        else MaterialTheme.colorScheme.outline)
    }
}



@Composable
fun SensorInfo(uiState: ExecuteWorkoutScreenState) {
    val style = mTypography.headlineMedium
    val sizeIcon = 32.dp
    Row( modifier = Modifier
        .fillMaxWidth()
        .padding(top = 4.dp, bottom = 4.dp, start = 12.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically)
    {   //Time
        TextApp(text = "${uiState.tickTime.hour}:${uiState.tickTime.min}:${uiState.tickTime.sec}", style = style)
        Spacer(modifier = Modifier.weight(1f))
        //Location
        Icon(contentDescription = null, modifier = Modifier.size(sizeIcon),
            imageVector = if(uiState.coordinate == null) Icons.Outlined.LocationOn
                            else Icons.Filled.LocationOn,)
        Spacer(modifier = Modifier.weight(1f))
        //HearthRate
        Icon( modifier = Modifier.size(sizeIcon), contentDescription = null,
            imageVector = if(uiState.connectingState != ConnectState.CONNECTED) Icons.Outlined.HeartBroken
                             else Icons.Filled.Favorite)
        TextApp(style = style, modifier = Modifier.padding(start=12.dp),
            text = if(uiState.connectingState != ConnectState.CONNECTED) "---"
                            else uiState.heartRate.toString() )
    }
    HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.surfaceContainerLow)
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
                    style = mTypography.bodySmall
                )
                Spacer(modifier = Modifier.width(12.dp))
                TextApp(text = item.message, style = mTypography.bodySmall)
            }
        }
    }
    LaunchedEffect(lazyState.canScrollForward){
        if (lazyState.canScrollForward) {
            coroutineScope.launch {
                lazyState.animateScrollToItem( index =
                if (uiState.messageWorkout.isEmpty()) 0 else uiState.messageWorkout.size - 1)
            }
        }
    }
}

@Composable fun Indication(uiState: ExecuteWorkoutScreenState){
    Column( modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.surfaceContainer, shape = shapes.extraSmall),) {
        CurrentTimePulse(uiState)
        ListExercise(uiState)
        Spacer(modifier = Modifier.height(24.dp))
        ButtonFasterSlower(uiState)
        ButtonsStartStopWorkOut(
            switchState= uiState.stateWorkOutService ?: RunningState.Stopped,
            onClickStart = { uiState.training?.let { uiState.startWorkOutService(it) } },
            onClickStop = {
                uiState.stopWorkOutService()
                uiState.showBottomSheetSaveTraining.value = true
            },
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
@Composable fun CountTime(uiState: ExecuteWorkoutScreenState, style: TextStyle = mTypography.displayLarge){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            TextApp(text = uiState.tickTime.hour, style = style)
            TextApp(text = ":", style = style)
            TextApp(text = uiState.tickTime.min, style = style)
            TextApp(text = ":", style = style)
            TextApp(text = uiState.tickTime.sec, style = style)
        }
        TextApp(text = "longitude: ${(uiState.coordinate?.longitude ?: "00")}" +
                "   latitude: ${uiState.coordinate?.latitude ?: "00"}", style = alumBodySmall)
    }
}
@Composable fun HearthRate(uiState: ExecuteWorkoutScreenState, style: TextStyle = mTypography.displayLarge) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextApp(text = uiState.heartRate.toString(), style = style)
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
@Composable fun ListExerciseContent(item: ExecuteSetInfo) {
    Column(modifier = Modifier.padding(horizontal = 12.dp)) {
        val setDescription = when(item.typeDescription){
            true -> "${stringResource(R.string.setFrom).lowercase()}: ${item.countSet}"
            false -> "${stringResource(R.string.set).lowercase()} ${item.currentIndexSet + 1} " +
                    "${stringResource(R.string.from)} ${item.countSet}"
            null -> ""
        }
//        if (item.roundNameId != 0 && setDescription == ""){
//            Spacer(modifier = Modifier.height(12.dp))
//            TextApp(text = stringResource(id = item.roundNameId),
//                style = mTypography.bodyLarge, fontWeight = FontWeight.Bold)
//        } else {
//            Row {
//                TextApp(
//                    text = item.activityName + " ",
//                    style = mTypography.bodyLarge,
//                    textAlign = TextAlign.Start,
//                    modifier = Modifier
//                        .padding(start = 12.dp)
//                        .weight(1f))
//                TextApp(text = setDescription, style = mTypography.bodyLarge)
//            }
//        }
    }
}

@Composable fun ButtonFasterSlower(uiState: ExecuteWorkoutScreenState){
    var downInterval = {}
    var upInterval = {}
    uiState.training?.let { training ->
        uiState.speakingSet?.let { set ->
            downInterval = { uiState.updateSet(
                training.idTraining, (set as SetDB).copy(intervalReps = set.intervalReps.minus())) }
            upInterval = { uiState.updateSet(
                training.idTraining, (set as SetDB).copy(intervalReps = set.intervalReps.plus())) }
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
            uiState.speakingSet.intervalReps.toBigDecimal().setScale(1, RoundingMode.UP)?.let {
                TextApp(text = it.toString(), style = mTypography.titleLarge)}
        }
        Spacer(modifier = Modifier.weight(1f))
        ButtonApp( modifier = Modifier.width(150.dp),
            text = stringResource(id = R.string.faster),
            enabled = enabledButton,
            onClick = { downInterval() })
    }
}
@Composable fun ButtonsStartStopWorkOut(
    onClickStart:()->Unit, onClickStop: ()->Unit, onClickPause:()->Unit, switchState: RunningState){
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
    IconSingleLarge(Icons.Filled.Stop,  onClickStop )
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

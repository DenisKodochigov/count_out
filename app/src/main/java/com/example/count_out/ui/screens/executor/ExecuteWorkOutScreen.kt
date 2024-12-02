package com.example.count_out.ui.screens.executor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.HeartBroken
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import com.example.count_out.ui.bottomsheet.BottomSheetSaveTraining
import com.example.count_out.ui.theme.mTypography
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.custom_view.Frame
import com.example.count_out.ui.view_components.custom_view.IconQ
import com.example.count_out.ui.view_components.lg
import java.math.RoundingMode

@Composable fun ExecuteWorkoutScreen(trainingId: Long ){
    lg("ExecuteWorkoutScreen $trainingId")
    val viewModel: ExecuteWorkViewModel = hiltViewModel()
    viewModel.getTraining(trainingId)
    ExecuteWorkoutScreenCreateView( viewModel = viewModel)
}
@Composable fun ExecuteWorkoutScreenCreateView(viewModel: ExecuteWorkViewModel){
    val uiState by viewModel.executeWorkoutScreenState.collectAsState()
    ExecuteWorkoutScreenLayout(uiState = uiState)
}
@Composable fun ExecuteWorkoutScreenLayout( uiState: ExecuteWorkoutScreenState){
    if (uiState.showBottomSheetSaveTraining.value) BottomSheetSaveTraining(uiState)
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp),
        content = {
            SensorInfo(uiState)
            AdditionalInformation(uiState, modifier = Modifier.weight(1f))
            ExerciseInfo(uiState)
            DownPlace(uiState)
        }
    )
}
@Composable fun SensorInfo(uiState: ExecuteWorkoutScreenState) {
    val style = mTypography.headlineMedium
    val sizeIcon = 32.dp
    Row( modifier = Modifier
        .fillMaxWidth()
        .padding(top = 4.dp, bottom = 4.dp, start = 12.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically)
    {   //Time
        TextApp(text = "${uiState.flowTime.hour}:${uiState.flowTime.min}:${uiState.flowTime.sec}", style = style)
        Spacer(modifier = Modifier.weight(1f))
        //Location
        Icon(contentDescription = null, modifier = Modifier.size(sizeIcon),
            imageVector = if(uiState.coordinate == null) Icons.Outlined.LocationOn
            else Icons.Filled.LocationOn,)
        Spacer(modifier = Modifier.weight(1f))
        //HearthRate
        Icon( modifier = Modifier.size(sizeIcon), contentDescription = null,
            imageVector = if(uiState.bleConnectState != ConnectState.CONNECTED) Icons.Outlined.HeartBroken
            else Icons.Filled.Favorite)
        TextApp(style = style, modifier = Modifier.padding(start=12.dp),
            text = if(uiState.bleConnectState != ConnectState.CONNECTED) "---"
            else uiState.heartRate.toString() )
    }
    HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.surfaceContainerLow)
}
@Composable fun AdditionalInformation(uiState: ExecuteWorkoutScreenState, modifier: Modifier = Modifier){
    Column (modifier = modifier.fillMaxWidth()) {
        TextApp(text = " ", style = mTypography.bodyMedium)
    }
}
@Composable fun ExerciseInfo(uiState: ExecuteWorkoutScreenState) {
    Frame{
        Column (modifier = Modifier.padding(horizontal = 6.dp)){
            TextApp(text = uiState.executeInfoExercise?.activity?.name ?: "",
                modifier = Modifier.padding(bottom = 12.dp),
                style = mTypography.titleLarge)
            when(uiState.executeInfoSet?.currentSet?.goal ?: GoalSet.COUNT){
                GoalSet.COUNT -> LayoutCount(uiState)
                GoalSet.DISTANCE -> LayoutDistance(uiState)
                GoalSet.DURATION -> LayoutDuration(uiState)
                GoalSet.COUNT_GROUP -> {}
            }

        }
    }
}
@Composable fun DownPlace(uiState: ExecuteWorkoutScreenState) {
    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 0.dp, top = 12.dp),
        horizontalArrangement = Arrangement.Center) {
        ButtonStartedService(uiState)
        Spacer(modifier = Modifier.width(32.dp))
        ButtonPauseService(uiState)
        Spacer(modifier = Modifier.width(32.dp))
        ButtonStoppedService(uiState)
    }
}
@Composable fun ButtonStartedService(uiState: ExecuteWorkoutScreenState){
    IconQ.Play(
        onClick = { if (uiState.stateWorkOutService != RunningState.Started)
                        uiState.training?.let { uiState.startWorkOutService(it) }},
        color = if (uiState.stateWorkOutService == RunningState.Started) MaterialTheme.colorScheme.surfaceContainerLow
                 else MaterialTheme.colorScheme.outline)
}
@Composable fun ButtonStoppedService(uiState: ExecuteWorkoutScreenState){
    IconQ.Stop(
        onClick = { if (uiState.stateWorkOutService != RunningState.Stopped){
            uiState.stopWorkOutService()
            uiState.showBottomSheetSaveTraining.value = true
        } },
        color = if (uiState.stateWorkOutService == RunningState.Stopped) MaterialTheme.colorScheme.surfaceContainerLow
                else MaterialTheme.colorScheme.outline)
}
@Composable fun ButtonPauseService(uiState: ExecuteWorkoutScreenState){
    IconQ.Pause(
        onClick = { if (uiState.stateWorkOutService != RunningState.Paused) uiState.pauseWorkOutService()},
        color = if (uiState.stateWorkOutService == RunningState.Paused) MaterialTheme.colorScheme.surfaceContainerLow
                    else MaterialTheme.colorScheme.outline)
}

@Composable fun LayoutCount(uiState: ExecuteWorkoutScreenState) {
    val widthValue = 60.dp
    RowSetsInterval(uiState, widthValue)
    RowRepsRest(uiState, widthValue)
    NextExercise(uiState)
//    RowInfoExercise(R.string.sets, "${uiState.executeSetInfo?.currentIndexSet ?: ""}/${uiState.executeSetInfo?.countSet ?: ""}", widthValue)
//    RowInfoExercise(R.string.count, "", widthValue)
//    RowInfoExerciseInterval(uiState, R.string.interval, widthValue)
//    RowInfoExercise(R.string.time_to_rest, "", widthValue)
}

@Composable fun LayoutDistance(uiState: ExecuteWorkoutScreenState) {}
@Composable fun LayoutDuration(uiState: ExecuteWorkoutScreenState) {}

@Composable fun RowSetsInterval(uiState: ExecuteWorkoutScreenState, widthValue: Dp){
    Row(verticalAlignment = Alignment.Bottom,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp))
    {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.Bottom){
            TextApp(style = mTypography.bodyLarge, modifier = Modifier.width(widthValue),
                textAlign = TextAlign.Start, text = stringResource(R.string.sets) + ":")
            TextApp(style = mTypography.titleLarge, modifier = Modifier,
                text = "${(uiState.executeInfoSet?.currentIndexSet ?: 0) + 1}/${uiState.executeInfoSet?.quantitySet ?: ""}")
        }
        Row(modifier = Modifier.weight(2f), verticalAlignment = Alignment.Bottom,){
            TextApp(style = mTypography.bodyLarge, modifier = Modifier.padding(end = 12.dp),
                textAlign = TextAlign.Start, text = stringResource(R.string.interval))
            ButtonFasterSlower1(uiState = uiState)
        }
    }
}
@Composable fun ButtonFasterSlower1(uiState: ExecuteWorkoutScreenState){
    var downInterval = {}
    var upInterval = {}
    uiState.training?.let { training ->
        uiState.executeInfoSet?.currentSet?.let { set ->
            downInterval = { uiState.updateSet(
                training.idTraining, (set as SetDB).copy(intervalReps = set.intervalReps.minus())) }
            upInterval = { uiState.updateSet(
                training.idTraining, (set as SetDB).copy(intervalReps = set.intervalReps.plus())) }
        }
    }
    val color = if(!uiState.enableChangeInterval) MaterialTheme.colorScheme.surfaceContainerLow
                else MaterialTheme.colorScheme.outline

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 2.dp, end = 2.dp))
    {
        IconQ.Slower( onClick = { if(uiState.enableChangeInterval) upInterval()}, color = color)
        TextApp(style = mTypography.titleLarge, modifier = Modifier.padding(horizontal = 6.dp),
            text = (uiState.executeInfoSet?.currentSet?.intervalReps?.toBigDecimal()?.setScale(1, RoundingMode.UP) ?: "  ").toString())
        IconQ.Faster( onClick = { if(uiState.enableChangeInterval) downInterval()}, color = color)
    }
}

@Composable fun RowRepsRest(uiState: ExecuteWorkoutScreenState, widthValue: Dp){
    Row(verticalAlignment = Alignment.Bottom,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 12.dp))
    {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.Bottom){
            TextApp(style = mTypography.bodyLarge, modifier = Modifier.width(widthValue),
                textAlign = TextAlign.Start, text = stringResource(R.string.reps) + ":")
            TextApp(style = mTypography.titleLarge, modifier = Modifier,
                text = "${uiState.countReps}/${uiState.executeInfoSet?.currentSet?.reps ?: ""}")
        }
        Row(modifier = Modifier.weight(2f), verticalAlignment = Alignment.Bottom){
            TextApp(style = mTypography.bodyLarge, modifier = Modifier.padding(end = 98.dp),
                textAlign = TextAlign.Start, text = stringResource(R.string.rest) + ":")
            TextApp(style = mTypography.titleLarge, modifier = Modifier,
                text = "${uiState.countRest}/${uiState.executeInfoSet?.currentSet?.timeRest ?: ""}")
        }
    }
}
@Composable fun NextExercise(uiState: ExecuteWorkoutScreenState){
    TextApp(style = mTypography.bodyLarge, modifier = Modifier.padding(top = 18.dp),
        text = "${ stringResource(R.string.next_exercise)}: ${uiState.executeInfoExercise?.nextExercise?.nextActivityName ?: ""}")
    TextApp(style = mTypography.bodyLarge,modifier = Modifier.padding(start = 12.dp),
        text = "${stringResource(R.string.sets)}: ${(uiState.executeInfoSet?.quantitySet ?: 0) + 1}" +
                " (${ uiState.executeInfoExercise?.nextExercise?.nextExerciseSummarizeSet?.let { viewNextSets(it) }}) ")
}
@Composable fun viewNextSets(list: List<Pair<String, Int>>): String{
    return list.map{"${it.first}${stringResource(it.second).lowercase()}"}.joinToString(separator = "-")
}
//
//@Composable fun RowInfoExerciseInterval(uiState: ExecuteWorkoutScreenState,idDescription: Int, widthValue: Dp){
//    Row (verticalAlignment = Alignment.Bottom) {
//        TextApp(style = mTypography.bodyLarge, modifier = Modifier.weight(1f),
//            textAlign = TextAlign.End, text = stringResource(idDescription))
//        ButtonFasterSlower1(uiState = uiState, widthValue)
//    }
//}

//@Composable fun ListState(uiState: ExecuteWorkoutScreenState, modifier: Modifier = Modifier) {
//    val coroutineScope = rememberCoroutineScope()
//    val lazyState = rememberLazyListState()
//    LazyColumn(
//        state = lazyState,
//        modifier = modifier
//            .fillMaxWidth()
//            .animateContentSize(),
//        horizontalAlignment = Alignment.Start,
//        verticalArrangement = Arrangement.Top
//    ){
//        items(items = uiState.messageWorkout ){ item->
//            Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)) {
//                TextApp(
//                    text = "${item.tickTime.hour}:${item.tickTime.min}:${item.tickTime.sec}",
//                    modifier = Modifier.width(70.dp),
//                    style = mTypography.bodySmall
//                )
//                Spacer(modifier = Modifier.width(12.dp))
//                TextApp(text = item.message, style = mTypography.bodySmall)
//            }
//        }
//    }
//    LaunchedEffect(lazyState.canScrollForward){
//        if (lazyState.canScrollForward) {
//            coroutineScope.launch {
//                lazyState.animateScrollToItem( index =
//                if (uiState.messageWorkout.isEmpty()) 0 else uiState.messageWorkout.size - 1)
//            }
//        }
//    }
//}

//@Composable fun Indication(uiState: ExecuteWorkoutScreenState){
//    Column( modifier = Modifier
//        .fillMaxWidth()
//        .background(color = MaterialTheme.colorScheme.surfaceContainer, shape = shapes.extraSmall),) {
//        CurrentTimePulse(uiState)
////        ListExercise(uiState)
//        Spacer(modifier = Modifier.height(24.dp))
//        ButtonFasterSlower(uiState)
//        ButtonsStartStopWorkOut(
//            switchState= uiState.stateWorkOutService ?: RunningState.Stopped,
//            onClickStart = { uiState.training?.let { uiState.startWorkOutService(it) } },
//            onClickStop = {
//                uiState.stopWorkOutService()
//                uiState.showBottomSheetSaveTraining.value = true
//            },
//            onClickPause = { uiState.pauseWorkOutService() },
//        )
//    }
//}
//
//@Composable fun CurrentTimePulse(uiState: ExecuteWorkoutScreenState) {
//    Row( modifier = Modifier
//        .fillMaxWidth()
//        .padding(vertical = 12.dp, horizontal = 18.dp),
//        horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.Top)
//    {
//        CountTime(uiState = uiState)
//        Spacer(modifier = Modifier.weight(1f))
//        HearthRate(uiState)
//    }
//}
//@Composable fun CountTime(uiState: ExecuteWorkoutScreenState, style: TextStyle = mTypography.displayLarge){
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Row {
//            TextApp(text = uiState.flowTime.hour, style = style)
//            TextApp(text = ":", style = style)
//            TextApp(text = uiState.flowTime.min, style = style)
//            TextApp(text = ":", style = style)
//            TextApp(text = uiState.flowTime.sec, style = style)
//        }
//        TextApp(text = "longitude: ${(uiState.coordinate?.longitude ?: "00")}" +
//                "   latitude: ${uiState.coordinate?.latitude ?: "00"}", style = alumBodySmall)
//    }
//}
//@Composable fun HearthRate(uiState: ExecuteWorkoutScreenState, style: TextStyle = mTypography.displayLarge) {
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        TextApp(text = uiState.heartRate.toString(), style = style)
//        TextApp(text = stringResource(uiState.bleConnectState.strId), style = alumBodySmall)
//    }
//}

//@Composable fun ListExercise(uiState: ExecuteWorkoutScreenState) {
//    LazyColumn(
//        state = rememberLazyListState(),
//        modifier = Modifier
//            .padding(horizontal = 6.dp)
//            .fillMaxWidth()
//            .height(150.dp)
//            .animateContentSize(),
//        horizontalAlignment = Alignment.Start,
//        verticalArrangement = Arrangement.Top
//    ) { items(items = uiState.listActivity){ item -> ListExerciseContent(item = item) } }
//}
//@Composable fun ListExerciseContent(item: ExecuteSetInfo) {
//    Column(modifier = Modifier.padding(horizontal = 12.dp)) {
//        val setDescription = when(item.typeDescription){
//            true -> "${stringResource(R.string.setFrom).lowercase()}: ${item.countSet}"
//            false -> "${stringResource(R.string.set).lowercase()} ${item.currentIndexSet + 1} " +
//                    "${stringResource(R.string.from)} ${item.countSet}"
//            null -> ""
//        }
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
//    }
//}
//
//@Composable fun ButtonFasterSlower(uiState: ExecuteWorkoutScreenState){
//    var downInterval = {}
//    var upInterval = {}
//    uiState.training?.let { training ->
//        uiState.currentSet?.let { set ->
//            downInterval = { uiState.updateSet(
//                training.idTraining, (set as SetDB).copy(intervalReps = set.intervalReps.minus())) }
//            upInterval = { uiState.updateSet(
//                training.idTraining, (set as SetDB).copy(intervalReps = set.intervalReps.plus())) }
//        }
//    }
//    val enabledButton = uiState.currentSet != null &&
//            ( uiState.currentSet.goal == GoalSet.COUNT || uiState.currentSet.goal == GoalSet.COUNT_GROUP)
//    Row(verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center,
//        modifier = Modifier
//            .padding(horizontal = 24.dp, vertical = 12.dp)
//            .fillMaxWidth()){
//        ButtonApp( modifier = Modifier.width(140.dp),
//            text = stringResource(id = R.string.slower),
//            enabled = enabledButton,
//            onClick = { upInterval() })
//        Spacer(modifier = Modifier.weight(1f))
//        if (enabledButton){
//            uiState.currentSet?.intervalReps?.let {
//                TextApp(text = it.toBigDecimal().setScale(1, RoundingMode.UP).toString(),
//                    style = mTypography.titleLarge)}
//        }
//        Spacer(modifier = Modifier.weight(1f))
//        ButtonApp( modifier = Modifier.width(150.dp),
//            text = stringResource(id = R.string.faster),
//            enabled = enabledButton,
//            onClick = { downInterval() })
//    }
//}
//@Composable fun ButtonsStartStopWorkOut(
//    onClickStart:()->Unit, onClickStop: ()->Unit, onClickPause:()->Unit, switchState: RunningState){
//    Row( horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
//        when (switchState){
//            RunningState.Started -> ButtonStartedService(onClickStop, onClickPause)
//            RunningState.Stopped -> ButtonStoppedService(onClickStart)
//            RunningState.Paused -> ButtonPauseService(onClickStart, onClickStop)
//            else->{}
//        }
//    }
//    Spacer(modifier = Modifier.height(12.dp))
//}
//@Composable fun ButtonStartedService(onClickStop: () -> Unit, onClickPause: () -> Unit){
//    IconSingleLarge(Icons.Filled.Pause, onClickPause)
//    Spacer(modifier = Modifier.width(12.dp))
//    IconSingleLarge(Icons.Filled.Stop,  onClickStop )
//}
//@Composable fun ButtonStoppedService(onClickStart: () -> Unit){
//    IconSingleLarge(Icons.Filled.PlayArrow, onClickStart )
//}
//@Composable fun ButtonPauseService(onClickStart: () -> Unit, onClickStop: () -> Unit){
//    IconSingleLarge(Icons.Filled.PlayArrow, onClickStart )
//    Spacer(modifier = Modifier.width(12.dp))
//    IconSingleLarge(Icons.Filled.Stop, onClickStop )
//}

@Preview
@Composable fun PreviewExecuteWorkoutScreen(){
    ExecuteWorkoutScreen(0)
}

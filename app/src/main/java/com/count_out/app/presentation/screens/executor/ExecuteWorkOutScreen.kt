package com.count_out.app.presentation.screens.executor

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.count_out.app.presentation.models.SetImpl
import com.count_out.app.presentation.navigation.NavigateEvent
import com.count_out.app.presentation.theme.mTypography
import com.count_out.app.presentation.view_components.TextApp
import com.count_out.app.presentation.view_components.custom_view.Frame
import com.count_out.app.presentation.view_components.custom_view.IconQ
import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.enums.Units
import java.math.RoundingMode
import com.count_out.app.R
import com.count_out.app.presentation.bottom_sheet.BottomSheetSaveTraining
import com.count_out.domain.entity.NextExercise
import com.count_out.domain.entity.minus
import com.count_out.domain.entity.plus

@Composable fun ExecuteWorkoutScreen(navigateEvent: NavigateEvent, trainingId: Long ){
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
        Column (modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 12.dp)){
            val text = if (uiState.stepTraining?.exercise?.activity?.name.isNullOrEmpty()) "" else
                "${uiState.stepTraining?.exercise?.activity?.name ?: ""}:" +
                " ${uiState.stepTraining?.numberExercise ?: ""}/${uiState.stepTraining?.quantityExercise}"
            TextApp(text = text,
                modifier = Modifier.padding(bottom = 12.dp),
                style = mTypography.titleLarge)
            when(uiState.stepTraining?.currentSet?.goal ?: Goal.Count){
                Goal.Count -> LayoutCount(uiState)
                Goal.Distance -> LayoutDistance(uiState)
                Goal.Duration -> LayoutDuration(uiState)
                Goal.CountGroup -> {}
            }
            NextExercise(uiState.stepTraining?.nextExercise)
        }
    }
}
@Composable fun DownPlace(uiState: ExecuteWorkoutScreenState) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 0.dp, top = 12.dp),
        horizontalArrangement = Arrangement.Center) {
        ButtonStartedService(uiState)
        Spacer(modifier = Modifier.width(32.dp))
        ButtonPauseService(uiState)
        Spacer(modifier = Modifier.width(32.dp))
        ButtonStoppedService(uiState)
    }
}
@Composable fun ButtonStartedService(uiState: ExecuteWorkoutScreenState){
    val ifValue = (uiState.stateWorkOutService != RunningState.Started) || (uiState.stateWorkOutService == RunningState.Binding)
    IconQ.Play(
        onClick = { if (ifValue) uiState.training?.let { uiState.startWorkOutService(it) }  },
        color = with(MaterialTheme.colorScheme) { if (ifValue) outline else surfaceContainerLow})
}
@Composable fun ButtonStoppedService(uiState: ExecuteWorkoutScreenState){
    val ifValue = (uiState.stateWorkOutService == RunningState.Started) || (uiState.stateWorkOutService == RunningState.Paused)
    IconQ.Stop(
        onClick = {
            if (ifValue){
                uiState.stopWorkOutService()
                uiState.showBottomSheetSaveTraining.value = true
            }
        },
        color = with(MaterialTheme.colorScheme) { if (ifValue) outline else surfaceContainerLow })
}
@Composable fun ButtonPauseService(uiState: ExecuteWorkoutScreenState){
    val ifValue = (uiState.stateWorkOutService == RunningState.Started)
    IconQ.Pause(
        onClick = { if (ifValue){ uiState.pauseWorkOutService() } },
        color = with(MaterialTheme.colorScheme) { if (ifValue) outline else surfaceContainerLow })
}

@Composable fun LayoutCount(uiState: ExecuteWorkoutScreenState) {
    Row(modifier = Modifier.fillMaxWidth().padding(start = 8.dp)) {
        uiState.stepTraining?.currentSet?.let { set ->
            //Description
            ColumnsA(
                style1 = mTypography.bodyLarge,
                style2 = mTypography.titleLarge,
                modifier = Modifier.padding(start = 12.dp).weight(1f),
                text1 = stringResource(R.string.sets) + ":",
                text2 = stringResource(R.string.reps) + ":",
                text3 = stringResource(R.string.weight) + ":",
                text4 = stringResource(R.string.rest) + ":",
            )
            //Value
            ColumnsA(
                style1 = mTypography.titleLarge,
                style2 = mTypography.titleLarge,
                modifier = Modifier.width(50.dp),
                text1 = "${uiState.stepTraining.numberSet}",
                text2 = "${uiState.currentCount}",
                text3 = "${set.weight.value / ( if (set.weight.unit == Units.KG) 1000 else 1 ) }",
                text4 = "${uiState.currentRest}",
            )
            //Total target (unit)
            ColumnsA(
                style1 = mTypography.bodyLarge,
                style2 = mTypography.titleLarge,
                modifier = Modifier.padding(start = 12.dp).width(100.dp),
                text1 = "${uiState.stepTraining.quantitySet}",
                text2 = "${set.reps}",
                text3 = "(${stringResource(set.weight.unit.id) })",
                text4 = "${set.rest.value/( if (set.rest.unit == Units.M) 60 else 1)}" +
                        " (${ stringResource(set.rest.unit.id) })",
            )
        }
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        TextApp(text = stringResource(R.string.interval) + ":", style = mTypography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp,))
        ButtonFasterSlower(uiState)
    }
}
@Composable fun LayoutDistance(uiState: ExecuteWorkoutScreenState) {
    Row(modifier = Modifier.fillMaxWidth().padding(start = 0.dp)) {
        uiState.stepTraining?.currentSet?.let { set ->
            //Description
            ColumnsA(
                style1 = mTypography.bodyLarge,
                style2 = mTypography.titleLarge,
                modifier = Modifier.padding(start = 12.dp).weight(1f),
                text1 = stringResource(R.string.sets) + ":",
                text2 = stringResource(R.string.distance) + ":",
                text3 = stringResource(R.string.rest) + ":",
            )
            //Value
            ColumnsA(
                style1 = mTypography.titleLarge,
                style2 = mTypography.titleLarge,
                modifier = Modifier.width(50.dp),
                text1 = "${uiState.stepTraining.numberSet}",
                text2 = "${uiState.currentDistance/( if (set.distance.unit == Units.KM) 1000 else 1)}",
                text3 = "${uiState.currentRest}",
            )
            //Total target (unit)
            ColumnsA(
                style1 = mTypography.bodyLarge,
                style2 = mTypography.titleLarge,
                modifier = Modifier.padding(start = 12.dp).width(100.dp),
                text1 = "${uiState.stepTraining.quantitySet}",
                text2 = "${set.distance.value /( if (set.distance.unit == Units.KM) 1000 else 1) }" +
                        " (${stringResource(set.distance.unit.id)})",
                text3 = "${set.rest.value/( if (set.rest.unit == Units.M) 60 else 1)}" +
                        " (${ stringResource(set.rest.unit.id) })",
            )
        }
    }
}
@Composable fun LayoutDuration(uiState: ExecuteWorkoutScreenState) {
    Row(modifier = Modifier.fillMaxWidth().padding(start = 8.dp)) {
        uiState.stepTraining?.currentSet?.let { set->
            //Description
            ColumnsA(
                style1 = mTypography.bodyLarge,
                style2 = mTypography.titleLarge,
                modifier = Modifier.padding(start = 12.dp).weight(1f),
                text1 = stringResource(R.string.sets) + ":",
                text2 = stringResource(R.string.duration_set) + ":",
                text3 = stringResource(R.string.weight) + ":",
                text4 = stringResource(R.string.rest) + ":",
            )
            //Value
            ColumnsA(
                style1 = mTypography.titleLarge,
                style2 = mTypography.titleLarge,
                modifier = Modifier.width(50.dp),
                text1 = "${(uiState.stepTraining.numberSet)}",
                text2 = "${uiState.currentDuration/( if (set.duration.unit == Units.M) 60 else 1)}",
                text3 = "${set.weight.value / ( if (set.weight.unit == Units.KG) 1000 else 1 ) }",
                text4 = "${uiState.currentRest}",
            )
            //Total target (unit)
            ColumnsA(
                style1 = mTypography.bodyLarge,
                style2 = mTypography.titleLarge,
                modifier = Modifier.padding(start = 12.dp).width(100.dp),
                text1 = "${uiState.stepTraining.quantitySet }",
                text2 = "${set.duration.value /( if (set.duration.unit == Units.M) 60 else 1) }" +
                        " (${ stringResource(set.duration.unit.id) })",
                text3 = "(${stringResource(set.weight.unit.id) })",
                text4 = "${set.rest.value/( if (set.rest.unit == Units.M) 60 else 1)}" +
                        " (${ stringResource(set.rest.unit.id) })",
            )
        }
    }
}

@Composable fun ColumnsA(modifier: Modifier = Modifier, text1: String, text2: String, text3: String,
                         text4: String = "", style1: TextStyle, style2: TextStyle){
    Column( modifier = modifier, horizontalAlignment = Alignment.End) {
        Position(text1, style1, style2)
        Position(text2, style1, style2)
        Position(text3, style1, style2)
        Position(text4, style1, style2)
    }
}
@Composable fun Position(text:String, style1: TextStyle, style2: TextStyle){
    Row(modifier = Modifier.padding(top = 4.dp)){ TextApp(style = style1, text = text)
        Text(text = "",style = style2)
    }
}
@Composable fun ButtonFasterSlower(uiState: ExecuteWorkoutScreenState){
    var downInterval = {}
    var upInterval = {}

    uiState.training?.let { training ->
        uiState.stepTraining?.currentSet?.let { set ->
            downInterval = { uiState.updateSet(
                training.idTraining, (set as SetImpl).copy(intervalReps = set.intervalReps.minus())) }
            upInterval = { uiState.updateSet(
                training.idTraining, (set as SetImpl).copy(intervalReps = set.intervalReps.plus())) }
        }
    }
    val color = if(!uiState.enableChangeInterval) MaterialTheme.colorScheme.surfaceContainerLow
                else MaterialTheme.colorScheme.outline

    Row(verticalAlignment = Alignment.Bottom,
        modifier = Modifier.padding(top = 4.dp, start = 12.dp, end = 2.dp).width(148.dp))
    {
        IconQ.Slower(modifier = Modifier.padding(bottom = 4.dp),
            onClick = { if(uiState.enableChangeInterval) upInterval()}, color = color)
        TextApp(style = mTypography.titleLarge, modifier = Modifier.padding(horizontal = 6.dp),
            text = (uiState.stepTraining?.currentSet?.intervalReps?.toBigDecimal()?.setScale(1, RoundingMode.UP) ?: "  ").toString())
        IconQ.Faster(modifier = Modifier.padding(bottom = 4.dp),
            onClick = { if(uiState.enableChangeInterval) downInterval()}, color = color)
    }
}

@Composable fun NextExercise(nextExercise: NextExercise?){
    TextApp(style = mTypography.bodyLarge, maxLines = 2, textAlign = TextAlign.Start,
            modifier = Modifier.padding(top = 18.dp),
            text = "${ stringResource(R.string.next_exercise)}: ${nextExercise?.nextActivityName ?: ""}")
    TextApp(style = mTypography.bodyLarge,modifier = Modifier.padding(start = 12.dp),
        text = "${stringResource(R.string.sets)}:" +
                " ${ nextExercise?.nextExerciseQuantitySet?.let { if(it != 0) it else "" }}" +
                " ${ nextExercise?.nextExerciseSummarizeSet?.let { viewNextSets(it) }} ")
}
@Composable fun viewNextSets(list: List<Pair<String, Int>>): String{
    return if(list.isNotEmpty())
        "(" +list.map{"${it.first}${stringResource(it.second).lowercase()}"}.joinToString(separator = "-") +")"
    else ""
}


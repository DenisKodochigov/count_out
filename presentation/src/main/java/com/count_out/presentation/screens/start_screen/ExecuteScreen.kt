package com.count_out.presentation.screens.start_screen

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.NextExercise
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.enums.Units
import com.count_out.presentation.R
import com.count_out.presentation.models.SetImplP
import com.count_out.presentation.screens.prime.Action
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeScreen
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.bottom_sheet.BottomSheetSaveTraining
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.custom_view.IconQ
import java.math.RoundingMode

@Composable fun ExecuteWorkoutScreen(viewModel: ExecuteViewModel){
    LaunchedEffect(Unit) { viewModel.submitEvent(ExecuteEvent.GetPlan) }
    ExecuteWorkoutScreenCreateView( viewModel = viewModel )
}
@Composable fun ExecuteWorkoutScreenCreateView(viewModel: ExecuteViewModel){
    val action = Action {viewModel.submitEvent(it) }
    viewModel.screenState.collectAsState().value.let { screenState ->
        PrimeScreen(loader = screenState) { dataState ->
            ExecuteWorkoutScreenLayout(dataState, action = action)
        }
    }
}
@Composable fun ExecuteWorkoutScreenLayout(dataState: ExecuteState, action: Action){
    if (dataState.showBS.training) BottomSheetSaveTraining(dataState)
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp),
        content = {
            SensorInfo(dataState)
            AdditionalInformation(dataState, action, modifier = Modifier.weight(1f))
            ExerciseInfo(dataState, action)
            DownPlace(dataState, action)
        }
    )
}
@Composable fun SensorInfo(dataState: ExecuteState) {
    val style = MaterialTheme.typography.displayLarge
    val sizeIcon = 32.dp
    Row( modifier = Modifier
        .fillMaxWidth()
        .padding(top = 4.dp, bottom = 4.dp, start = 12.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically)
    {   //Time
        TextApp(text = "${dataState.flowTime.hour}:${dataState.flowTime.min}:${dataState.flowTime.sec}", style = style)
        Spacer(modifier = Modifier.weight(1f))
        //Location
        Icon(contentDescription = null, modifier = Modifier.size(sizeIcon),
            imageVector = if(dataState.coordinate == null) Icons.Outlined.LocationOn
            else Icons.Filled.LocationOn,)
        Spacer(modifier = Modifier.weight(1f))
        //HearthRate
        Icon( modifier = Modifier.size(sizeIcon), contentDescription = null,
            imageVector = if(dataState.bleConnectState != ConnectState.CONNECTED) Icons.Outlined.HeartBroken
            else Icons.Filled.Favorite)
        TextApp(style = style, modifier = Modifier.padding(start=12.dp),
            text = if(dataState.bleConnectState != ConnectState.CONNECTED) "---"
            else dataState.heartRate.toString() )
    }
    HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.surfaceContainerLow)
}
@Composable fun AdditionalInformation(dataState: ExecuteState, action: Action, modifier: Modifier = Modifier){
    Column (modifier = modifier.fillMaxWidth()) {
        TextApp(text = " ", style = MaterialTheme.typography.bodyMedium)
//        Text(text = "Screen Execute ${dataState.stepTraining}")
    }
}
@Composable fun ExerciseInfo(dataState: ExecuteState, action: Action) {
    Frame{
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp)){
            val text = if (dataState.stepTraining?.exercise?.activity?.name.isNullOrEmpty()) "" else
                "${dataState.stepTraining.exercise?.activity?.name ?: ""}:" +
                " ${dataState.stepTraining.numberExercise}/${dataState.stepTraining.quantityExercise}"
            TextApp(text = text,
                modifier = Modifier.padding(bottom = 12.dp),
                style = MaterialTheme.typography.titleLarge)
            when(dataState.stepTraining?.currentSet?.goal ?: Goal.Count){
                Goal.Count -> LayoutCount(dataState, action)
                Goal.Distance -> LayoutDistance(dataState)
                Goal.Duration -> LayoutDuration(dataState)
                Goal.CountGroup -> {}
            }
            NextExercise(dataState.stepTraining?.nextExercise)
        }
    }
}
@Composable fun DownPlace(dataState: ExecuteState, action: Action) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 0.dp, top = 12.dp),
        horizontalArrangement = Arrangement.Center) {
        ButtonStartedService(dataState, action)
        Spacer(modifier = Modifier.width(32.dp))
        ButtonPauseService(dataState, action)
        Spacer(modifier = Modifier.width(32.dp))
        ButtonStoppedService(dataState, action)
    }
}
@Composable fun ButtonStartedService(dataState: ExecuteState, action: Action){
    val ifValue = (dataState.stateWorkOutService != RunningState.Started) ||
                            (dataState.stateWorkOutService == RunningState.Binding)
    IconQ.Play(
        onClick = { if (ifValue){ action.ex(ExecuteEvent.Start) } },
        color = with(MaterialTheme.colorScheme) { if (ifValue) outline else surfaceContainerLow})
}
@Composable fun ButtonStoppedService(dataState: ExecuteState, action: Action){
    val ifValue = (dataState.stateWorkOutService == RunningState.Started) || (dataState.stateWorkOutService == RunningState.Paused)
    IconQ.Stop(
        onClick = {
            if (ifValue){
                action.ex(ExecuteEvent.Stop)
                action.ex(ExecuteEvent.ShowBS(dataState.showBS))
                dataState.showBottomSheetSaveTraining.value = true
            }
        },
        color = with(MaterialTheme.colorScheme) { if (ifValue) outline else surfaceContainerLow })
}
@Composable fun ButtonPauseService(dataState: ExecuteState, action: Action){
    val ifValue = (dataState.stateWorkOutService == RunningState.Started)
    IconQ.Pause(
        onClick = { if (ifValue){ action.ex(ExecuteEvent.Pause) } },
        color = with(MaterialTheme.colorScheme) { if (ifValue) outline else surfaceContainerLow })
}

@Composable fun LayoutCount(dataState: ExecuteState, action: Action) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 8.dp)) {
        dataState.stepTraining?.currentSet?.let { set ->
            //Description
            ColumnsA(
                style1 = MaterialTheme.typography.bodyLarge,
                style2 = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f),
                text1 = stringResource(R.string.sets) + ":",
                text2 = stringResource(R.string.reps) + ":",
                text3 = stringResource(R.string.weight) + ":",
                text4 = stringResource(R.string.rest) + ":",
            )
            //Value
            ColumnsA(
                style1 = MaterialTheme.typography.titleLarge,
                style2 = MaterialTheme.typography.titleLarge,
                modifier = Modifier.width(50.dp),
                text1 = "${dataState.stepTraining.numberSet}",
                text2 = "${dataState.currentCount}",
                text3 = "${set.weight.value / ( if (set.weight.unit == Units.KG) 1000 else 1 ) }",
                text4 = "${dataState.currentRest}",
            )
            //Total target (unit)
            ColumnsA(
                style1 = MaterialTheme.typography.bodyLarge,
                style2 = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .width(100.dp),
                text1 = "${dataState.stepTraining.quantitySet}",
                text2 = "${set.reps}",
                text3 = "(${stringResource(set.weight.unit.id) })",
                text4 = "${set.rest.value/( if (set.rest.unit == Units.M) 60 else 1)}" +
                        " (${ stringResource(set.rest.unit.id) })",
            )
        }
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        TextApp(text = stringResource(R.string.interval) + ":",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp))
        ButtonChangeInterval(dataState, action)
    }
}
@Composable fun LayoutDistance(dataState: ExecuteState) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 0.dp)) {
        dataState.stepTraining?.currentSet?.let { set ->
            //Description
            ColumnsA(
                style1 = MaterialTheme.typography.bodyLarge,
                style2 = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f),
                text1 = stringResource(R.string.sets) + ":",
                text2 = stringResource(R.string.distance) + ":",
                text3 = stringResource(R.string.rest) + ":",
            )
            //Value
            ColumnsA(
                style1 = MaterialTheme.typography.titleLarge,
                style2 = MaterialTheme.typography.titleLarge,
                modifier = Modifier.width(50.dp),
                text1 = "${dataState.stepTraining.numberSet}",
                text2 = "${dataState.currentDistance/( if (set.distance.unit == Units.KM) 1000 else 1)}",
                text3 = "${dataState.currentRest}",
            )
            //Total target (unit)
            ColumnsA(
                style1 = MaterialTheme.typography.bodyLarge,
                style2 = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .width(100.dp),
                text1 = "${dataState.stepTraining.quantitySet}",
                text2 = "${set.distance.value /( if (set.distance.unit == Units.KM) 1000 else 1) }" +
                        " (${stringResource(set.distance.unit.id)})",
                text3 = "${set.rest.value/( if (set.rest.unit == Units.M) 60 else 1)}" +
                        " (${ stringResource(set.rest.unit.id) })",
            )
        }
    }
}
@Composable fun LayoutDuration(dataState: ExecuteState) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 8.dp)) {
        dataState.stepTraining?.currentSet?.let { set->
            //Description
            ColumnsA(
                style1 = MaterialTheme.typography.bodyLarge,
                style2 = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f),
                text1 = stringResource(R.string.sets) + ":",
                text2 = stringResource(R.string.duration_set) + ":",
                text3 = stringResource(R.string.weight) + ":",
                text4 = stringResource(R.string.rest) + ":",
            )
            //Value
            ColumnsA(
                style1 = MaterialTheme.typography.titleLarge,
                style2 = MaterialTheme.typography.titleLarge,
                modifier = Modifier.width(50.dp),
                text1 = "${(dataState.stepTraining.numberSet)}",
                text2 = "${dataState.currentDuration/( if (set.duration.unit == Units.M) 60 else 1)}",
                text3 = "${set.weight.value / ( if (set.weight.unit == Units.KG) 1000 else 1 ) }",
                text4 = "${dataState.currentRest}",
            )
            //Total target (unit)
            ColumnsA(
                style1 = MaterialTheme.typography.bodyLarge,
                style2 = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .width(100.dp),
                text1 = "${dataState.stepTraining.quantitySet }",
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

@Composable fun ButtonChangeInterval(dataState: ExecuteState, action: Action){

    val color = if(!dataState.enableChangeInterval) MaterialTheme.colorScheme.surfaceContainerLow
                        else MaterialTheme.colorScheme.outline
    Row(verticalAlignment = Alignment.Bottom,
        modifier = Modifier.padding(top = 4.dp, start = 12.dp, end = 2.dp).width(148.dp))
    {
        IconQ.Slower(modifier = Modifier.padding(bottom = 4.dp),
            onClick = { action.ex(ExecuteEvent.UpInterval)}, color = color)
        TextApp(style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 6.dp),
            text = (dataState.stepTraining?.currentSet?.intervalReps?.toBigDecimal()?.setScale(1, RoundingMode.UP) ?: "  ").toString())
        IconQ.Faster(modifier = Modifier.padding(bottom = 4.dp),
            onClick = { action.ex(ExecuteEvent.DownInterval)}, color = color)
    }
}

@Composable fun NextExercise(nextExercise: NextExercise?){
    TextApp(style = MaterialTheme.typography.bodyLarge, maxLines = 2, textAlign = TextAlign.Start,
            modifier = Modifier.padding(top = 18.dp),
            text = "${ stringResource(R.string.next_exercise)}: ${nextExercise?.nextActivityName ?: ""}")
    TextApp(style = MaterialTheme.typography.bodyLarge,modifier = Modifier.padding(start = 12.dp),
        text = "${stringResource(R.string.sets)}:" +
                " ${ nextExercise?.nextExerciseQuantitySet?.let { if(it != 0) it else "" } ?: ""}" +
                " ${ nextExercise?.nextExerciseSummarizeSet?.let { viewNextSets(it) } ?: ""} ")
}
@Composable fun viewNextSets(list: List<Pair<String, Int>>): String{
    return if(list.isNotEmpty())
        "(" + list.map{"${it.first}${stringResource(it.second).lowercase()}"}.joinToString(separator = "-") +")"
    else ""

}
@Preview
@Composable fun PreviewExecuteWorkoutScreen(){
    val dataState = ExecuteState()
    val action = object : Action {
        override fun ex(ev: Event) {  }
    }
    ExecuteWorkoutScreenLayout( dataState, action )
}

//@Composable fun ButtonChangeInterval(dataState: ExecuteState, action: Action){
//    var downInterval = {}
//    var upInterval = {}
//
//    dataState.plan?.let { plan ->
//        dataState.stepTraining?.currentSet?.let { set ->
//            downInterval = { }
//                dataState.updateSet(
//                plan.idTraining, (set as SetImplP).copy(intervalReps = set.intervalReps.minus()))
//            upInterval = { }
//                dataState.updateSet(
//                    plan.idTraining, (set as SetImplP).copy(intervalReps = set.intervalReps.plus()))
//        }
//    }
//    val color = if(!dataState.enableChangeInterval) MaterialTheme.colorScheme.surfaceContainerLow
//                else MaterialTheme.colorScheme.outline
//
//    Row(verticalAlignment = Alignment.Bottom,
//        modifier = Modifier
//            .padding(top = 4.dp, start = 12.dp, end = 2.dp)
//            .width(148.dp))
//    {
//        IconQ.Slower(modifier = Modifier.padding(bottom = 4.dp),
//            onClick = { if(dataState.enableChangeInterval) upInterval()}, color = color)
//        TextApp(style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 6.dp),
//            text = (dataState.stepTraining?.currentSet?.intervalReps?.toBigDecimal()?.setScale(1, RoundingMode.UP) ?: "  ").toString())
//        IconQ.Faster(modifier = Modifier.padding(bottom = 4.dp),
//            onClick = { if(dataState.enableChangeInterval) downInterval()}, color = color)
//    }
//}
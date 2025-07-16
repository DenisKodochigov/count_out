package com.count_out.presentation.screens.start_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.NextExercise
import com.count_out.domain.entity.StepPlan
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.workout.Set
import com.count_out.presentation.R
import com.count_out.presentation.models.ParameterImplP
import com.count_out.presentation.screens.prime.PrimeScreen
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TopBarApp
import com.count_out.presentation.view_element.bottom_sheet.BottomSheetSaveTraining
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.custom_view.IconQ
import java.math.RoundingMode

@Composable fun ExecuteWorkoutScreen(viewModel: ExecuteViewModel){
    LaunchedEffect(Unit) { viewModel.submitEvent(ExecuteEvent.GetPlan) }
    ExecuteWorkoutScreenCreateView( viewModel = viewModel )
}
@Composable fun ExecuteWorkoutScreenCreateView(viewModel: ExecuteViewModel){
    viewModel.screenState.collectAsState().value.let { screenState ->
        PrimeScreen(loader = screenState) { dataState ->
            ExecuteWorkoutScreenLayout(dataState)
        }
    }
}
@Composable fun ExecuteWorkoutScreenLayout(dataState: ExecuteState){
    if (dataState.showBS.training) BottomSheetSaveTraining(dataState)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp),
        content = {
            TopBar(dataState)
            SensorInfo(dataState)
            AdditionalInformation(dataState, modifier = Modifier.weight(1f))
            ExerciseInfo(dataState)
            DownPlace(dataState)
        }
    )
}
@Composable fun TopBar(dataState: ExecuteState){
    TopBarApp(
        text = "${stringResource(R.string.training_text_fab)}: ${dataState.stepTraining?.namePlan ?: ""}",
        selected = true,
        onClickText = { dataState.event(ExecuteEvent.ToScreenPlans) } ,
    )
}
@Composable fun SensorInfo(dataState: ExecuteState) {
    val style = typography.displayLarge
    val sizeIcon = 32.dp
    Row( verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp),)
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
@Composable fun AdditionalInformation(dataState: ExecuteState, modifier: Modifier = Modifier){
    Column (modifier = modifier.fillMaxWidth()) {
//        Text(text = "Screen Execute ${typography.titleLarge.fontFamily}", style = typography.titleLarge)
        Button(onClick = {
            Log.d("KDS","AdditionalInformation ${dataState.stepTraining}")
            dataState.event(ExecuteEvent.ShowBS(dataState.showBS))}) { Text(text = "Show")}
    }
}
@Composable fun ExerciseInfo(dataState: ExecuteState) {
    Frame{
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp)){

            dataState.stepTraining?.let { stepPlan ->
                TextApp( modifier = Modifier.padding(bottom = 0.dp), style = typography.titleLarge,
                    text = stepPlan.exercise?.activity?.name?.let {
                        "$it: ${stepPlan.numberExercise}/${stepPlan.quantityExercise}"} ?: "")
                stepPlan.currentSet?.let { set->
                    when (set.goal ) {
                        Goal.Count -> InfoSet(dataState, stepPlan, set,
                            desc = "${stringResource(R.string.reps)} (${set.reps}):" )
                        Goal.Distance -> InfoSet(dataState, stepPlan, set,
                            desc = "${stringResource(R.string.distance) } ${ParameterImplP(set.distance).print()}:" )
                        Goal.Duration -> InfoSet(dataState, stepPlan, set,
                            desc = "${stringResource(R.string.duration_set)} ${ParameterImplP(set.duration).print()}:" )
                        Goal.CountGroup -> InfoSet(dataState, stepPlan, set,
                            desc = "${stringResource(R.string.reps)} (${set.reps}):" )
                    }
                }
                NextExercise(dataState.stepTraining.nextExercise)
            }
        }
    }
}
@Composable fun DownPlace(dataState: ExecuteState) {
    /**
     * Стостояния кнопок отображения:
     * 1. Play              Binding or Stoped
     * 2. Pause + Stop      Play
     * 3. Play + Stop       Pause
     */
    Row(horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 0.dp, top = 12.dp),)
    {
        dataState.event(ExecuteEvent.Start)
        when (dataState.stateWorkOut) {
            RunningState.Binding, RunningState.Stopped -> {
                IconQ.Play( onClick = { dataState.event(ExecuteEvent.Start)})}
            RunningState.Started -> {
                IconQ.Pause(onClick = { dataState.event(ExecuteEvent.Pause)})
                Spacer(modifier = Modifier.width(32.dp))
                IconQ.Stop(onClick = { dataState.event(ExecuteEvent.Stop(dataState.showBS))}) }
            RunningState.Paused -> {
                IconQ.Play( onClick = { dataState.event(ExecuteEvent.Start) })
                Spacer(modifier = Modifier.width(32.dp))
                IconQ.Stop(onClick = { dataState.event(ExecuteEvent.Stop(dataState.showBS))})}
        }
    }
}

@Composable fun InfoSet(dataState: ExecuteState, stepPlan: StepPlan, set: Set, desc: String){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 8.dp)) {
        val listParam = listOf(
            listOf("${stringResource(R.string.sets)} (${stepPlan.quantitySet}):", "${stepPlan.numberSet}"),
            listOf(desc, "${dataState.currentCount}"),
            listOf("${stringResource(R.string.rest)} ${ ParameterImplP(set.rest).print()}:", "${dataState.currentRest}"),
            listOf(stringResource(R.string.weight) + ":", ParameterImplP(set.weight).print()),
            listOf(stringResource(R.string.interval) + ":", "")
//                if (set.intervalReps > 0) "${set.intervalReps.discard(2)}" else "")
        )
        ColumnsB( modifier = Modifier.width(200.dp), listParam, 0)
        ColumnsB( modifier = Modifier.width(30.dp), listParam, 1)
        ChangeInterval(dataState, modifier = Modifier.align(Alignment.Bottom), set)
    }
}
@Composable fun ColumnsB(modifier: Modifier, listParam: List<List<String>>, order : Int){
    val modifierL = Modifier.padding(top = 0.dp)
    val style = typography.bodyLarge
    Column( modifier = modifier, horizontalAlignment = Alignment.End) {
        listParam.forEach { TextApp(text = it[order], style = style, modifier = modifierL) }
    }
}
@Composable fun ChangeInterval(dataState: ExecuteState, modifier:Modifier, set: Set){
    val color = with(MaterialTheme.colorScheme){
        if(dataState.enableChangeInterval) outline else surfaceContainerLow }
    Row(modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically)
    {
        IconQ.Slower(onClick = {if (dataState.enableChangeInterval)
                dataState.event(ExecuteEvent.UpInterval) }, color = color)
        TextApp(
            style = typography.titleLarge, modifier = Modifier.padding(start = 12.dp, end = 12.dp),
            text = (set.intervalReps.toBigDecimal().setScale(1, RoundingMode.UP)).toString())
        IconQ.Faster( onClick = { if (dataState.enableChangeInterval)
                dataState.event(ExecuteEvent.DownInterval) }, color = color)
    }
}

@Composable fun NextExercise(nextExercise: NextExercise?){
    val quantitySets = nextExercise?.nextExerciseQuantitySet?.let { if(it != 0) it else 0 } ?: 0
    TextApp(style = typography.titleLarge, maxLines = 2, textAlign = TextAlign.Start,
        modifier = Modifier.padding(top = 18.dp),
        text = "${ stringResource(R.string.next)}: ${nextExercise?.nextActivityName ?: ""} " +
                "(${pluralStringResource(R.plurals.set_1, quantitySets,quantitySets)})")
}

//@Composable fun ExecuteWorkoutScreen(viewModel: ExecuteViewModel){
//    LaunchedEffect(Unit) { viewModel.submitEvent(ExecuteEvent.GetPlan) }
//    ExecuteWorkoutScreenCreateView( viewModel = viewModel )
//}
//@Composable fun ExecuteWorkoutScreenCreateView(viewModel: ExecuteViewModel){
////    val action = remember {
////        Action {
////            Log.d(
////                "KDS",
////                "ExecuteAction ${viewModel.dataState.value.stepTraining} ${viewModel.dataState.value.showBS.training}"
////            )
////            viewModel.submitEvent(it)
////        }
////    }
////    val action = viewModel.action()
//    viewModel.screenState.collectAsState().value.let { screenState ->
//        PrimeScreen(loader = screenState) { dataState ->
////            Log.d("KDS","ExecuteAction ${viewModel.dataState.value.stepTraining} ${viewModel.dataState.value.showBS.training}")
//
//            ExecuteWorkoutScreenLayout(dataState, action = action)
//        }
//    }
//}
//@Composable fun ExecuteWorkoutScreenLayout(dataState: ExecuteState, action: Action){
//    if (dataState.showBS.training) BottomSheetSaveTraining(dataState, action)
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(horizontal = 4.dp),
//        content = {
//            TopBar(dataState, action)
//            SensorInfo(dataState)
//            AdditionalInformation(dataState, action, modifier = Modifier.weight(1f))
//            ExerciseInfo(dataState, action)
//            DownPlace(dataState, action)
//        }
//    )
//}
//@Composable fun TopBar(dataState: ExecuteState, action: Action){
//    TopBarApp(
//        text = "${stringResource(R.string.training_text_fab)}: ${dataState.stepTraining?.namePlan ?: ""}",
//        selected = true,
//        onClickText = { action.ex(ExecuteEvent.ToScreenPlans) } ,
//    )
//}
//@Composable fun SensorInfo(dataState: ExecuteState) {
//    val style = typography.displayLarge
//    val sizeIcon = 32.dp
//    Row( verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(start = 12.dp, end = 12.dp),)
//    {   //Time
//        TextApp(text = "${dataState.flowTime.hour}:${dataState.flowTime.min}:${dataState.flowTime.sec}", style = style)
//        Spacer(modifier = Modifier.weight(1f))
//        //Location
//        Icon(contentDescription = null, modifier = Modifier.size(sizeIcon),
//            imageVector = if(dataState.coordinate == null) Icons.Outlined.LocationOn
//            else Icons.Filled.LocationOn,)
//        Spacer(modifier = Modifier.weight(1f))
//        //HearthRate
//        Icon( modifier = Modifier.size(sizeIcon), contentDescription = null,
//            imageVector = if(dataState.bleConnectState != ConnectState.CONNECTED) Icons.Outlined.HeartBroken
//            else Icons.Filled.Favorite)
//        TextApp(style = style, modifier = Modifier.padding(start=12.dp),
//            text = if(dataState.bleConnectState != ConnectState.CONNECTED) "---"
//            else dataState.heartRate.toString() )
//    }
//    HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.surfaceContainerLow)
//}
//@Composable fun AdditionalInformation(dataState: ExecuteState, action: Action, modifier: Modifier = Modifier){
//    Column (modifier = modifier.fillMaxWidth()) {
////        Text(text = "Screen Execute ${typography.titleLarge.fontFamily}", style = typography.titleLarge)
//        Button(onClick = {
//            Log.d("KDS","AdditionalInformation ${dataState.stepTraining}")
//            action.ex(ExecuteEvent.ShowBS(dataState.showBS))}) { Text(text = "Show")}
//    }
//}
//@Composable fun ExerciseInfo(dataState: ExecuteState, action: Action) {
//    Frame{
//        Column (modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 12.dp, vertical = 12.dp)){
//
//            dataState.stepTraining?.let { stepPlan ->
//                TextApp( modifier = Modifier.padding(bottom = 0.dp), style = typography.titleLarge,
//                    text = stepPlan.exercise?.activity?.name?.let {
//                        "$it: ${stepPlan.numberExercise}/${stepPlan.quantityExercise}"} ?: "")
//                stepPlan.currentSet?.let { set->
//                    when (set.goal ) {
//                        Goal.Count -> InfoSet(dataState, action, stepPlan, set,
//                            desc = "${stringResource(R.string.reps)} (${set.reps}):" )
//                        Goal.Distance -> InfoSet(dataState, action, stepPlan, set,
//                            desc = "${stringResource(R.string.distance) } ${ParameterImplP(set.distance).print()}:" )
//                        Goal.Duration -> InfoSet(dataState, action, stepPlan, set,
//                            desc = "${stringResource(R.string.duration_set)} ${ParameterImplP(set.duration).print()}:" )
//                        Goal.CountGroup -> InfoSet(dataState, action, stepPlan, set,
//                            desc = "${stringResource(R.string.reps)} (${set.reps}):" )
//                    }
//                }
//                NextExercise(dataState.stepTraining.nextExercise)
//            }
//        }
//    }
//}
//@Composable fun DownPlace(dataState: ExecuteState, action: Action) {
//    /**
//     * Стостояния кнопок отображения:
//     * 1. Play              Binding or Stoped
//     * 2. Pause + Stop      Play
//     * 3. Play + Stop       Pause
//     */
//    Row(horizontalArrangement = Arrangement.Center,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(bottom = 0.dp, top = 12.dp),)
//    {
//        when (dataState.stateWorkOut) {
//            RunningState.Binding, RunningState.Stopped -> {
//                IconQ.Play( onClick = { action.ex(ExecuteEvent.Start)})}
//            RunningState.Started -> {
//                IconQ.Pause(onClick = { action.ex(ExecuteEvent.Pause)})
//                Spacer(modifier = Modifier.width(32.dp))
//                IconQ.Stop(onClick = { action.ex(ExecuteEvent.Stop(dataState.showBS))}) }
//            RunningState.Paused -> {
//                IconQ.Play( onClick = { action.ex(ExecuteEvent.Start) })
//                Spacer(modifier = Modifier.width(32.dp))
//                IconQ.Stop(onClick = { action.ex(ExecuteEvent.Stop(dataState.showBS))})}
//        }
//    }
//}
//
//@Composable fun InfoSet(dataState: ExecuteState, action: Action, stepPlan: StepPlan, set: Set, desc: String){
//    Row(modifier = Modifier
//        .fillMaxWidth()
//        .padding(start = 8.dp)) {
//        val listParam = listOf(
//            listOf("${stringResource(R.string.sets)} (${stepPlan.quantitySet}):", "${stepPlan.numberSet}"),
//            listOf(desc, "${dataState.currentCount}"),
//            listOf("${stringResource(R.string.rest)} ${ ParameterImplP(set.rest).print()}:", "${dataState.currentRest}"),
//            listOf(stringResource(R.string.weight) + ":", ParameterImplP(set.weight).print()),
//            listOf(stringResource(R.string.interval) + ":", "")
////                if (set.intervalReps > 0) "${set.intervalReps.discard(2)}" else "")
//        )
//        ColumnsB( modifier = Modifier, listParam, 0)
//        ColumnsB( modifier = Modifier.width(30.dp), listParam, 1)
//        ChangeInterval(modifier = Modifier.align(Alignment.Bottom), action, set, dataState.enableChangeInterval)
//    }
//}
//@Composable fun ColumnsB(modifier: Modifier, listParam: List<List<String>>, order : Int){
//    val modifierL = Modifier.padding(top = 0.dp)
//    val style = typography.bodyLarge
//    Column( modifier = modifier, horizontalAlignment = Alignment.End) {
//        listParam.forEach { TextApp(text = it[order], style = style, modifier = modifierL) }
//    }
//}
//@Composable fun ChangeInterval(modifier:Modifier, action: Action, set: Set, allowChange: Boolean){
//    val color = with(MaterialTheme.colorScheme){ if(allowChange) outline else surfaceContainerLow }
//    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.CenterVertically) {
//        IconQ.Slower(
//            onClick = {if (allowChange) action.ex(ExecuteEvent.UpInterval) }, color = color)
//        TextApp(
//            style = typography.titleLarge, modifier = Modifier.padding(start = 12.dp, end = 12.dp),
//            text = (set.intervalReps.toBigDecimal().setScale(1, RoundingMode.UP)).toString())
//        IconQ.Faster(
//            onClick = { if (allowChange) action.ex(ExecuteEvent.DownInterval) }, color = color)
//    }
//}
//
//@Composable fun NextExercise(nextExercise: NextExercise?){
//    val quantitySets = nextExercise?.nextExerciseQuantitySet?.let { if(it != 0) it else 0 } ?: 0
//    TextApp(style = typography.titleLarge, maxLines = 2, textAlign = TextAlign.Start,
//        modifier = Modifier.padding(top = 18.dp),
//        text = "${ stringResource(R.string.next)}: ${nextExercise?.nextActivityName ?: ""} " +
//                "(${pluralStringResource(R.plurals.set_1, quantitySets,quantitySets)})")
//}
//
//@Preview
//@Composable fun PreviewExecuteWorkoutScreen(){
//    val dataState = ExecuteState()
//    val action = object : Action { override fun ex(ev: Event) {  } }
//    ExecuteWorkoutScreenLayout( dataState, action )
//}
//
//@Composable fun ExecuteWorkoutScreen(viewModel: ExecuteViewModel){
//    LaunchedEffect(Unit) { viewModel.submitEvent(ExecuteEvent.GetPlan) }
//    ExecuteWorkoutScreenCreateView( viewModel = viewModel )
//}
//@Composable fun ExecuteWorkoutScreenCreateView(viewModel: ExecuteViewModel){
//    val action = Action {viewModel.submitEvent(it) }
//    viewModel.screenState.collectAsState().value.let { screenState ->
//        PrimeScreen(loader = screenState) { dataState ->
//            ExecuteWorkoutScreenLayout(dataState, action = action)
//        }
//    }
//}
//@Composable fun ExecuteWorkoutScreenLayout(dataState: ExecuteState, action: Action){
//    if (dataState.showBS.training) BottomSheetSaveTraining(dataState)
//    Column(
//        modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp),
//        content = {
//            TopBar(dataState, action)
//            SensorInfo(dataState)
//            AdditionalInformation(dataState, action, modifier = Modifier.weight(1f))
//            ExerciseInfo(dataState, action)
//            DownPlace(dataState, action)
//        }
//    )
//}
//@Composable fun TopBar(dataState: ExecuteState, action: Action){
//    TopBarApp(
//        text = "${stringResource(R.string.training_text_fab)}: ${dataState.stepTraining?.namePlan ?: ""}",
//        selected = true,
//        onClickText = { action.ex(ExecuteEvent.ToScreenPlans) } ,
//    )
//}
//@Composable fun SensorInfo(dataState: ExecuteState) {
//    val style = typography.displayLarge
//    val sizeIcon = 32.dp
//    Row( verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(start = 12.dp, end = 12.dp),)
//    {   //Time
//        TextApp(text = "${dataState.flowTime.hour}:${dataState.flowTime.min}:${dataState.flowTime.sec}", style = style)
//        Spacer(modifier = Modifier.weight(1f))
//        //Location
//        Icon(contentDescription = null, modifier = Modifier.size(sizeIcon),
//            imageVector = if(dataState.coordinate == null) Icons.Outlined.LocationOn
//            else Icons.Filled.LocationOn,)
//        Spacer(modifier = Modifier.weight(1f))
//        //HearthRate
//        Icon( modifier = Modifier.size(sizeIcon), contentDescription = null,
//            imageVector = if(dataState.bleConnectState != ConnectState.CONNECTED) Icons.Outlined.HeartBroken
//            else Icons.Filled.Favorite)
//        TextApp(style = style, modifier = Modifier.padding(start=12.dp),
//            text = if(dataState.bleConnectState != ConnectState.CONNECTED) "---"
//            else dataState.heartRate.toString() )
//    }
//    HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.surfaceContainerLow)
//}
//@Composable fun AdditionalInformation(dataState: ExecuteState, action: Action, modifier: Modifier = Modifier){
//    Column (modifier = modifier.fillMaxWidth()) {
//        Text(text = "Screen Execute ${typography.titleLarge.fontFamily}", style = typography.titleLarge)
//    }
//}
//@Composable fun ExerciseInfo(dataState: ExecuteState, action: Action) {
//    Frame{
//        Column (modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 12.dp, vertical = 12.dp)){
//            val text = if (dataState.stepTraining?.exercise?.activity?.name.isNullOrEmpty()) "" else
//                "${dataState.stepTraining.exercise?.activity?.name ?: ""}:" +
//                " ${dataState.stepTraining.numberExercise}/${dataState.stepTraining.quantityExercise}"
//            TextApp(text = text,
//                modifier = Modifier.padding(bottom = 12.dp),
//                style = typography.titleLarge)
//            when(dataState.stepTraining?.currentSet?.goal ?: Goal.Count){
//                Goal.Count -> LayoutCount(dataState, action)
//                Goal.Distance -> LayoutDistance(dataState)
//                Goal.Duration -> LayoutDuration(dataState)
//                Goal.CountGroup -> {}
//            }
//            NextExercise(dataState.stepTraining?.nextExercise)
//        }
//    }
//}
//@Composable fun DownPlace(dataState: ExecuteState, action: Action) {
//    Row(modifier = Modifier
//        .fillMaxWidth()
//        .padding(bottom = 0.dp, top = 12.dp),
//        horizontalArrangement = Arrangement.Center) {
//        ButtonStartedService(dataState, action)
//        Spacer(modifier = Modifier.width(32.dp))
//        ButtonPauseService(dataState, action)
//        Spacer(modifier = Modifier.width(32.dp))
//        ButtonStoppedService(dataState, action)
//    }
//}
//@Composable fun ButtonStartedService(dataState: ExecuteState, action: Action){
//    val ifValue = (dataState.stateWorkOutService != RunningState.Started) ||
//                            (dataState.stateWorkOutService == RunningState.Binding)
//    IconQ.Play(
//        onClick = { if (ifValue){ action.ex(ExecuteEvent.Start) } },
//        color = with(MaterialTheme.colorScheme) { if (ifValue) outline else surfaceContainerLow})
//}
//@Composable fun ButtonStoppedService(dataState: ExecuteState, action: Action){
//    val ifValue = (dataState.stateWorkOutService == RunningState.Started) || (dataState.stateWorkOutService == RunningState.Paused)
//    IconQ.Stop(
//        onClick = {
//            if (ifValue){
//                action.ex(ExecuteEvent.Stop)
//                action.ex(ExecuteEvent.ShowBS(dataState.showBS))
//                dataState.showBottomSheetSaveTraining.value = true
//            }
//        },
//        color = with(MaterialTheme.colorScheme) { if (ifValue) outline else surfaceContainerLow })
//}
//@Composable fun ButtonPauseService(dataState: ExecuteState, action: Action){
//    val ifValue = (dataState.stateWorkOutService == RunningState.Started)
//    IconQ.Pause(
//        onClick = { if (ifValue){ action.ex(ExecuteEvent.Pause) } },
//        color = with(MaterialTheme.colorScheme) { if (ifValue) outline else surfaceContainerLow })
//}
//
//@Composable fun LayoutCount(dataState: ExecuteState, action: Action) {
//    Row(modifier = Modifier
//        .fillMaxWidth()
//        .padding(start = 8.dp)) {
//        dataState.stepTraining?.currentSet?.let { set ->
//            //Description
//            ColumnsA(
//                style1 = typography.bodyLarge,
//                style2 = typography.titleLarge,
//                modifier = Modifier.padding(start = 12.dp).weight(1f),
//                text1 = stringResource(R.string.sets) + ":",
//                text2 = stringResource(R.string.reps) + ":",
//                text3 = stringResource(R.string.weight) + ":",
//                text4 = stringResource(R.string.rest) + ":",
//            )
//            //Value
//            ColumnsA(
//                style1 = typography.titleLarge,
//                style2 = typography.titleLarge,
//                modifier = Modifier.width(50.dp),
//                text1 = "${dataState.stepTraining.numberSet}",
//                text2 = "${dataState.currentCount}",
//                text3 = "${set.weight.value}",
//                text4 = "${dataState.currentRest}",
//            )
//            //Total target (unit)
//            ColumnsA(
//                style1 = typography.bodyLarge,
//                style2 = typography.titleLarge,
//                modifier = Modifier.padding(start = 12.dp).width(100.dp),
//                text1 = "${dataState.stepTraining.quantitySet}",
//                text2 = "${set.reps}",
//                text3 = "${set.weight.value} (${stringResource(set.weight.unit.id) })",
//                text4 = "${set.rest.value} (${stringResource(set.rest.unit.id)})",
//            )
//        }
//    }
//    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
//        TextApp(text = stringResource(R.string.interval) + ":",
//            style = typography.bodyLarge,
//            modifier = Modifier.padding(top = 8.dp))
//        ButtonChangeInterval(dataState, action)
//    }
//}
//@Composable fun LayoutDistance(dataState: ExecuteState) {
//    Row(modifier = Modifier
//        .fillMaxWidth()
//        .padding(start = 0.dp)) {
//        dataState.stepTraining?.currentSet?.let { set ->
//            //Description
//            ColumnsA(
//                style1 = typography.bodyLarge,
//                style2 = typography.titleLarge,
//                modifier = Modifier.padding(start = 12.dp).weight(1f),
//                text1 = stringResource(R.string.sets) + ":",
//                text2 = stringResource(R.string.distance) + ":",
//                text3 = "${stringResource(R.string.rest)}:",
//            )
//            //Value
//            ColumnsA(
//                style1 = typography.titleLarge,
//                style2 = typography.titleLarge,
//                modifier = Modifier.width(50.dp),
//                text1 = "${dataState.stepTraining.numberSet}",
//                text2 = "${dataState.currentDistance}",
//                text3 = "${dataState.currentRest}",
//            )
//            //Total target (unit)
//            ColumnsA(
//                style1 = typography.bodyLarge,
//                style2 = typography.titleLarge,
//                modifier = Modifier.padding(start = 12.dp).width(100.dp),
//                text1 = "${dataState.stepTraining.quantitySet}",
//                text2 = "${set.distance.value } (${stringResource(set.distance.unit.id)})",
//                text3 = "${set.rest.value} (${ stringResource(set.rest.unit.id) })",
//            )
//        }
//    }
//}
//@Composable fun LayoutDuration(dataState: ExecuteState) {
//    Row(modifier = Modifier
//        .fillMaxWidth()
//        .padding(start = 8.dp)) {
//        dataState.stepTraining?.currentSet?.let { set->
//            //Description
//            ColumnsA(
//                style1 = typography.bodyLarge,
//                style2 = typography.titleLarge,
//                modifier = Modifier.padding(start = 12.dp).weight(1f),
//                text1 = stringResource(R.string.sets) + ":",
//                text2 = stringResource(R.string.duration_set) + ":",
//                text3 = stringResource(R.string.weight) + ":",
//                text4 = stringResource(R.string.rest) + ":",
//            )
//            //Value
//            ColumnsA(
//                style1 = typography.titleLarge,
//                style2 = typography.titleLarge,
//                modifier = Modifier.width(50.dp),
//                text1 = "${(dataState.stepTraining.numberSet)}",
//                text2 = "${dataState.currentDuration}",
//                text3 = "${set.weight.value}",
//                text4 = "${dataState.currentRest}",
//            )
//            //Total target (unit)
//            ColumnsA(
//                style1 = typography.bodyLarge,
//                style2 = typography.titleLarge,
//                modifier = Modifier.padding(start = 12.dp).width(100.dp),
//                text1 = "${dataState.stepTraining.quantitySet}",
//                text2 = "${set.duration.value} (${ stringResource(set.duration.unit.id) })",
//                text3 = "${set.weight.value} (${stringResource(set.weight.unit.id)})",
//                text4 = "${set.rest.value} (${ stringResource(set.rest.unit.id) })",
//            )
//        }
//    }
//}
//
//@Composable fun ColumnsA(modifier: Modifier = Modifier, text1: String, text2: String, text3: String,
//                         text4: String = "", style1: TextStyle, style2: TextStyle){
//    Column( modifier = modifier, horizontalAlignment = Alignment.End) {
//        Position(text1, style1, style2)
//        Position(text2, style1, style2)
//        Position(text3, style1, style2)
//        Position(text4, style1, style2)
//    }
//}
//@Composable fun Position(text:String, style1: TextStyle, style2: TextStyle){
//    Row(modifier = Modifier.padding(top = 4.dp)){
//        TextApp(style = style1, text = text)
//        Text(text = "",style = style2)
//    }
//}
//
//@Composable fun ButtonChangeInterval(dataState: ExecuteState, action: Action){
//
//    val color = if(!dataState.enableChangeInterval) MaterialTheme.colorScheme.surfaceContainerLow
//                        else MaterialTheme.colorScheme.outline
//    Row(verticalAlignment = Alignment.Bottom,
//        modifier = Modifier
//            .padding(top = 4.dp, start = 12.dp, end = 2.dp)
//            .width(148.dp))
//    {
//        IconQ.Slower(modifier = Modifier.padding(bottom = 4.dp),
//            onClick = { action.ex(ExecuteEvent.UpInterval)}, color = color)
//        TextApp(style = typography.titleLarge, modifier = Modifier.padding(horizontal = 6.dp),
//            text = (dataState.stepTraining?.currentSet?.intervalReps?.toBigDecimal()?.setScale(1, RoundingMode.UP) ?: "  ").toString())
//        IconQ.Faster(modifier = Modifier.padding(bottom = 4.dp),
//            onClick = { action.ex(ExecuteEvent.DownInterval)}, color = color)
//    }
//}
//
//@Composable fun NextExercise(nextExercise: NextExercise?){
//    TextApp(style = typography.bodyLarge, maxLines = 2, textAlign = TextAlign.Start,
//            modifier = Modifier.padding(top = 18.dp),
//            text = "${ stringResource(R.string.next_exercise)}: ${nextExercise?.nextActivityName ?: ""}")
//    TextApp(style = typography.bodyLarge,modifier = Modifier.padding(start = 12.dp),
//        text = "${stringResource(R.string.sets)}:" +
//                " ${ nextExercise?.nextExerciseQuantitySet?.let { if(it != 0) it else "" } ?: ""}" +
//                " ${ nextExercise?.nextExerciseSummarizeSet?.let { viewNextSets(it) } ?: ""} ")
//}
//@Composable fun viewNextSets(list: List<Pair<String, Int>>): String{
//    return if(list.isNotEmpty())
//        "(" + list.map{"${it.first}${stringResource(it.second).lowercase()}"}.joinToString(separator = "-") +")"
//    else ""
//
//}
//@Preview
//@Composable fun PreviewExecuteWorkoutScreen(){
//    val dataState = ExecuteState()
//    val action = object : Action { override fun ex(ev: Event) {  } }
//    ExecuteWorkoutScreenLayout( dataState, action )
//}

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
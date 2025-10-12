package com.count_out.presentation.screens.execute

import android.widget.ProgressBar
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.HeartBroken
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.NavigateEvent
import com.count_out.domain.entity.NextExercise
import com.count_out.domain.entity.StepPlan
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.workout.Set
import com.count_out.presentation.R
import com.count_out.presentation.models.ParameterImplP
import com.count_out.presentation.screens.prime.PrimeScreen
import com.count_out.presentation.view_element.ProgressBar
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TopBarApp
import com.count_out.presentation.view_element.bottom_sheet.BottomSheetSaveTraining
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.custom_view.IconQ
import java.math.RoundingMode

@Composable fun ExecuteWorkoutScreen(viewModel: ExecuteViewModel, navigateEvent: NavigateEvent){
    viewModel.screenState.collectAsState().value.let { screenState ->
        PrimeScreen(loader = screenState) { dataState ->
            dataState.goToScreenPlans = { navigateEvent.goToScreenPlans() }
            ExecuteWorkoutScreenLayout(dataState)
        }
    }
}
@Composable fun ExecuteWorkoutScreenLayout(dataState: ExecuteState){
    if (dataState.showBS.plan) BottomSheetSaveTraining(dataState)
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
        onClickText = { dataState.goToScreenPlans() } ,
    )
}
@Composable fun SensorInfo(dataState: ExecuteState) {
    val style = typography.displayLarge
    val sizeIcon = 30.dp
    Row( verticalAlignment = Alignment.CenterVertically, modifier = Modifier
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

    }
}
@Composable fun ExerciseInfo(dataState: ExecuteState) {
    Frame{
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp)){
            dataState.stepTraining?.let { stepPlan ->
                val progress by animateFloatAsState(
                    targetValue = stepPlan.numberSet/(stepPlan.quantitySet).toFloat())
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom){
                    TextApp( modifier = Modifier.weight(1f), textAlign = TextAlign.Start,
                        style = typography.titleMedium,
                        text = stepPlan.exercise?.activity?.name ?: "")
                    Box(){
                        TextApp(modifier = Modifier.align(alignment = Alignment.Center),
                            text = "${stepPlan.numberSet}/${stepPlan.quantitySet}",
                            style = typography.labelSmall,)
                        CircularProgressIndicator(
                            progress = { progress },
                            modifier = Modifier.size(32.dp),
                            strokeWidth = 2.dp,
                            color = colorScheme.onPrimary,
                            trackColor = colorScheme.surfaceContainer,
                            strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                        )
                    }
                }

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
                ProgressBar(text = "Exercise ${stepPlan.numberExercise} / ${stepPlan.quantityExercise}",
                    alignment = Alignment.Start,
                    value = stepPlan.numberExercise/stepPlan.quantityExercise.toFloat() )
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
//            listOf("${stringResource(R.string.sets)} (${stepPlan.quantitySet}):", "${stepPlan.numberSet}"),
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
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        TextApp(style = typography.titleMedium, maxLines = 2, textAlign = TextAlign.End,
            text = nextExercise?.nextActivityName ?: "")
    }
}


package com.count_out.presentation.screens.execute

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.HeartBroken
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.count_out.presentation.view_element.VerticalProgress
import com.count_out.presentation.view_element.bottom_sheet.BottomSheetSaveTraining
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.custom_view.IconQ
import java.math.RoundingMode

@Composable fun ExecuteWorkoutScreen( viewModel: ExecuteViewModel, navigateEvent: NavigateEvent){
    viewModel.screenState.collectAsState().value.let { screenState ->
        PrimeScreen(loader = screenState) { dataState ->
            dataState.goToScreenPlans = { navigateEvent.goToScreenPlans() }
            ExecuteWorkoutScreenLayout( dataState)
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
            ExerciseInfoNew(dataState)
            DownPlace(dataState)
        }
    )
}
@Composable fun TopBar(dataState: ExecuteState){
    TopBarApp(
        text = "${stringResource(R.string.training_text_fab)}: ${dataState.stepPlan?.namePlan ?: ""}",
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
    Column (modifier = modifier.fillMaxSize()) {
//        TextApp(text = "displayLarge ${typography.displayLarge.fontSize} ", style = typography.displayLarge)
//        TextApp(text = "displayMedium ${typography.displayMedium.fontSize} ", style = typography.displayMedium)
//        TextApp(text = "displaySmall ${typography.displaySmall.fontSize} ", style = typography.displaySmall)
//        TextApp(text = "headlineLarge ${typography.headlineLarge.fontSize} ", style = typography.headlineLarge)
//        TextApp(text = "headlineMedium ${typography.headlineMedium.fontSize} ", style = typography.headlineMedium)
//        TextApp(text = "headlineSmall ${typography.headlineSmall.fontSize} ", style = typography.headlineSmall)
//        TextApp(text = "titleLarge ${typography.titleLarge.fontSize} ", style = typography.titleLarge)
//        TextApp(text = "titleMedium ${typography.titleMedium.fontSize} ", style = typography.titleMedium)
//        TextApp(text = "titleSmall ${typography.titleSmall.fontSize} ", style = typography.titleSmall)
//        TextApp(text = "labelLarge ${typography.labelLarge.fontSize} ", style = typography.labelLarge)
//        TextApp(text = "labelMedium ${typography.labelMedium.fontSize} ", style = typography.labelMedium)
//        TextApp(text = "labelSmall ${typography.labelSmall.fontSize} ", style = typography.labelSmall)
//        TextApp(text = "bodyLarge ${typography.bodyLarge.fontSize} ", style = typography.bodyLarge)
//        TextApp(text = "bodyMedium ${typography.bodyMedium.fontSize} ", style = typography.bodyMedium)
//        TextApp(text = "bodySmall ${typography.bodySmall.fontSize} ", style = typography.bodySmall)
    }
}
@Composable fun ExerciseInfoNew(dataState: ExecuteState) {

    var showNext by remember { mutableStateOf(false) }

    dataState.stepPlan?.let { stepPlan ->
        Row(modifier = Modifier.height(IntrinsicSize.Min), verticalAlignment = Alignment.Top) {//
            VerticalProgress(stepPlan.numberExercise, stepPlan.quantityExercise)
            AnimatedContent(
                targetState = showNext,
                transitionSpec = {
                    ContentTransform(
                        targetContentEnter = slideInVertically { fullHeight -> fullHeight } + fadeIn(),
                        initialContentExit = slideOutVertically { fullHeight -> -fullHeight } + fadeOut()
                    )},
                label = "exercise-switch"
            ) { isNext->
                Column {
                    ContextCurrentAndNext(!isNext, stepPlan, dataState)
                    Spacer(Modifier.height(6.dp))
                    ContextCurrentAndNext(isNext, stepPlan, dataState)
                }
            }
        }
    }
}

@Composable fun ContextCurrentAndNext(visible: Boolean, stepPlan: StepPlan, dataState: ExecuteState){

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = colorScheme.surfaceContainerHighest, shape = RoundedCornerShape(6.dp))
        .padding(8.dp)
    ) {
        if (visible) {
            ContextCurrentAndNextName(stepPlan.exercise?.activity?.name ?: "")
            ContextCurrentAndNextInfo(stepPlan, dataState)
        } else ContextCurrentAndNextName(stepPlan.nextExercise?.nextActivityName ?: "")
    }
}

@Composable fun ContextCurrentAndNextName(name: String){
    Text(text = name, style = typography.headlineMedium)
}
@Composable fun ContextCurrentAndNextInfo(stepPlan: StepPlan, dataState: ExecuteState){
    Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceAround) {
        ProgressSet(dataState, stepPlan)
        ProgressCountDistanceDuration(dataState, stepPlan)
        ProgressRest(dataState, stepPlan)
        ShowWeight(stepPlan)
    }
}

@Composable fun ProgressSet(dataState: ExecuteState,stepPlan: StepPlan) {
    if (stepPlan.quantitySet > 1)
        ProgressIndicator(dataState.stateWorkOut,
            stepPlan.numberSet,
            stepPlan.quantitySet,
            R.string.set
        )
}
@Composable fun ProgressCountDistanceDuration(dataState: ExecuteState, stepPlan: StepPlan) {
    stepPlan.currentSet?.let { set ->
//        Log.d("KDS", "ProgressCountDistanceDuration ${set.goal}")
        when (set.goal ) {
            Goal.Count -> ProgressIndicator(dataState.stateWorkOut,
                dataState.currentCount, set.reps,R.string.counts)
            Goal.Distance -> ProgressIndicator(dataState.stateWorkOut,
                dataState.currentDistance, set.distance.value.toInt(),
                R.string.distance)
            Goal.Duration -> ProgressIndicator(dataState.stateWorkOut,
                dataState.currentDuration, set.duration.value.toInt(),
                R.string.duration)
            Goal.CountGroup -> ProgressIndicator(dataState.stateWorkOut,
                dataState.currentCount, set.reps, R.string.counts)
        }
    }
}
@Composable fun ProgressRest(dataState: ExecuteState, stepPlan: StepPlan) {
    stepPlan.currentSet?.let { set ->
        ProgressIndicator( dataState.stateWorkOut,
            dataState.currentRest,set.rest.value.toInt(), R.string.rest)
    }
}
@Composable fun ShowWeight(stepPlan: StepPlan) {
    stepPlan.currentSet?.let { set ->
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 12.dp)) {
            TextApp(text = set.weight.value.toString(), style = typography.displaySmall)
            TextApp(text = stringResource(set.weight.unit.id), style = typography.labelSmall)
        }
    }
}
@Composable fun ProgressIndicator(state: RunningState, number: Int, quantity: Int, idTextIndicator: Int){
    val numberL = if (state == RunningState.Binding || state == RunningState.Stopped) 0.01 else number.toDouble()
    if (quantity.toDouble() >= numberL){
        val progress by animateFloatAsState( targetValue = (numberL/quantity).toFloat())
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Box(){
                TextApp(modifier = Modifier.align(alignment = Alignment.Center),
                    text = "$number",
                    style = typography.displaySmall,)
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(64.dp),
                    strokeWidth = 4.dp,
                    color = colorScheme.onPrimary,
                    trackColor = colorScheme.surfaceContainer,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                )
            }
            TextApp(text = stringResource(idTextIndicator), style = typography.labelMedium)
        }
    }
}

@Composable fun ExerciseInfo(dataState: ExecuteState) {
    Frame{
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp)){
            dataState.stepPlan?.let { stepPlan ->
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
                NextExercise(dataState.stepPlan.nextExercise)
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

//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth().padding(top = 8.dp)
//                        .padding(top = 8.dp)
//                        .background(
//                            color = colorScheme.surfaceContainerHighest,
//                            shape = RoundedCornerShape(6.dp)
//                        )
//                ) {
//                    TextApp(
//                        modifier = Modifier.padding(start = 4.dp),
//                        style = typography.headlineMedium,
//                        text = stepPlan.nextExercise?.nextActivityName ?: ""
//                    )
//                }
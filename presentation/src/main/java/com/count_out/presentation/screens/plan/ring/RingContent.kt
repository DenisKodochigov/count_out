package com.count_out.presentation.screens.plan.ring

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.SetViewId
import com.count_out.domain.entity.enums.PartName
import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Ring.Companion.amount
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen.contourAll1
import com.count_out.presentation.models.Dimen.contourHor2
import com.count_out.presentation.models.TypeKeyboard
import com.count_out.presentation.models.lg
import com.count_out.presentation.screens.plan.PlanEvent
import com.count_out.presentation.screens.plan.PlanEvent.ShowBS
import com.count_out.presentation.screens.plan.PlanState
import com.count_out.presentation.screens.plan.exercise.ListExercises
import com.count_out.presentation.screens.plan.getCollapsing
import com.count_out.presentation.screens.plan.getSelecting
import com.count_out.presentation.screens.plan.set.BodySet
import com.count_out.presentation.screens.plan.set.TaskSwitch
import com.count_out.presentation.screens.plan.set.ZonePulseSwitch
import com.count_out.presentation.screens.plan.setCollapsing
import com.count_out.presentation.screens.plan.setSelecting
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TextFieldApp
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.drag_drop_column.column.ColumnDragDrop
import com.count_out.presentation.view_element.icons.IconsCollapsing
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable fun Rings(dataState: PlanState, part: Part){
    Frame(colorAlpha = 0.8f, contour = contourHor2,
        modifier = Modifier.padding(start = 6.dp, bottom = 4.dp, top = 4.dp)){
        part.rings.forEachIndexed { ind,ring ->
            if (ring.amount > 1){CardRing(dataState, ring, ind)
            } else { ListExercises(dataState, ring) }
        }
//            ColumnDragDrop(
//                items = part.rings,
//                modifier = modifier,
//                content = { item -> ElementColum( item, dataState = dataState) },
//                onMoveItem = { from, to->
//                    Log.d("KDS"," from=$from   to=$to")
//                    dataState.event(
//                        PlanEvent.ChangeSequenceExercise(
//                            item = SetViewId(ringId = part.idPart, from = from, to = to)))
//                },)
        Spacer(modifier = Modifier.height(4.dp))
    }
}
@Composable fun CardRing(dataState: PlanState, ring: Ring, index: Int){
    Column {
        CardRingTitle(dataState, ring, index)
        CardRingBody(dataState, ring, getCollapsing(dataState, ring))
    }
}
@Composable fun CardRingTitle(dataState: PlanState, ring: Ring, index: Int){
    val enteredName: MutableState<String> = remember { mutableStateOf(ring.amount.toString() ) }
    Row( verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 6.dp)){
        IconsCollapsing(
            onClick = { setCollapsing(dataState, ring) },
            wrap = getCollapsing(dataState, ring) )
        Spacer(modifier = Modifier.width(2.dp))
        TextApp(
            text = stringResource(id = R.string.ring) + " ${index + 1}: ",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineMedium,
        )
        TextFieldApp(
            modifier = Modifier.padding(start = 4.dp),
            edit = true,
            typeKeyboard = TypeKeyboard.DIGIT,
            contentAlignment = Alignment.CenterStart,
            textStyle = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Start),
            colorLine = colorScheme.outline,
            placeholder = enteredName.value,
            onChangeFocus = {
                enteredName.value = it
                dataState.plan?.let { pl->
                    dataState.event(
                        PlanEvent.UpdateRing(ring.amount(enteredName.value.toInt())))
                }
            }
        )
        TextApp(
            text = " ${stringResource(id = R.string.circles)}",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.weight(1f))
        IconsGroup(
            onClickSpeech = { showSpeechRound(dataState, ring) },
            onClickAddExercise = {
                dataState.event(PlanEvent.CopyRing(ring = Ring.default(ring.partId))) },
            onClickRingExercise = { dataState.event(PlanEvent.RingOrExercise(ring)) },
            selected = ring.amount > 1
        )
        Spacer(modifier = Modifier.width(6.dp))
    }
}
@Composable fun CardRingBody(dataState: PlanState, ring: Ring, visible: Boolean ){
    AnimatedVisibility(modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp), visible = visible) {
        Row {
            Exercises(dataState, ring)
            ExerciseBody(dataState, ring)
        }
    }
}
@Composable
fun ExerciseBody(dataState: PlanState, ring: Ring) {
    val selectedExercise = ring.exercises.find { it.idExercise in dataState.selecting.exercises } ?: ring.exercises[0]
    ExerciseContent(dataState,selectedExercise)
}

@Composable fun ExerciseContent(dataState: PlanState, exercise: Exercise){
    Frame(colorAlpha = 0.5f, contour = contourAll1) {
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)) {
            TextApp(text = exercise.activity?.name ?: "",
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 6.dp),
                style = MaterialTheme.typography.headlineSmall)
            TaskSwitch( dataState, exercise.sets[0] )
            BodySet( dataState, exercise.sets[0] )
            ZonePulseSwitch( dataState, exercise.sets[0] )
        }
    }
}
@Composable fun Exercises(dataState: PlanState, ring: Ring) {
    ColumnDragDrop(
        items = ring.exercises,
        modifier = Modifier.padding(end = 4.dp),
        content = { item -> ExerciseElementColum(item, dataState, ring) },
        onMoveItem = { from, to->
            Log.d("KDS"," from=$from   to=$to")
            dataState.event(
                PlanEvent.ChangeSequenceExercise(
                    item = SetViewId(ringId = ring.idRing, from = from, to = to)))
        },)
}
@Composable fun ExerciseElementColum(exercise: Exercise, dataState: PlanState, ring: Ring) {
    val selected = getSelecting(dataState, exercise)
    lg("ExerciseElementColum $selected")
    val modifier = if (selected) Modifier.background(color = colorScheme.surfaceContainer) else Modifier
    Row(horizontalArrangement = Arrangement.Start,
        modifier= modifier
            .padding(vertical = 3.dp)
            .clickable {
                setSelecting(
                    dataState,
                    exercise,
                    ring.exercises.map { LongDm(it.idExercise) })
            }
            .border(width = 1.dp, shape = MaterialTheme.shapes.small, color = Color.LightGray))
    {
        TextApp(modifier = Modifier
            .width(50.dp)
            .padding(horizontal = 4.dp),
            textAlign = TextAlign.Center,
            text = "${exercise.idView} ${stringResource(R.string.exer)}",
            style = MaterialTheme.typography.bodyLarge)
    }
}
//@Composable fun CardExercise(dataState: PlanState, ring: Ring){
//    CardExerciseTitle(dataState, ring)
////    CardExerciseBody(dataState, ring)
//}
//@Composable fun CardExerciseTitle(dataState: PlanState, ring: Ring){
//    Row( verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 6.dp)){
//        IconsCollapsing(
//            onClick = { setCollapsing(dataState, ring) },
//            wrap = getCollapsing(dataState, ring) )
//        Spacer(modifier = Modifier.width(2.dp))
//        TextApp(
//            text = stringResource(id = R.string.ring)  ,
//            textAlign = TextAlign.Start,
//            style = MaterialTheme.typography.headlineMedium,)
//
//        TextApp(
//            text = stringResource(id = R.string.times),
//            textAlign = TextAlign.Start,
//            style = MaterialTheme.typography.headlineMedium,)
//        Spacer(modifier = Modifier.weight(1f))
//        IconsGroup(
//            onClickSpeech = { showSpeechRound(dataState, ring) },
//            onClickAddExercise = {
//                dataState.event(PlanEvent.CopyRing(ring = Ring.default(ring.partId))) },
//            onClickRingExercise = { dataState.event(PlanEvent.RingOrExercise(ring)) },
//            selected = ring.amount > 1
//        )
//        Spacer(modifier = Modifier.width(6.dp))
//    }
//}
//@Composable fun CardExerciseBody(dataState: PlanState, ring: Ring){
//
//}


//@Composable fun <T>ElementColum (item:T, dataState: PlanState){
//    Spacer(modifier = Modifier.padding(top = 1.dp))
//    Frame(contour = contourAll1) {
//        Column (modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),) {
//            CardRingTitle(dataState, item as Ring, 0)
//            BodyExercise(dataState, item as Exercise)
//        }
//    }
//}
//@Composable fun ListExercise(dataState: PlanState, ring: Ring){
//    if (getCollapsing(dataState, ring) && ring.amount > 0){
////        ListExercises(dataState = dataState, part = part, modifier = Modifier.padding(end = 8.dp))
//    }
//}
fun showSpeechRound(dataState: PlanState, ring: Ring){
    dataState.item = ring
    dataState.event(ShowBS(dataState.showBS.copy(domain = ring)))
}


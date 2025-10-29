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
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.SetViewId
import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Ring.Companion.amount
import com.count_out.domain.entity.workout.Set
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen.contourAll1
import com.count_out.presentation.models.TypeKeyboard
import com.count_out.presentation.screens.plan.PlanEvent
import com.count_out.presentation.screens.plan.PlanEvent.ShowBS
import com.count_out.presentation.screens.plan.PlanState
import com.count_out.presentation.screens.plan.exercise.ExercisesList
import com.count_out.presentation.screens.plan.getCollapsing
import com.count_out.presentation.screens.plan.getSelecting
import com.count_out.presentation.screens.plan.set.SetBody
import com.count_out.presentation.screens.plan.setCollapsing
import com.count_out.presentation.screens.plan.setSelecting
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TextFieldApp
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.drag_drop_column.column.ColumnDragDrop
import com.count_out.presentation.view_element.icons.IconGoal
import com.count_out.presentation.view_element.icons.IconZoneNew
import com.count_out.presentation.view_element.icons.IconsCollapsing
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable fun Rings(dataState: PlanState, part: Part){
    part.rings.forEachIndexed { ind,ring ->
        if (ring.amount > 1){ RingCard(dataState, ring, ind)
        } else { ExercisesList(dataState, ring) }
    }
    Spacer(modifier = Modifier.height(4.dp))
}
@Composable fun RingCard(dataState: PlanState, ring: Ring, index: Int){
    Frame(colorAlpha = 0.8f, contour = contourAll1,
        modifier = Modifier.padding(top = 4.dp)){
        Column {
            RingCardTitle(dataState, ring, index)
            RingCardBody(dataState, ring, getCollapsing(dataState, ring))
        }
    }
}
@Composable fun RingCardTitle(dataState: PlanState, ring: Ring, index: Int){
    val enteredName: MutableState<String> = remember { mutableStateOf(ring.amount.toString() ) }
    Row( verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 6.dp)){
        IconsCollapsing(
            onClick = { setCollapsing(dataState, ring) },
            wrap = getCollapsing(dataState, ring) )
        Spacer(modifier = Modifier.width(4.dp))
        TextApp(
            text = stringResource(id = R.string.ring) + " ${index + 1}: ",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineSmall,
        )
        TextFieldApp(
            modifier = Modifier.padding(start = 4.dp, end =4.dp),
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
            style = MaterialTheme.typography.headlineSmall,
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
@Composable fun RingCardBody(dataState: PlanState, ring: Ring, visible: Boolean ){
    AnimatedVisibility(modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp), visible = visible) {
        Row {
            RingCardBodyExercises(dataState, ring)
            RingCardBodyExerciseBody(dataState, ring)
        }
    }
}
@Composable fun RingCardBodyExercises(dataState: PlanState, ring: Ring) {
    ColumnDragDrop(
        items = ring.exercises,
        modifier = Modifier.padding(end = 4.dp),
        content = { item -> RingCardBodyExerciseElementColum(item, dataState, ring) },
        onMoveItem = { from, to->
            Log.d("KDS"," from=$from   to=$to")
            dataState.event(
                PlanEvent.ChangeSequenceExercise(
                    item = SetViewId(idOwner = ring.idRing, from = from, to = to)))
        },)
}
@Composable
fun RingCardBodyExerciseBody(dataState: PlanState, ring: Ring) {
    val selectedExercise = ring.exercises.find { it.idExercise in dataState.selecting.exercises } ?: ring.exercises[0]
    RingCardBodyExerciseBodyContent(dataState,selectedExercise)
}
@Composable fun RingCardBodyExerciseBodyContent(dataState: PlanState, exercise: Exercise){
    Frame(colorAlpha = 0.5f, contour = contourAll1) {
        Column (modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
            RingCardBodyExerciseName( dataState, exercise )
            SetBody( dataState, exercise.sets[0] )
            ControlExercise( dataState, exercise.sets[0] )
        }
    }
}
@Composable fun RingCardBodyExerciseName(dataState: PlanState, exercise: Exercise){
    Row(verticalAlignment = Alignment.CenterVertically){
        TextApp(text = exercise.activity?.name ?: "",
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(bottom = 0.dp, start = 6.dp),
            style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.weight(1f))
        IconsGroup(
            onClickCopy = { dataState.event(PlanEvent.CopyExercise(exercise))},
            onClickDelete = { dataState.event(PlanEvent.DelExercise(exercise)) },
            onClickEdit = {
                dataState.item = exercise
                dataState.event(ShowBS(dataState.showBS.copy(domain = exercise.activity)))},
            onClickSpeech = {
                dataState.item = exercise
                dataState.event(ShowBS(dataState.showBS.copy(domain = exercise))) },
        )
    }

}
@Composable fun ControlExercise(dataState: PlanState, set: Set){
    Row(verticalAlignment = Alignment.CenterVertically, modifier= Modifier.fillMaxWidth()){
        Spacer(modifier = Modifier.weight(1f))
        IconGoal(set.goal){dataState.event(PlanEvent.ChangeGoal( set))}
        Spacer(modifier = Modifier.weight(1f))
        IconZoneNew(set.intensity.ordinal + 1, onClick = {dataState.event(PlanEvent.ChangeZone(set))})
        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable fun RingCardBodyExerciseElementColum(exercise: Exercise, dataState: PlanState, ring: Ring) {
    val selected = getSelecting(dataState, exercise)
    val modifier = if (selected) Modifier.background(color = colorScheme.surfaceContainer) else Modifier
    Row(horizontalArrangement = Arrangement.Start,
        modifier= modifier
            .padding(vertical = 3.dp)
            .border(width = 1.dp, shape = MaterialTheme.shapes.small, color = Color.LightGray)
            .clickable {
                setSelecting(dataState, exercise, ring.exercises.map { LongDm(it.idExercise) }) }
    ) {
        TextApp(modifier = Modifier
            .width(50.dp)
            .padding(horizontal = 4.dp),
            textAlign = TextAlign.Center,
            text = "${stringResource(R.string.exer)} ${exercise.idView}",
            style = MaterialTheme.typography.titleMedium)
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


package com.count_out.presentation.screens.plan.ring

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Ring.Companion.amount
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen.contourHor2
import com.count_out.presentation.models.TypeKeyboard
import com.count_out.presentation.screens.plan.CarcassTitle
import com.count_out.presentation.screens.plan.PlanEvent
import com.count_out.presentation.screens.plan.PlanState
import com.count_out.presentation.screens.plan.exercise.ExercisesList
import com.count_out.presentation.screens.plan.exercise.RingCardBodyExerciseBody
import com.count_out.presentation.screens.plan.exercise.RingCardBodyExerciseList
import com.count_out.presentation.screens.plan.getCollapsing
import com.count_out.presentation.screens.plan.set.CarcassTuningSet
import com.count_out.presentation.screens.plan.setCollapsing
import com.count_out.presentation.screens.plan.setSelecting
import com.count_out.presentation.screens.plan.showSpeech
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TextFieldApp
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.icons.IconsCollapsing
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable fun Rings(dataState: PlanState, part: Part){
    part.rings.forEachIndexed { ind,ring ->
        if (ring.amount > 1) { RingCard(dataState, ring, ind) }
        else {                 ExercisesList(dataState, ring) }
    }
    Spacer(modifier = Modifier.height(4.dp))
}
@Composable fun RingCard(dataState: PlanState, ring: Ring, index: Int){
    Frame(colorAlpha = 0.4f, contour = contourHor2, modifier = Modifier.padding(top = 4.dp)){
        Column{
            RingCardTitle(dataState, ring, index)
            RingCardBody( dataState, ring )
        }
    }
}
@Composable fun RingCardTitle(dataState: PlanState, ring: Ring, index: Int){
    val enteredName: MutableState<String> = remember { mutableStateOf(ring.amount.toString() ) }
    CarcassTitle(
        onCollapsing = { IconsCollapsing(onClick = { setCollapsing(dataState, ring) },
            wrap = getCollapsing(dataState, ring) )},
        nameItem = { NameRing(dataState, ring, index, enteredName )},
        infoItem = { TextApp(style = MaterialTheme.typography.bodySmall,
            text = stringResource(id = R.string.exercises) + " ${ring.exercises.count()}: ",) },
        actionItem = {
            IconsGroup(
                onRingAdd = { dataState.event(PlanEvent.CopyRing(ring = Ring.default(ring.partId)))},
                onRingDel = { dataState.event(PlanEvent.DelRing(ring))},
                onRingSpeech = { showSpeech(dataState, ring)},
                onToExercise = {dataState.event(PlanEvent.RingOrExercise(ring))}
            )
        }
    )
}
@Composable fun RingCardBody(dataState: PlanState, ring: Ring){
    val visible = getCollapsing(dataState, ring)
    AnimatedVisibility(modifier = Modifier.padding(horizontal = 4.dp), visible = visible) {
        if (ring.exercises.isNotEmpty()) {
            if (ring.exercises.find{ it.idExercise in dataState.selecting.exercises } == null) {
                setSelecting(dataState, ring.exercises[0],
                    ring.exercises.map { LongDm(it.idExercise) })
            }
            CarcassTuningSet(
                columnLeft = { RingCardBodyExerciseList( dataState, ring)},
                columnRight = { RingCardBodyExerciseBody( dataState, ring)},
                button = {}
            )
        }
    }
}
@Composable fun NameRing(dataState: PlanState, ring: Ring, index: Int, enteredName: MutableState<String>){
    TextApp(
        text = stringResource(id = R.string.ring) + " ${index + 1}: ",
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.titleLarge,
    )
    TextFieldApp(
        modifier = Modifier.padding(start = 4.dp, end =4.dp).width(20.dp),
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
        style = MaterialTheme.typography.titleLarge,
    )
}




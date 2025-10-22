package com.count_out.presentation.screens.plan.ring

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Ring.Companion.amount
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen.contourAll1
import com.count_out.presentation.models.Dimen.contourHor2
import com.count_out.presentation.models.TypeKeyboard
import com.count_out.presentation.screens.plan.PlanEvent
import com.count_out.presentation.screens.plan.PlanEvent.ShowBS
import com.count_out.presentation.screens.plan.PlanState
import com.count_out.presentation.screens.plan.exercise.BodyExercise
import com.count_out.presentation.screens.plan.exercise.ListExercises
import com.count_out.presentation.screens.plan.getCollapsing
import com.count_out.presentation.screens.plan.setCollapsing
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TextFieldApp
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.icons.IconRingOrExercise
import com.count_out.presentation.view_element.icons.IconsCollapsing
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable fun Rings(dataState: PlanState, part: Part, modifier: Modifier = Modifier){
    Column( modifier = Modifier.padding(start = 6.dp, bottom = 4.dp, top = 4.dp)){
//        TitleRing(dataState = dataState, ring = ring)
        Frame(colorAlpha = 0.8f, contour = contourHor2){
            part.rings.forEachIndexed { ind,ring ->
                if (ring.amount > 1){ CardRing(dataState, ring, ind)
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
}

@Composable fun CardRing(dataState: PlanState, ring: Ring, index: Int){
    CardRingTitle(dataState, ring, index)
    CardRingBody(dataState, ring)
}
@Composable fun CardExercise(dataState: PlanState, ring: Ring){
    CardExerciseTitle(dataState, ring)
    CardExerciseBody(dataState, ring)
}
@Composable fun CardRingTitle(dataState: PlanState, ring: Ring, index: Int){
    val enteredName: MutableState<String> = remember { mutableStateOf(ring.amount.toString() ) }
    Row( verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 6.dp)){
        IconsCollapsing(
            onClick = { setCollapsing(dataState, ring) },
            wrap = getCollapsing(dataState, ring) )
        Spacer(modifier = Modifier.width(2.dp))
        TextApp(
            text = stringResource(id = R.string.ring) + "$index " ,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineMedium,)
        TextFieldApp(
            modifier = Modifier.padding(start = 4.dp),
            edit = true,
            typeKeyboard = TypeKeyboard.DIGIT,
            contentAlignment = Alignment.CenterStart,
            textStyle = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Start),
            colorLine = MaterialTheme.colorScheme.outline,
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
            text = stringResource(id = R.string.times),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineMedium,)
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
@Composable fun CardRingBody(dataState: PlanState, ring: Ring){

}

@Composable fun CardExerciseTitle(dataState: PlanState, ring: Ring){
    Row( verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 6.dp)){
        IconsCollapsing(
            onClick = { setCollapsing(dataState, ring) },
            wrap = getCollapsing(dataState, ring) )
        Spacer(modifier = Modifier.width(2.dp))
        TextApp(
            text = stringResource(id = R.string.ring)  ,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineMedium,)

        TextApp(
            text = stringResource(id = R.string.times),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineMedium,)
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
@Composable fun CardExerciseBody(dataState: PlanState, ring: Ring){

}


@Composable fun <T>ElementColum (item:T, dataState: PlanState,){
    Spacer(modifier = Modifier.padding(top = 1.dp))
    Frame(contour = contourAll1) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),) {
            CardRingTitle(dataState, item as Ring, 0)
            BodyExercise(dataState, item as Exercise)
        }
    }
}
@Composable fun ListExercise(dataState: PlanState, ring: Ring){
    if (getCollapsing(dataState, ring) && ring.amount > 0){
//        ListExercises(dataState = dataState, part = part, modifier = Modifier.padding(end = 8.dp))
    }
}
fun showSpeechRound(dataState: PlanState, ring: Ring){
    dataState.item = ring
    dataState.event(ShowBS(dataState.showBS.copy(domain = ring)))
}
//fun setCollapsing(dataState: PlanState, part: Ring) {
//    if (part.amount > 0) {
//        dataState.event(PlanEvent.SetCollapsing(dataState.collapsing.copy(item = part)))
//    }
//}
//fun getCollapsing(dataState: PlanState, ring: Ring): Boolean {
//    return dataState.collapsing.rings.find { it == ring.idRing } != null
//}

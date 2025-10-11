package com.count_out.presentation.screens.plan.ring

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.discard
import com.count_out.domain.entity.enums.PartName
import com.count_out.domain.entity.enums.RoundType
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Speech
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen.contourHor2
import com.count_out.presentation.screens.plan.PlanEvent
import com.count_out.presentation.screens.plan.PlanEvent.ShowBS
import com.count_out.presentation.screens.plan.PlanState
import com.count_out.presentation.view_element.EnumsTo
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.bottom_sheet.ShowBottomSheetSpeech
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.icons.IconsCollapsing
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable fun Part(dataState: PlanState, ring: Ring){

    Frame(colorAlpha = 0.8f, contour = contourHor2){
        Column( modifier = Modifier.padding(start = 6.dp, bottom = 4.dp, top = 4.dp)){
            TitlePart(dataState = dataState, ring = ring)
            ListExercise(dataState = dataState, ring = ring)
        }
    }
}
@Composable fun TitlePart(dataState: PlanState, ring: Ring){
    Row( verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 6.dp)){
        IconsCollapsing(
            onClick = { setCollapsing(dataState, ring) },
            wrap = getCollapsing(dataState, ring) )
        Spacer(modifier = Modifier.width(2.dp))
        Column(modifier = Modifier.weight(1f)) {
            TextApp(
                text = stringResource(id = EnumsTo(ring.numberLaps).string()),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.headlineSmall,)
            TextApp( style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Light,
                text = "${ stringResource(id = R.string.exercises) }: ${ring.amount}" +
                        " / ${ring.duration.value.discard(2)} ${ stringResource(id = R.string.min)}",) }
        IconsGroup(
            onClickSpeech = { showSpeechRound(dataState, ring) },
            onClickAddExercise = {
                dataState.event(PlanEvent.CopyExercise(exercise = Exercise.default(ring.idRing)))
            })
        Spacer(modifier = Modifier.width(6.dp))
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

fun setCollapsing(dataState: PlanState, part: Ring) {
    if (part.amount > 0) {
        dataState.event(PlanEvent.SetCollapsing(dataState.collapsing.copy(item = part)))
    }
}
fun getCollapsing(dataState: PlanState, ring: Ring): Boolean {
    return dataState.collapsing.rounds.find { it == ring.idRing } != null
}

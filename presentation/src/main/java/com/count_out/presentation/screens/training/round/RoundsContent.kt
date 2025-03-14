package com.count_out.presentation.screens.training.round

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
import com.count_out.presentation.screens.prime.Action
import com.count_out.presentation.screens.training.TrainingEvent
import com.count_out.presentation.screens.training.TrainingState
import com.count_out.domain.entity.enums.RoundType
import com.count_out.domain.entity.workout.Round
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen.contourHor2
import com.count_out.presentation.models.ExerciseImpl
import com.count_out.presentation.screens.training.exercise.ListExercises
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.icons.IconsCollapsing
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable
fun Round(dataState: TrainingState, action: Action, round: Round){
    Frame(colorAlpha = 0.8f, contour = contourHor2){
        Column( modifier = Modifier.padding(start = 6.dp, bottom = 4.dp, top = 4.dp)){
            TitleRound(dataState = dataState, action = action, round = round)
            ListExercise(dataState = dataState, action = action, round = round)
        }
    }
}
@Composable fun TitleRound(dataState: TrainingState, action: Action, round: Round){
    Row( verticalAlignment = Alignment.CenterVertically ){
        IconsCollapsing(
            onClick = { setCollapsing(dataState, round) },
            wrap = getCollapsing(dataState, round) )
        Spacer(modifier = Modifier.width(2.dp))
        TextApp(
            text = stringResource(id = nameRound(round)),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.weight(1f))
        Column {
            TextApp( style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Light,
                text = "${ stringResource(id = R.string.exercises) }: ${round.amount}" +
                        " / ${round.duration} ${ stringResource(id = R.string.min)}",) }
        IconsGroup(
            onClickSpeech = { showSpeechRound(dataState, round) },
            onClickAddExercise = {
                action.ex(TrainingEvent.CopyExercise(exercise = ExerciseImpl(roundId = round.idRound)))})
        Spacer(modifier = Modifier.width(6.dp))
    }
}
@Composable fun ListExercise(dataState: TrainingState, action: Action, round: Round){
    ListExercises(
        dataState = dataState,
        action = action,
        round = round,
        modifier = Modifier.padding(end = 8.dp),
        showExercises = getCollapsing(dataState, round) && round.amount > 0)
}

fun showSpeechRound(dataState: TrainingState, round: Round){
    when (round.roundType) {
        RoundType.WorkUp -> dataState.showSpeechWorkUp.value = true
        RoundType.WorkOut -> dataState.showSpeechWorkOut.value = true
        RoundType.WorkDown -> dataState.showSpeechWorkDown.value = true
    }
}

fun setCollapsing(dataState: TrainingState, round: Round) {
    if (round.amount > 0) {
        when (round.roundType) {
            RoundType.WorkUp -> dataState.workUpCollapsing.value = !dataState.workUpCollapsing.value
            RoundType.WorkOut -> dataState.workOutCollapsing.value = !dataState.workOutCollapsing.value
            RoundType.WorkDown -> dataState.workDownCollapsing.value = !dataState.workDownCollapsing.value
        }
    }
}
fun getCollapsing(dataState: TrainingState, round: Round): Boolean {
    return when (round.roundType) {
        RoundType.WorkUp -> dataState.workUpCollapsing.value
        RoundType.WorkOut -> dataState.workOutCollapsing.value
        RoundType.WorkDown -> dataState.workDownCollapsing.value
    }
}
fun nameRound(round: Round): Int {
    return when(round.roundType){
        RoundType.WorkUp -> R.string.work_up
        RoundType.WorkOut -> R.string.work_out
        RoundType.WorkDown -> R.string.work_down
    }
}
package com.count_out.presentation.screens.training.round

import android.util.Log
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
import com.count_out.domain.entity.enums.RoundType
import com.count_out.domain.entity.workout.Round
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen.contourHor2
import com.count_out.presentation.models.ExerciseImplP
import com.count_out.presentation.screens.training.ShowBottomSheetSpeech
import com.count_out.presentation.screens.training.TrainingEvent
import com.count_out.presentation.screens.training.TrainingEvent.ShowBS
import com.count_out.presentation.screens.training.TrainingState
import com.count_out.presentation.screens.training.exercise.ListExercises
import com.count_out.presentation.view_element.EnumsTo
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.icons.IconsCollapsing
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable fun Round(dataState: TrainingState, round: Round){
    when(round.roundType){
        RoundType.WorkUp -> ShowBottomSheetSpeech(dataState, dataState.showBS.workUp,
            R.string.work_up1, round)
        RoundType.WorkOut -> ShowBottomSheetSpeech(dataState, dataState.showBS.workOut,
            R.string.work_out1, round)
        RoundType.WorkDown -> ShowBottomSheetSpeech(dataState, dataState.showBS.workDown,
            R.string.work_down1, round)
    }
    Frame(colorAlpha = 0.8f, contour = contourHor2){
        Column( modifier = Modifier.padding(start = 6.dp, bottom = 4.dp, top = 4.dp)){
            TitleRound(dataState = dataState, round = round)
            ListExercise(dataState = dataState, round = round)
        }
    }
}
@Composable fun TitleRound(dataState: TrainingState, round: Round){
    Row( verticalAlignment = Alignment.CenterVertically ){
        IconsCollapsing(
            onClick = { setCollapsing(dataState, round) },
            wrap = getCollapsing(dataState, round) )
        Spacer(modifier = Modifier.width(2.dp))
        Column(modifier = Modifier.weight(1f)) {
            TextApp(
                text = stringResource(id = EnumsTo(round.roundType).string()),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.headlineSmall,)
            TextApp( style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Light,
                text = "${ stringResource(id = R.string.exercises) }: ${round.amount}" +
                        " / ${round.duration.value.discard(2)} ${ stringResource(id = R.string.min)}",) }
        IconsGroup(
            onClickSpeech = { showSpeechRound(dataState, round) },
            onClickAddExercise = {
                dataState.event(TrainingEvent.CopyExercise(exercise = ExerciseImplP(roundId = round.idRound)))})
        Spacer(modifier = Modifier.width(6.dp))
    }
}
@Composable fun ListExercise(dataState: TrainingState, round: Round){
    if (getCollapsing(dataState, round) && round.amount > 0){
        ListExercises(dataState = dataState, round = round, modifier = Modifier.padding(end = 8.dp))
    }
}
fun showSpeechRound(dataState: TrainingState, round: Round){
    dataState.item = round
    dataState.event(ShowBS(dataState.showBS.copy(element = round)))
}

fun setCollapsing(dataState: TrainingState, round: Round) {
    if (round.amount > 0) {
        dataState.event(TrainingEvent.SetCollapsing(dataState.collapsing.copy(item = round)))
    }
}
fun getCollapsing(dataState: TrainingState, round: Round): Boolean {
    return dataState.collapsing.rounds.find { it == round.idRound } != null
}

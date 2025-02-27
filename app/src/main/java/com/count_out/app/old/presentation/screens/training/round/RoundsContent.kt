//package com.count_out.app.presentation.screens.training.round
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import com.count_out.app.R
//import com.count_out.app.entity.Const.contourHor2
//import com.count_out.app.presentation.models.ExerciseImpl
//import com.count_out.app.presentation.prime.Action
//import com.count_out.app.presentation.screens.training.TrainingEvent
//import com.count_out.app.presentation.screens.training.TrainingState
//import com.count_out.app.presentation.screens.training.exercise.ListExercises
//import com.count_out.app.ui.view_components.TextApp
//import com.count_out.app.ui.view_components.custom_view.Frame
//import com.count_out.app.ui.view_components.icons.IconsCollapsing
//import com.count_out.app.ui.view_components.icons.IconsGroup
//import com.count_out.domain.entity.Round
//import com.count_out.domain.entity.enums.RoundType
//
//@Composable
//fun Round(dataState: TrainingState, action:Action, round: Round){
//    Frame(colorAlpha = 0.8f, contour = contourHor2){
//        Column( modifier = Modifier.padding(start = 6.dp, bottom = 4.dp, top = 4.dp)){
//            TitleRound(dataState = dataState, action = action, round = round)
//            ListExercise(dataState = dataState, action = action, round = round)
//        }
//    }
//}
//@Composable fun TitleRound(dataState: TrainingState, action:Action, round: Round){
//    Row( verticalAlignment = Alignment.CenterVertically ){
//        IconsCollapsing(
//            onClick = { setCollapsing(dataState, round) },
//            wrap = getCollapsing(dataState, round) )
//        Spacer(modifier = Modifier.width(2.dp))
//        TextApp(
//            text = stringResource(id = nameRound(round)),
//            textAlign = TextAlign.Start,
//            style = MaterialTheme.typography.headlineSmall,
//            modifier = Modifier.weight(1f))
//        Column {
//            TextApp( style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Light,
//                text = "${ stringResource(id = R.string.exercises) }: ${round.amount}" +
//                         " / ${round.duration} ${ stringResource(id = R.string.min)}",) }
//        IconsGroup(
//            onClickSpeech = {showSpeechRound(dataState, round)},
//            onClickAddExercise = {
//                action.ex(TrainingEvent.AddExercise(exercise = ExerciseImpl(roundId = round.idRound)))})
//        Spacer(modifier = Modifier.width(6.dp))
//    }
//}
//@Composable fun ListExercise(dataState: TrainingState, action:Action, round: Round){
//    ListExercises(
//        dataState = dataState,
//        action = action,
//        round = round,
//        modifier = Modifier.padding(end = 8.dp),
//        showExercises = getCollapsing(dataState, round) && round.amount > 0)
//}
//
//fun showSpeechRound(dataState: TrainingState, round: Round){
//    when (round.roundType) {
//        RoundType.WorkUp -> dataState.showSpeechWorkUp.value = true
//        RoundType.WorkOut -> dataState.showSpeechWorkOut.value = true
//        RoundType.WorkDown -> dataState.showSpeechWorkDown.value = true
//    }
//}
//
//fun setCollapsing(dataState: TrainingState, round: Round) {
//    if (round.amount > 0) {
//        when (round.roundType) {
//            RoundType.WorkUp -> dataState.workUpCollapsing.value = !dataState.workUpCollapsing.value
//            RoundType.WorkOut -> dataState.workOutCollapsing.value = !dataState.workOutCollapsing.value
//            RoundType.WorkDown -> dataState.workDownCollapsing.value = !dataState.workDownCollapsing.value
//        }
//    }
//}
//fun getCollapsing(dataState: TrainingState, round: Round): Boolean {
//    return when (round.roundType) {
//        RoundType.WorkUp -> dataState.workUpCollapsing.value
//        RoundType.WorkOut -> dataState.workOutCollapsing.value
//        RoundType.WorkDown -> dataState.workDownCollapsing.value
//    }
//}
//fun nameRound(round: Round): Int {
//    return when(round.roundType){
//        RoundType.WorkUp -> R.string.work_up
//        RoundType.WorkOut -> R.string.work_out
//        RoundType.WorkDown -> R.string.work_down
//    }
//}
////fun amountExercise(dataState: TrainingState, roundType: MutableState<RoundType>) =
////    dataState.training.rounds.find{ it.roundType == roundType.value }?.exercise?.size ?: 0
////
////fun durationRound(dataState: TrainingState, roundType: MutableState<RoundType>): Int{
////    var durationRound = 0.0
////    dataState.training.rounds.find{ it.roundType == roundType.value }?.exercise?.forEach { exercise ->
////        exercise.sets.forEach { set->
////            durationRound += when (set.goal){
////                GoalSet.DURATION-> set.duration
////                GoalSet.COUNT-> (set.reps * set.intervalReps).roundToInt()
////                GoalSet.COUNT_GROUP -> (set.reps * set.intervalReps).roundToInt()
////                GoalSet.DISTANCE -> (set.distance * 6).roundToInt()
////            } + set.timeRest
////        }
////    } ?: 0
////    return ( durationRound / 60).roundToInt()
////}
////fun getIdRound(dataState: TrainingState, roundType: MutableState<RoundType>) =
////    dataState.training.rounds.find { it.roundType == roundType.value }?.idRound ?: 0
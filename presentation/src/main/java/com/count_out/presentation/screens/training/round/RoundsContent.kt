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
import com.count_out.domain.entity.discard
import com.count_out.domain.entity.workout.Part
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen.contourHor2
import com.count_out.presentation.screens.training.PlanEvent
import com.count_out.presentation.screens.training.PlanEvent.ShowBS
import com.count_out.presentation.screens.training.PlanState
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.icons.IconsCollapsing
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable fun Part(dataState: PlanState, part: Part){
//    when(part.roundType){
//        RoundType.WorkUp -> ShowBottomSheetSpeech(dataState, dataState.showBS.workUp,
//            R.string.work_up1, part)
//        RoundType.WorkOut -> ShowBottomSheetSpeech(dataState, dataState.showBS.workOut,
//            R.string.work_out1, part)
//        RoundType.WorkDown -> ShowBottomSheetSpeech(dataState, dataState.showBS.workDown,
//            R.string.work_down1, part)
//    }
    Frame(colorAlpha = 0.8f, contour = contourHor2){
        Column( modifier = Modifier.padding(start = 6.dp, bottom = 4.dp, top = 4.dp)){
            TitleRound(dataState = dataState, part = part)
            ListExercise(dataState = dataState, part = part)
        }
    }
}
@Composable fun TitleRound(dataState: PlanState, part: Part){
    Row( verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 6.dp)){
        IconsCollapsing(
            onClick = { setCollapsing(dataState, part) },
            wrap = getCollapsing(dataState, part) )
        Spacer(modifier = Modifier.width(2.dp))
        Column(modifier = Modifier.weight(1f)) {
            TextApp(
                text = "roundType",//stringResource(id = EnumsTo(part.roundType).string()),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.headlineSmall,)
            TextApp( style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Light,
                text = "${ stringResource(id = R.string.exercises) }: ${part.amount}" +
                        " / ${part.duration.value.discard(2)} ${ stringResource(id = R.string.min)}",) }
        IconsGroup(
            onClickSpeech = { showSpeechRound(dataState, part) },
            onClickAddExercise = {
//                dataState.event(TrainingEvent.CopyExercise(exercise =
//                    object: Exercise{
//                        override val idExercise: Long = (exercise as Exercise).idExercise
//                        override val ringId: Long = (exercise as Exercise).ringId
//                        override val idView: Int = (exercise as Exercise).idView
//                        override val activity: Activity? = (activity as Activity)
//                        override val activityId: Long= (activity as Activity).idActivity
//                        override val speechId: Long = (exercise as Exercise).speechId
//                        override val speeches: List<Speech> = (exercise as Exercise).speeches
//                        override val sets: List<Set> = (exercise as Exercise).sets
//                        override val amountSet: Int = (exercise as Exercise).amountSet
//                        override val duration: Parameter = (exercise as Exercise).duration
//                    }))
//                    ExerciseImplP(roundId = part.idPart)))
            })
        Spacer(modifier = Modifier.width(6.dp))
    }
}
@Composable fun ListExercise(dataState: PlanState, part: Part){
    if (getCollapsing(dataState, part) && part.amount > 0){
//        ListExercises(dataState = dataState, part = part, modifier = Modifier.padding(end = 8.dp))
    }
}
fun showSpeechRound(dataState: PlanState, part: Part){
    dataState.item = part
    dataState.event(ShowBS(dataState.showBS.copy(element = part)))
}

fun setCollapsing(dataState: PlanState, part: Part) {
    if (part.amount > 0) {
        dataState.event(PlanEvent.SetCollapsing(dataState.collapsing.copy(item = part)))
    }
}
fun getCollapsing(dataState: PlanState, part: Part): Boolean {
    return dataState.collapsing.rounds.find { it == part.idPart } != null
}

//package com.count_out.presentation.screens.plan.set
//
//import androidx.compose.animation.animateContentSize
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.IntrinsicSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.material3.MaterialTheme.colorScheme
//import androidx.compose.material3.MaterialTheme.shapes
//import androidx.compose.material3.MaterialTheme.typography
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import com.count_out.domain.entity.enums.Goal
//import com.count_out.domain.entity.enums.Units
//import com.count_out.domain.entity.toIntMy
//import com.count_out.domain.entity.workout.Parameter
//import com.count_out.domain.entity.workout.Set
//import com.count_out.domain.entity.workout.Set.Companion.copy
//import com.count_out.presentation.R
//import com.count_out.presentation.models.Dimen
//import com.count_out.presentation.models.TypeKeyboard
//import com.count_out.presentation.screens.plan.PlanEvent
//import com.count_out.presentation.screens.plan.PlanState
//import com.count_out.presentation.view_element.TextApp
//import com.count_out.presentation.view_element.TextFieldApp
//
//val interval_between_pole = 8.dp
//
//@Composable fun SetBody(dataState: PlanState, set: Set){
//    when (set.goal){
//        Goal.Distance -> Distance( dataState, set)
//        Goal.Duration -> Duration( dataState, set)
//        Goal.Count -> Count( dataState, set)
//        Goal.CountGroup -> {}
//    }
//}
//@Composable fun Distance(dataState: PlanState, set: Set) {
//    CarcassCardDistance(
//        { DistancePole(dataState, set) },
//        { RestPole(dataState, set) }
//    )
//}
//@Composable fun Duration(dataState: PlanState, set: Set) {
//    CarcassCardDuration(
//        { DurationPole(dataState, set) },
//        { WeightPole(dataState, set) },
//        { RestPole(dataState, set) }
//    )
//}
//@Composable fun Count(dataState: PlanState, set: Set) {
//    CarcassCardCount(
//        { IntervalPole(dataState, set) },
//        { WeightPole(dataState, set) },
//        { RestPole(dataState, set) },
//        { CountFieldText(dataState, set) },
//        { CountGroupFieldText(dataState, set) },
//    )
//}
//@Composable fun DistancePole(dataState: PlanState, set: Set){
//    CarcassPoleInputAndUnits(
//        headId = R.string.distance,
//        parameter = set.distance,
//        onChange = { param-> dataState.event( PlanEvent.UpdateSet(set.copy(distance = param)))},
//    )
//}
//@Composable fun DurationPole(dataState: PlanState, set: Set){   //B7B7B7
//    CarcassPoleInputAndUnits(
//        headId = R.string.duration,
//        parameter = set.duration,
//        onChange = { param-> dataState.event(PlanEvent.UpdateSet( set.copy(duration = param)))},
//    )
//}
//@Composable fun IntervalPole(dataState: PlanState, set: Set){   //B7B7B7
//    CarcassPoleInputAndUnits(
//        headId = R.string.interval,
//        enableClick = false,
//        parameter = Parameter.fill(set.intervalReps, Units.S),
//        onChange = { param-> dataState.event(PlanEvent.UpdateSet( set.copy(intervalReps = param.value)))},
//    )
//}
//@Composable fun WeightPole(dataState: PlanState, set: Set){   //B7B7B7
//    CarcassPoleInputAndUnits(
//        headId = R.string.weight,
//        parameter = set.weight,
//        onChange = { param-> dataState.event(PlanEvent.UpdateSet( set.copy(weight = param)))},
//    )
//}
//@Composable fun RestPole(dataState: PlanState, set: Set){
//    CarcassPoleInputAndUnits(
//        headId = R.string.rest_time,
//        parameter = set.rest,
//        onChange = { param-> dataState.event(PlanEvent.UpdateSet( set.copy(rest = param)))},
//    )
//}
//@Composable fun CountFieldText(dataState: PlanState, set: Set){
//    PoleInput(
//        headId = R.string.counts,
//        typeKey = TypeKeyboard.DIGIT,
//        beginValueEmpty = true,
//        placeholder = "${ set.reps }",
//        modifier = Modifier.width(IntrinsicSize.Min),
//        onChangeValue = { dataState.event(PlanEvent.UpdateSet( set.copy(reps = it.toIntMy())))})
//}
//@Composable fun CountGroupFieldText(dataState: PlanState, set: Set){
//    PoleInput(
//        headId = R.string.counts_by_group_add,
//        placeholder = set.groupCount,
//        onChangeValue ={ dataState.event(PlanEvent.UpdateSet( set.copy(groupCount = it))) })
//}
//@Composable fun PoleInput(
//    headId: Int,
//    placeholder: String,
//    modifier: Modifier = Modifier,
//    typeKey: TypeKeyboard = TypeKeyboard.TEXT,
//    beginValueEmpty: Boolean = false,
//    onChangeValue: (String)-> Unit,
//){
//    Column (horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = modifier
//            .animateContentSize()
//            .padding(start = interval_between_pole)
//            .background(color = colorScheme.onSecondary, shape = shapes.small)
//            .padding(top = 2.dp, bottom = 6.dp, start = 4.dp, end = 4.dp)
//    ) {
//        TextFieldApp(
//            modifier = Modifier.fillMaxWidth(),
//            edit = true,
//            beginValueEmpty = beginValueEmpty,
//            typeKeyboard = typeKey,
//            contentAlignment = Alignment.Center,
//            textStyle = typography.titleLarge.copy(textAlign = TextAlign.Center),
//            onChangeFocus = { onChangeValue(it) },
//            placeholder = placeholder,
//        )
//        TextApp(
//            text = stringResource(headId), textAlign = TextAlign.Center, style = Dimen.typeLabel(),
//            maxLines = 2
//        )
//    }
//}
//

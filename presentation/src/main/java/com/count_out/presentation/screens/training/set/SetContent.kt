package com.count_out.presentation.screens.training.set

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.Units
import com.count_out.domain.entity.enums.Zone
import com.count_out.domain.entity.toDoubleMy
import com.count_out.domain.entity.toIntMy
import com.count_out.domain.entity.workout.Set
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen.contourAll1
import com.count_out.presentation.models.Dimen.contourBot1
import com.count_out.presentation.models.ParameterImpl
import com.count_out.presentation.models.SetImpl
import com.count_out.presentation.models.TypeKeyboard
import com.count_out.presentation.models.alumBodyLarge
import com.count_out.presentation.models.alumBodyMedium
import com.count_out.presentation.models.alumBodySmall
import com.count_out.presentation.screens.prime.Action
import com.count_out.presentation.screens.training.TrainingEvent
import com.count_out.presentation.screens.training.TrainingState
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TextFieldApp
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.custom_view.IconQ
import com.count_out.presentation.view_element.icons.IconsCollapsing
import com.count_out.presentation.view_element.icons.IconsGroup


val interval_between_pole = 4.dp

@Composable fun SetContent(dataState: TrainingState, action: Action, set: SetImpl){
    AnimatedVisibility(modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp), visible = true) {
        Frame(colorAlpha = 0.4f, contour = contourAll1) {
            Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)) {
                if (set.positions.second == 1 &&
                    dataState.listCollapsingSet.value.find { it == set.idSet } == null)
                    FirstLine(dataState, action, set )
                else {
                    TaskSwitch( dataState, action, set )
                    BodySet( dataState, action, set )
                    ZonePulseSwitch( dataState, action, set )
                }
            }
        }
    }
}
@Composable fun FirstLine(dataState: TrainingState, action: Action, set: SetImpl) {
    val setInfo = when (set.goal) {
        Goal.Distance -> viewDistance(set) + stringResource(id = set.distance.unit.id )
        Goal.Duration -> "${set.duration.value} ${stringResource(id = set.duration.unit.id)}"
        Goal.Count -> "${stringResource(id = R.string.counts)}: ${set.reps}"
        Goal.CountGroup -> "${set.reps} ${stringResource(id = R.string.counts)}"
    }
    Row (verticalAlignment = Alignment.CenterVertically){
        IconsCollapsing(
            onClick = { setCollapsing(dataState, set) },
            wrap = dataState.listCollapsingSet.value.find { it == set.idSet } != null )
        TextApp(
            text = "${(set.positions.first + 1)}" ,
            style = typography.titleMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 4.dp, end =16.dp))
        Spacer(modifier = Modifier.weight(1f))
        TextApp(
            text = setInfo,
            style = typography.bodySmall,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Start,)
        IconsGroup(
            onClickCopy = {
                action.ex(TrainingEvent.CopySet(set))},
            onClickDelete = {
                action.ex(TrainingEvent.DeleteSet(set))},
            onClickSpeech = {
                dataState.set = set
                dataState.showSpeechSet.value = true},
        )
    }
}

@Composable fun BodySet(dataState: TrainingState, action: Action, set: SetImpl){
    when (set.goal){
        Goal.Distance -> Distance( dataState, action, set)
        Goal.Duration -> Duration( dataState, action, set)
        Goal.Count -> Count( dataState, action, set)
        Goal.CountGroup -> {}
    }
}
@Composable fun Distance(dataState: TrainingState, action: Action, set: SetImpl) {
    Row( horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)){
        DistancePole(dataState, action, set, Modifier.weight(0.8f))
        RestPole(dataState, action, set, Modifier.weight(1f))
    }
}
@Composable fun Duration(dataState: TrainingState, action: Action, set: SetImpl) {
    Row( horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)){
        DurationPole(dataState, action, set, Modifier.weight(1f))
        WeightPole(dataState, action, set, Modifier.weight(0.9f))
        RestPole(dataState, action, set, Modifier.weight(1f))
    }
}
@Composable fun Count(dataState: TrainingState, action: Action, set: SetImpl) {
    Row( horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)){
        IntervalPole(dataState, action, set, Modifier.weight(1f))
        WeightPole(dataState, action, set, Modifier.weight(0.8f))
        RestPole(dataState, action, set, Modifier.weight(1f))
    }
    Row( horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)){
        CountFieldText(dataState, action, set)
        CountGroupFieldText(dataState, action, set)
    }
}
@Composable fun DistancePole(dataState: TrainingState, action: Action, set: SetImpl, modifier: Modifier = Modifier,){   //B7B7B7
    PoleInputWithUnit(
        unitId1 = R.string.m,
        unitId2 = R.string.km,
        headId = R.string.distance,
        term = set.distance.unit == Units.MT,
        placeholder = "${ set.distance.value / (if (set.distance.unit == Units.MT) 1 else 1000) }",
        modifier = modifier,
        typeKey = TypeKeyboard.DIGIT,
        onChangeValue = { action.ex(
            TrainingEvent.ChangeSet( set.copy(
                    distance = ParameterImpl(
                        value = (if (set.distance.unit == Units.MT) 1.0 else 1000.0) * it.toDoubleMy(),
                        unit = set.distance.unit
                    )
                ))
            ) },
        onChangeUnit = { action.ex(
            TrainingEvent.ChangeSet( set.copy(
                    distance = ParameterImpl(
                        value = (if (set.distance.unit == Units.MT) 1.0 else 1000.0) * set.distance.value,
                        unit = if (set.distance.unit == Units.MT) Units.KM else Units.MT
                    )
                ))
            ) }
    )
}
@Composable fun DurationPole(dataState: TrainingState, action: Action, set: SetImpl, modifier: Modifier = Modifier){   //B7B7B7
    PoleInputWithUnit(
        unitId1 = R.string.sec,
        unitId2 = R.string.min,
        headId = R.string.duration,
        term = set.duration.unit == Units.S,
        placeholder = "${ set.duration.value / (if (set.duration.unit == Units.M) 60 else 1)}",
        modifier = modifier,
        typeKey = TypeKeyboard.DIGIT,
        onChangeValue = { action.ex(
            TrainingEvent.ChangeSet( set.copy(
                    distance = ParameterImpl(
                        value = (if (set.duration.unit == Units.M) 60.0 else 1.0) * it.toDoubleMy(),
                        unit = set.distance.unit
                    )
                ))
            )  },
        onChangeUnit = {action.ex(
            TrainingEvent.ChangeSet( set.copy(
                    distance = ParameterImpl(
                        value = (if (set.duration.unit == Units.M) 60.0 else 1.0) * set.duration.value,
                        unit = if (set.duration.unit == Units.M) Units.S else Units.M
                    )
                ))
            ) }
    )
}
@Composable fun IntervalPole(dataState: TrainingState, action: Action, set: SetImpl, modifier: Modifier = Modifier){   //B7B7B7
    PoleInputWithUnit(
        unitId1 = R.string.sec,
        headId = R.string.interval,
        term = true,
        placeholder = "${ set.intervalReps }",
        modifier = modifier,
        typeKey = TypeKeyboard.DIGIT,
        onChangeValue = { action.ex(TrainingEvent.ChangeSet( set.copy(intervalReps = it.toDoubleMy()))) },
        onChangeUnit = { }
    )
}
@Composable fun WeightPole(dataState: TrainingState, action: Action, set: SetImpl, modifier: Modifier = Modifier){   //B7B7B7
    PoleInputWithUnit(
        unitId1 = R.string.gr,
        unitId2 = R.string.kg,
        headId = R.string.weight,
        term = set.weight.unit == Units.GR,
        placeholder = "${ set.weight.value / (if (set.weight.unit == Units.GR) 1 else 1000) }",
        modifier = modifier,
        typeKey = TypeKeyboard.DIGIT,
        onChangeValue = { action.ex(
            TrainingEvent.ChangeSet(set.copy(
                    weight = ParameterImpl(
                        value = (if (set.weight.unit == Units.GR) 1 else 1000) * it.toDoubleMy(),
                        unit = set.weight.unit))))
        },
        onChangeUnit = { action.ex(
            TrainingEvent.ChangeSet( set.copy(
                    weight = ParameterImpl(
                        value = (if (set.weight.unit == Units.GR) 1 else 1000) * set.weight.value,
                        unit = if (set.weight.unit == Units.GR) Units.KG else Units.GR)
            )))
        }
    )
}
@Composable fun RestPole(dataState: TrainingState, action: Action, set: SetImpl, modifier: Modifier = Modifier){
    PoleInputWithUnit(
        unitId1 = R.string.sec,
        unitId2 = R.string.min,
        headId = R.string.rest_time,
        term = set.rest.unit == Units.S,
        placeholder =  "${ set.rest.value / (if (set.rest.unit == Units.S) 1 else 60) }",
        modifier = modifier,
        typeKey = TypeKeyboard.DIGIT,
        onChangeValue = { action.ex(
            TrainingEvent.ChangeSet( set.copy(
                    rest = ParameterImpl(
                        value = it.toDoubleMy() / (if (set.rest.unit == Units.S) 1.0 else 60.0),
                        unit = set.rest.unit)
            ))) },
        onChangeUnit = { action.ex(
            TrainingEvent.ChangeSet( set.copy(
                    rest = ParameterImpl(
                        value = set.rest.value / (if (set.rest.unit == Units.S) 1.0 else 60.0),
                        unit = if (set.rest.unit == Units.S) Units.M else Units.S)
            )))}
    )
}

@Composable fun CountFieldText(dataState: TrainingState, action: Action, set: SetImpl){
    PoleInput(
        headId = R.string.counts, typeKey = TypeKeyboard.DIGIT, placeholder = "${ set.reps }",
        modifier = Modifier.width(IntrinsicSize.Min),
        onChangeValue = { action.ex(TrainingEvent.ChangeSet( set.copy(reps = it.toIntMy())))})

}
@Composable fun CountGroupFieldText(dataState: TrainingState, action: Action, set: SetImpl){
    PoleInput(
        headId = R.string.counts_by_group_add,
        placeholder = set.groupCount,
        onChangeValue ={ action.ex(TrainingEvent.ChangeSet( set.copy(groupCount = it))) })
}

@Composable fun TaskSwitch(dataState: TrainingState, action: Action, set: SetImpl){
    Row(verticalAlignment = Alignment.CenterVertically){
        if (set.positions.second == 1){
            IconsCollapsing(
                onClick = { setCollapsing(dataState, set) },
                wrap = dataState.listCollapsingSet.value.find { it == set.idSet } != null )
        }
        TextApp(
            text = "${(set.positions.first + 1)}" ,
            style = typography.displayMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 4.dp, end =16.dp))
        Spacer(modifier = Modifier.weight(1f))
        IconQ.Duration(selected = set.goal == Goal.Duration,
            onClick = { action.ex(TrainingEvent.ChangeSet( set.copy(goal = Goal.Duration)))},)
        Spacer(modifier = Modifier.width(24.dp))
        IconQ.Distance(selected = set.goal == Goal.Distance,
            onClick = { action.ex(TrainingEvent.ChangeSet( set.copy(goal = Goal.Distance))) },)
        Spacer(modifier = Modifier.width(24.dp))
        IconQ.Count(selected = set.goal == Goal.Count,
            onClick = { action.ex(TrainingEvent.ChangeSet( set.copy(goal = Goal.Count))) },)
        Spacer(modifier = Modifier.width(24.dp))
        Spacer(modifier = Modifier.weight(1f))
        IconsGroup(
            onClickCopy = { action.ex(TrainingEvent.CopySet( set )) },
            onClickDelete = {  action.ex(
                TrainingEvent.DeleteSet(set)) },
            onClickSpeech = {
                dataState.set = set
                dataState.showSpeechSet.value = true},)
    }
}
@Composable fun ZonePulseSwitch(dataState: TrainingState, action: Action, set: SetImpl){
    Row(verticalAlignment = Alignment.CenterVertically){
        Spacer(modifier = Modifier.weight(1f))
        ButtonSwitchPulse(selected = set.intensity == Zone.Low, idString = R.string.zone1,
            onClick = { action.ex(TrainingEvent.ChangeSet(set.copy(intensity = Zone.Low)))})
        ButtonSwitchPulse(selected = set.intensity == Zone.Min, idString = R.string.zone2,
            onClick = {action.ex(TrainingEvent.ChangeSet( set.copy(intensity = Zone.Min)))})
        ButtonSwitchPulse(selected = set.intensity == Zone.Medium, idString = R.string.zone3,
            onClick = {action.ex(TrainingEvent.ChangeSet(set.copy(intensity = Zone.Medium)))})
        ButtonSwitchPulse(selected = set.intensity == Zone.High, idString = R.string.zone4,
            onClick = {action.ex(TrainingEvent.ChangeSet( set.copy(intensity = Zone.High)))})
        ButtonSwitchPulse(selected = set.intensity == Zone.Max, idString = R.string.zone5,
            onClick = {action.ex(TrainingEvent.ChangeSet( set.copy(intensity = Zone.Max)))})
        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable fun ButtonSwitchPulse(selected: Boolean, onClick: () -> Unit, idString: Int,){
    ButtonSwitch(selected = selected, idString = idString, onClick = onClick,
        style = alumBodyMedium, modifier = Modifier.width(50.dp))
}

@Composable fun ButtonSwitch(
    color: Color = colorScheme.outline,
    background: Color = colorScheme.background,
    selected: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
        .width(35.dp)
        .padding(vertical = 2.dp, horizontal = 2.dp),
    idString: Int,
    style: TextStyle = typography.bodySmall)
{
    Frame (colorBorder = color, contour = if (selected) contourAll1 else contourBot1,
        background = background,) {
        TextApp( modifier = modifier.clickable { onClick() },
            fontWeight =  if (selected) FontWeight.Bold else FontWeight.Normal,
            style = style,
            text = stringResource(idString))
    }
}

@Composable fun PoleInputWithUnit(
    unitId1: Int,
    unitId2: Int = R.string.no,
    headId: Int,
    term: Boolean,
    placeholder: String,
    modifier: Modifier = Modifier,
    typeKey: TypeKeyboard = TypeKeyboard.TEXT,
    onChangeValue: (String)-> Unit,
    onChangeUnit: ()-> Unit,
){
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .animateContentSize()
            .padding(start = interval_between_pole)
            .background(color = colorScheme.onSecondary, shape = shapes.small)
            .padding(top = 2.dp, bottom = 6.dp, start = 4.dp, end = 4.dp)
    ) {
        TextApp(text = stringResource(headId), textAlign = TextAlign.Center, style = alumBodySmall)
        Row (verticalAlignment = Alignment.Top,) {
            TextFieldApp(
                modifier = Modifier.weight(1f),
                edit = true,
                beginValueZero = true,
                typeKeyboard = typeKey,
                contentAlignment = Alignment.Center,
                textStyle = typography.bodyLarge.copy(textAlign = TextAlign.Center),
                onChangeValue = { onChangeValue(it) },
                placeholder = placeholder,
            )
            Text(
                buildAnnotatedString {
                    append("(")
                    withStyle(style = SpanStyle(
                        fontWeight = if (term) FontWeight.ExtraBold else FontWeight.Normal))
                    { append(stringResource(unitId1)) }
                    if (unitId2 != R.string.no) {
                        append("/")
                        withStyle(style = SpanStyle(
                            fontWeight = if (term) FontWeight.Normal else FontWeight.ExtraBold))
                        { append(stringResource(unitId2)) }
                    }
                    append(")")},
                modifier = Modifier.padding(start = 2.dp, top = 2.dp).clickable { onChangeUnit() },
                style = alumBodyLarge,
                color = colorScheme.outline
            )
        }
    }
}

@Composable fun PoleInput(
    headId: Int,
    placeholder: String,
    modifier: Modifier = Modifier,
    typeKey: TypeKeyboard = TypeKeyboard.TEXT,
    onChangeValue: (String)-> Unit,
){
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .animateContentSize()
            .padding(start = interval_between_pole)
            .background(color = colorScheme.onSecondary, shape = shapes.small)
            .padding(top = 2.dp, bottom = 6.dp, start = 4.dp, end = 4.dp)
    ) {
        Row (verticalAlignment = Alignment.Top, modifier =Modifier) {
            TextFieldApp(
                modifier = Modifier.weight(1f),
                edit = true,
                typeKeyboard = typeKey,
                contentAlignment = Alignment.Center,
                textStyle = typography.bodyLarge.copy(textAlign = TextAlign.Center),
                onChangeValue = { onChangeValue(it) },
                placeholder = placeholder,
            )
        }
        TextApp(
            text = stringResource(headId), textAlign = TextAlign.Center, style = alumBodySmall,
            modifier = TODO()
        )
    }
}

fun viewDistance(set: Set):String {
    return "${ set.distance.value / (if (set.distance.unit == Units.KM) 1000 else 1) }"
}
fun setCollapsing(dataState: TrainingState, set: Set) {
    val listCollapsingSet = dataState.listCollapsingSet.value.toMutableList()
    val itemList = listCollapsingSet.find { it == set.idSet }
    if ( itemList != null) {
        listCollapsingSet.remove(itemList)
        dataState.listCollapsingSet.value = listCollapsingSet
    } else {
        listCollapsingSet.add(set.idSet)
        dataState.listCollapsingSet.value = listCollapsingSet
    }
}

//        TaskButtonSwitch(selected = set.goal == GoalSet.DISTANCE, idString = R.string.distance,
//            onClick = {uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.DISTANCE))},)
//        TaskButtonSwitch(selected = set.goal == GoalSet.DURATION, idString = R.string.duration,
//            onClick = {uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.DURATION))})
//        TaskButtonSwitch(selected = set.goal == GoalSet.COUNT, idString = R.string.count,
//            onClick = {uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.COUNT))})
//@Composable fun TaskButtonSwitch(selected: Boolean, onClick: () -> Unit, idString: Int,){
//    ButtonSwitch(selected = selected, idString = idString, onClick = onClick,
//        modifier = Modifier.width(90.dp), style = typography.bodyMedium )
//}
//#####################################Old#########################################################
//@Composable fun AdditionalInformation1(uiState: TrainingScreenState, set: Set, amountSet: Int){
//    val visibleLazy = (uiState.listCollapsingSet.value.find { it == set.idSet } != null) || (amountSet == 1)
//    Column(modifier = Modifier.testTag("1"))
//    {
//        AnimatedVisibility(modifier = Modifier.padding(0.dp), visible = visibleLazy){
//            Row(modifier = Modifier
//                .testTag("1")
//                .padding(start = 8.dp)) {
//                Column(modifier = Modifier.weight(1f)) {
//                    Spacer(modifier = Modifier.height(8.dp))
//                    TextApp(text = stringResource(id = R.string.task_in_approach), style = interReg14)
//                    Spacer(modifier = Modifier.height(8.dp))
//                    SwitchGoal(uiState, set)
//                    Spacer(modifier = Modifier.height(8.dp))
//                    TextStringAndField(
//                        placeholder = set.timeRest.toString(),
//                        onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(timeRest = it.toIntMy())) },
//                        editing = visibleLazy,
//                        text = stringResource(id = R.string.time_to_rest) + " (" + stringResource(id = R.string.sec) + "): ",)
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier
//                        .width(65.dp)
//                        .padding(top = 12.dp),
//                    content = { SelectZone(uiState, set) })
//            }
//        }
//    }
//}
//@Composable fun SwitchGoal(uiState: TrainingScreenState, set: Set) {
//    val state = remember { mutableIntStateOf( set.goal.id) }
//    Column( Modifier.selectableGroup())
//    {
//        Spacer(modifier = Modifier.height(6.dp))
//        RadioButtonApp(
//            radioButtonId = 1,
//            state = state.intValue,
//            onClick = {
//                state.intValue = 1
//                uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.DISTANCE)) },
//            contextRight = {
//                TextStringAndField(
//                    placeholder = set.distance.toString(),
//                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(distance = it.toDoubleMy())) },
//                    editing = true,
//                    visibleField = state.intValue == 1,
//                    text = "${stringResource(id = R.string.distance)} (${stringResource(id = R.string.km)}):"
//                )},
//            contextBottom = { },
//        )
//        Spacer(modifier = Modifier.height(6.dp))
//        RadioButtonApp(
//            radioButtonId = 2,
//            state = state.intValue,
//            onClick = {
//                state.intValue = 2
//                uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.DURATION)) },
//            contextRight = {
//                TextStringAndField(
//                    placeholder = set.duration.toString(),
//                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(duration = it.toIntMy())) },
//                    editing = true,
//                    visibleField = state.intValue == 2,
//                    text = "${stringResource(id = R.string.duration)} (${stringResource(id = R.string.min)})")
//            },
//            contextBottom = { RadioButtonDurationBottom(uiState = uiState, set = set, visible = state.intValue == 2)},
//        )
//        Spacer(modifier = Modifier.height(6.dp))
//        RadioButtonApp(
//            radioButtonId = 3,
//            state = state.intValue,
//            onClick = {
//                state.intValue = 3
//                uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.COUNT)) },
//            contextRight = {
//                TextStringAndField(
//                    placeholder = set.reps.toString(),
//                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(reps = it.toIntMy())) },
//                    editing = true,
//                    visibleField = state.intValue == 3,
//                    text = stringResource(id = R.string.quantity_reps))
//            },
//            contextBottom = { RadioButtonCountBottom(uiState = uiState, set = set, visible = state.intValue == 3)},
//        )
//        Spacer(modifier = Modifier.height(6.dp))
//        RadioButtonApp(
//            radioButtonId = 4,
//            state = state.intValue,
//            onClick = {
//                state.intValue = 4
//                uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.COUNT_GROUP)) },
//            contextRight = {
//                TextStringAndField(
//                    placeholder = set.reps.toString(),
//                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(reps = it.toIntMy())) },
//                    editing = true,
//                    visibleField = state.intValue == 4,
//                    text = stringResource(id = R.string.quantity_group_reps)
//                )},
//            contextBottom = {
//                RadioButtonCountGroupBottom(uiState = uiState, set = set, visible = state.intValue == 4)},
//        )
//    }
//}
//@Composable fun RadioButtonDurationBottom(uiState: TrainingScreenState, set: Set, visible: Boolean) {
//    RadioButtonBottom(uiState = uiState,set = set,visible = visible,
//        text = "${stringResource(id = R.string.weight)} (${stringResource(id = R.string.kg)}):")
//}
//@Composable fun RadioButtonCountBottom(uiState: TrainingScreenState, set: Set, visible: Boolean) {
//    AnimatedVisibility(modifier = Modifier.padding(top = 0.dp), visible = visible
//    ){
//        Column(modifier = Modifier.padding(start = 12.dp)){
//            TextStringAndField(
//                placeholder = set.intervalReps.toString(),
//                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intervalReps = it.toDoubleMy())) },
//                editing = true,
//                text = stringResource(id = R.string.time_between_counts) + " (" +
//                        stringResource(id = R.string.sec) + "): ")
//            Spacer(modifier = Modifier.height(0.dp))
//            TextStringAndField(
//                placeholder = set.intervalDown.toString(),
//                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intervalDown = it.toIntMy())) },
//                editing = true,
//                text = stringResource(id = R.string.slowing_down_counts) )
//        }
//    }
//}
//@Composable fun RadioButtonCountGroupBottom(uiState: TrainingScreenState, set: Set, visible: Boolean) {
//    AnimatedVisibility(modifier = Modifier.padding(top = 8.dp, start = 12.dp), visible = visible
//    ){
//        Column{
//            TextStringAndField(
//                placeholder = set.intervalReps.toString(),
//                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intervalReps = it.toDoubleMy())) },
//                editing = true,
//                text = stringResource(id =
//                R.string.time_between_counts) + " (" + stringResource(id = R.string.sec) + "): ")
//            Spacer(modifier = Modifier.height(8.dp))
//            TextStringAndField(
//                placeholder = set.intervalDown.toString(),
//                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intervalDown = it.toIntMy())) },
//                editing = true,
//                text = stringResource(id = R.string.slowing_down_counts) )
//            Spacer(modifier = Modifier.height(8.dp))
//            TextAppLines(
//                text = stringResource(id = R.string.counts_by_group_add),
//                style = interReg12,
//                modifier = Modifier.padding(vertical = 2.dp))
//            TextFieldApp(
//                modifier = Modifier.fillMaxWidth(),
//                placeholder = set.groupCount,
//                typeKeyboard = TypeKeyboard.TEXT,
//                textStyle = interLight12,
//                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(groupCount = it)) })
//        }
//    }
//}
//@Composable fun RadioButtonBottom(uiState: TrainingScreenState, set: Set, visible: Boolean, text: String) {
//    AnimatedVisibility( visible = visible, modifier = Modifier.padding(top = 8.dp, start = 12.dp)
//    ){
//        TextStringAndField(
//            placeholder = set.weight.toString(),
//            onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(weight = it.toIntMy())) },
//            editing = true,
//            text = text
//        )
//    }
//}

//@Composable fun SelectZone(uiState: TrainingScreenState, set: Set){
//    TextApp(
//        style = interLight12,
//        text = stringResource(id = R.string.zone) )
//    ChipZone(Zone.EXTRA_SLOW, uiState, set)
//    ChipZone(Zone.SLOW, uiState, set)
//    ChipZone(Zone.MEDIUM, uiState, set)
//    ChipZone(Zone.HIGH, uiState, set)
//    ChipZone(Zone.MAX, uiState, set)
//}
//@Composable fun ChipZone(zone: Zone, uiState: TrainingScreenState, set: Set){
//    ChipMy(text = ".. " + zone.maxPulse.toString(),
//        selected = set.intensity == zone,
//        onClick = { uiState.onChangeSet ((set as SetDB).copy(intensity = zone)) },)
//}
//
//@Composable fun ChipMy(text: String, selected: Boolean, onClick: ()->Unit){
//    val delta = 0.1f
//    val containerColor = CardDefaults.cardColors().containerColor
//    val containerColorSelected = Color(
//        red = containerColor.red + if (containerColor.red < (1.0 - delta)) delta else 0.0f,
//        blue = containerColor.blue + if (containerColor.blue < (1.0 - delta)) delta else 0.0f,
//        green = containerColor.green + if (containerColor.green < (1.0 - delta)) delta else 0.0f,
//        alpha = containerColor.alpha
//    )
//    Card(
//        shape = shapes.extraSmall,
//        elevation = elevationTraining(),
//        colors = CardDefaults.cardColors(
//            containerColor = if (selected) containerColorSelected else containerColor),
//        modifier = Modifier
//            .padding(horizontal = 6.dp, vertical = 4.dp)
//            .clickable { onClick() }
//    ) {
//        TextApp(text = text,
//            style = interLight12 ,
//            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
//    }
//}

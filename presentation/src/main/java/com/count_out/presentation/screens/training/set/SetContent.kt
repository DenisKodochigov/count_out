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
import com.count_out.domain.entity.discard
import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.Units
import com.count_out.domain.entity.enums.Zone
import com.count_out.domain.entity.toDoubleMy
import com.count_out.domain.entity.toIntMy
import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.workout.Set
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen.contourAll1
import com.count_out.presentation.models.Dimen.contourBot1
import com.count_out.presentation.models.ParameterImplP
import com.count_out.presentation.models.SetImplP
import com.count_out.presentation.models.TypeKeyboard
import com.count_out.presentation.models.alumBodyLarge
import com.count_out.presentation.models.alumBodyMedium
import com.count_out.presentation.models.alumBodySmall
import com.count_out.presentation.screens.prime.Action
import com.count_out.presentation.screens.training.TrainingEvent
import com.count_out.presentation.screens.training.TrainingEvent.ShowBS
import com.count_out.presentation.screens.training.TrainingState
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TextFieldApp
import com.count_out.presentation.view_element.bottom_sheet.BottomSheetSpeech
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.custom_view.IconQ
import com.count_out.presentation.view_element.icons.IconsCollapsing
import com.count_out.presentation.view_element.icons.IconsGroup

val interval_between_pole = 4.dp

@Composable fun SetContent(dataState: TrainingState, action: Action, set: SetImplP){

    if (dataState.showBS.set) {
        dataState.nameSection = stringResource(id = R.string.set2)
        dataState.item = dataState.set
        dataState.onDismissSpeech =
            { action.ex(ShowBS(dataState.showBS.copy(element = dataState.item))) }
        dataState.onConfirmationSpeech = {speech, item->
            action.ex(TrainingEvent.UpdateSpeech(speech))
            action.ex(ShowBS(dataState.showBS.copy(element = dataState.item)))
        }
        BottomSheetSpeech(dataState)
    }
    AnimatedVisibility(modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp), visible = true) {
        Frame(colorAlpha = 0.4f, contour = contourAll1) {
            Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)) {
                if (set.positions.second == 1 && dataState.collapsing.sets.find { it == set.idSet } == null)
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
@Composable fun FirstLine(dataState: TrainingState, action: Action, set: SetImplP) {
    val setInfo = when (set.goal) {
        Goal.Distance -> viewDistance(set) + stringResource(id = set.distance.unit.id )
        Goal.Duration -> "${set.duration.value} ${stringResource(id = set.duration.unit.id)}"
        Goal.Count -> "${stringResource(id = R.string.counts)}: ${set.reps}"
        Goal.CountGroup -> "${set.reps} ${stringResource(id = R.string.counts)}"
    }
    Row (verticalAlignment = Alignment.CenterVertically){
        IconsCollapsing(
            onClick = { action.ex(TrainingEvent.SetCollapsing(dataState.collapsing.copy(item = set))) },
            wrap = dataState.collapsing.sets.find { it == set.idSet } != null )
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
            onClickCopy = { action.ex(TrainingEvent.CopySet(set))},
            onClickDelete = { action.ex(TrainingEvent.DeleteSet(set))},
            onClickSpeech = {
                dataState.set = set
                action.ex(ShowBS(dataState.showBS.copy(element = set)))      },
        )
    }
}

@Composable fun BodySet(dataState: TrainingState, action: Action, set: SetImplP){
    when (set.goal){
        Goal.Distance -> Distance( dataState, action, set)
        Goal.Duration -> Duration( dataState, action, set)
        Goal.Count -> Count( dataState, action, set)
        Goal.CountGroup -> {}
    }
}
@Composable fun Distance(dataState: TrainingState, action: Action, set: SetImplP) {
    Row( horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)){
        DistancePole(dataState, action, set, Modifier.weight(1f))
        RestPole(dataState, action, set, Modifier.weight(1f))
    }
}
@Composable fun Duration(dataState: TrainingState, action: Action, set: SetImplP) {
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
@Composable fun Count(dataState: TrainingState, action: Action, set: SetImplP) {
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
@Composable fun DistancePole(dataState: TrainingState, action: Action, set: SetImplP, modifier: Modifier = Modifier,){   //B7B7B7
    PoleInputWithUnit(
        unitId1 = R.string.m,
        unitId2 = R.string.km,
        headId = R.string.distance,
        term = set.distance.unit == Units.MT,
        placeholder = "${ set.distance.value}",
        modifier = modifier,
        typeKey = TypeKeyboard.DIGIT,
        onChangeValue = { action.ex(
            TrainingEvent.UpdateSet( set.copy(
                    distance = ParameterImplP(
                        value = it.toDoubleMy(),
                        unit = set.distance.unit
                    )
                ))
            ) },
        onChangeUnit = { action.ex(
            TrainingEvent.UpdateSet( set.copy(
                    distance = ParameterImplP(
                        value = bringingDist(set.distance),
                        unit = if (set.distance.unit == Units.MT) Units.KM else Units.MT
                    )
                ))
            ) }
    )
}
@Composable fun DurationPole(dataState: TrainingState, action: Action, set: SetImplP, modifier: Modifier = Modifier){   //B7B7B7
    PoleInputWithUnit(
        unitId1 = R.string.sec,
        unitId2 = R.string.min,
        headId = R.string.duration,
        term = set.duration.unit == Units.S,
        placeholder = "${set.duration.value}",
        modifier = modifier,
        typeKey = TypeKeyboard.DIGIT,
        onChangeValue = { action.ex(
            TrainingEvent.UpdateSet( set.copy(
                    distance = ParameterImplP(value = it.toDoubleMy(), unit = set.distance.unit))))
        },
        onChangeUnit = {action.ex(
            TrainingEvent.UpdateSet( set.copy(
                    distance = ParameterImplP(value = bringingTime(set.duration),
                        unit = if (set.duration.unit == Units.M) Units.S else Units.M)
                ))
            ) }
    )
}
@Composable fun IntervalPole(dataState: TrainingState, action: Action, set: SetImplP, modifier: Modifier = Modifier){   //B7B7B7
    PoleInputWithUnit(
        unitId1 = R.string.sec,
        headId = R.string.interval,
        term = true,
        placeholder = "${ set.intervalReps }",
        modifier = modifier,
        typeKey = TypeKeyboard.DIGIT,
        onChangeValue = { action.ex(TrainingEvent.UpdateSet( set.copy(intervalReps = it.toDoubleMy()))) },
        onChangeUnit = { }
    )
}
@Composable fun WeightPole(dataState: TrainingState, action: Action, set: SetImplP, modifier: Modifier = Modifier){   //B7B7B7
    PoleInputWithUnit(
        unitId1 = R.string.gr,
        unitId2 = R.string.kg,
        headId = R.string.weight,
        term = set.weight.unit == Units.GR,
        placeholder = "${set.weight.value}",
        modifier = modifier,
        typeKey = TypeKeyboard.DIGIT,
        onChangeValue = { action.ex(
            TrainingEvent.UpdateSet(set.copy(
                    weight = ParameterImplP(
                        value = it.toDoubleMy(),
                        unit = set.weight.unit))))
        },
        onChangeUnit = { action.ex(
            TrainingEvent.UpdateSet( set.copy(
                    weight = ParameterImplP(
                        value = bringingWeight(set.weight),
                        unit = if (set.weight.unit == Units.GR) Units.KG else Units.GR)
            )))
        }
    )
}
@Composable fun RestPole(dataState: TrainingState, action: Action, set: SetImplP, modifier: Modifier = Modifier){
    PoleInputWithUnit(
        unitId1 = R.string.sec,
        unitId2 = R.string.min,
        headId = R.string.rest_time,
        term = set.rest.unit == Units.S,
        placeholder =  "${ set.rest.value }",
        modifier = modifier,
        typeKey = TypeKeyboard.DIGIT,
        onChangeValue = { action.ex(
            TrainingEvent.UpdateSet( set.copy(
                    rest = ParameterImplP(
                        value = it.toDoubleMy(),
                        unit = set.rest.unit)
            ))) },
        onChangeUnit = { action.ex(
            TrainingEvent.UpdateSet( set.copy(
                    rest = ParameterImplP(
                        value = bringingTime(set.rest),
                        unit = if (set.rest.unit == Units.S) Units.M else Units.S)
            )))}
    )
}

@Composable fun CountFieldText(dataState: TrainingState, action: Action, set: SetImplP){
    PoleInput(
        headId = R.string.counts, typeKey = TypeKeyboard.DIGIT, placeholder = "${ set.reps }",
        modifier = Modifier.width(IntrinsicSize.Min),
        onChangeValue = { action.ex(TrainingEvent.UpdateSet( set.copy(reps = it.toIntMy())))})

}
@Composable fun CountGroupFieldText(dataState: TrainingState, action: Action, set: SetImplP){
    PoleInput(
        headId = R.string.counts_by_group_add,
        placeholder = set.groupCount,
        onChangeValue ={ action.ex(TrainingEvent.UpdateSet( set.copy(groupCount = it))) })
}

@Composable fun TaskSwitch(dataState: TrainingState, action: Action, set: SetImplP){
    Row(verticalAlignment = Alignment.CenterVertically){
        if (set.positions.second == 1){
            IconsCollapsing(
                onClick = { action.ex(TrainingEvent.SetCollapsing(dataState.collapsing.copy(item = set)))  },
                wrap = dataState.collapsing.sets.find { it == set.idSet } != null )
        }
        TextApp(
            text = "${(set.positions.first + 1)}" ,
            style = typography.displayMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 4.dp, end =16.dp))
        Spacer(modifier = Modifier.weight(1f))
        IconQ.Duration(selected = set.goal == Goal.Duration,
            onClick = { action.ex(TrainingEvent.UpdateSet( set.copy(goal = Goal.Duration)))},)
        Spacer(modifier = Modifier.width(24.dp))
        IconQ.Distance(selected = set.goal == Goal.Distance,
            onClick = { action.ex(TrainingEvent.UpdateSet( set.copy(goal = Goal.Distance))) },)
        Spacer(modifier = Modifier.width(24.dp))
        IconQ.Count(selected = set.goal == Goal.Count,
            onClick = { action.ex(TrainingEvent.UpdateSet( set.copy(goal = Goal.Count))) },)
        Spacer(modifier = Modifier.width(24.dp))
        Spacer(modifier = Modifier.weight(1f))
        IconsGroup(
            onClickCopy = { action.ex(TrainingEvent.CopySet( set )) },
            onClickDelete = {  action.ex(
                TrainingEvent.DeleteSet(set)) },
            onClickSpeech = {
                dataState.set = set
                action.ex(TrainingEvent.ShowBS(dataState.showBS.copy(element = set)))   },)
    }
}
@Composable fun ZonePulseSwitch(dataState: TrainingState, action: Action, set: SetImplP){
    Row(verticalAlignment = Alignment.CenterVertically){
        Spacer(modifier = Modifier.weight(1f))
        ButtonSwitchPulse(selected = set.intensity == Zone.Low, idString = R.string.zone1,
            onClick = { action.ex(TrainingEvent.UpdateSet(set.copy(intensity = Zone.Low)))})
        ButtonSwitchPulse(selected = set.intensity == Zone.Min, idString = R.string.zone2,
            onClick = {action.ex(TrainingEvent.UpdateSet( set.copy(intensity = Zone.Min)))})
        ButtonSwitchPulse(selected = set.intensity == Zone.Medium, idString = R.string.zone3,
            onClick = {action.ex(TrainingEvent.UpdateSet(set.copy(intensity = Zone.Medium)))})
        ButtonSwitchPulse(selected = set.intensity == Zone.High, idString = R.string.zone4,
            onClick = {action.ex(TrainingEvent.UpdateSet( set.copy(intensity = Zone.High)))})
        ButtonSwitchPulse(selected = set.intensity == Zone.Max, idString = R.string.zone5,
            onClick = { action.ex(TrainingEvent.UpdateSet( set.copy(intensity = Zone.Max)))})
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
    modifier: Modifier = Modifier.width(35.dp).padding(vertical = 2.dp, horizontal = 2.dp),
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
    modifier: Modifier = Modifier,
    unitId1: Int,
    unitId2: Int = R.string.no,
    headId: Int,
    term: Boolean,
    placeholder: String,
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
        TextFieldApp(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            edit = true,
            beginValueZero = true,
            typeKeyboard = typeKey,
            contentAlignment = Alignment.Center,
            textStyle = typography.bodyLarge.copy(textAlign = TextAlign.Center),
            onChangeValue = { onChangeValue(it) },
            placeholder = placeholder,
        )
        Text(
            text = buildAnnotatedString {
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
        Row (verticalAlignment = Alignment.Top, modifier = Modifier.weight(1f)) {
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
            modifier = Modifier
        )
    }
}

fun viewDistance(set: Set):String {
    return "${ set.distance.value / (if (set.distance.unit == Units.KM) 1000 else 1) }"
}

fun bringingTime(duration: Parameter): Double{
    return (duration.value * (if (duration.unit == Units.M) 60.0 else 1/60.0)).discard(2)
}
fun bringingDist(dist: Parameter): Double{
    return (dist.value * (if (dist.unit == Units.MT) 0.001 else 1000.0)).discard(2)
}
fun bringingWeight(weight: Parameter): Double{
    return (weight.value * (if (weight.unit == Units.GR) 0.001 else 1000.0)).discard(2)
}

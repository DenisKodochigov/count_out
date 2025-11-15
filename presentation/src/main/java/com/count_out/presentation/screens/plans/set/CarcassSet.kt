package com.count_out.presentation.screens.plans.set

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.discard
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Parameter
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.models.TypeKeyboard
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TextFieldApp
import com.count_out.presentation.view_element.borderMy

@Composable fun CarcassTuning(
    button: @Composable () -> Unit,
    getSelect: (Domain)-> Boolean,
    list: List<Domain>,
    nameItemLeftList: @Composable (Int)-> String,
    onClick: (Int) ->Unit,
    onLongClick: ()-> Unit = {},
    nameExercise: @Composable () -> Unit,
    actionTitle: @Composable () -> Unit,
    setBody: @Composable () -> Unit,
    iconGoal: @Composable () -> Unit,
    actionBottom: @Composable () -> Unit,
    iconZone: @Composable () -> Unit,
){
    var rightHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    Row(modifier = Modifier.fillMaxWidth()) {
        Column( modifier = Modifier.height(rightHeight).width(IntrinsicSize.Min).padding(top = 16.dp)) {
            Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                Column {
                    list.forEachIndexed { ind, item ->
                        Row(horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.borderMy(getSelect(item), color = colorScheme.surfaceContainerLow)
                                .combinedClickable(onLongClick = onLongClick, onClick = { onClick(ind) })
                        ) {
                            TextApp( modifier = Modifier.width(60.dp).padding(horizontal = 2.dp),
                                textAlign = TextAlign.Center,
                                text = nameItemLeftList(ind),
                                style = typography.titleMedium )
                             }
                    }
                }
                Column { button() }
            }
        }
        Column( modifier = Modifier.weight(1f)
                    .onGloballyPositioned { rightHeight = with(density) { it.size.height.toDp() }}
        ){
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
                Spacer(modifier = Modifier.weight(1f))
                nameExercise()
                Spacer(modifier = Modifier.weight(1f))
                actionTitle()
            }
            setBody()
            Row(verticalAlignment = Alignment.CenterVertically){
                Spacer(modifier = Modifier.weight(1f))
                iconGoal()
                Spacer(modifier = Modifier.weight(1f))
                iconZone()
                Spacer(modifier = Modifier.weight(1f))
                actionBottom()
            }
        }
    }
}
//@Composable fun CarcassTuningSet(
//    columnRight: @Composable () -> Unit,
//    columnLeft: @Composable () -> Unit,
//    button: @Composable () -> Unit
//){
//    var rightHeight by remember { mutableStateOf(0.dp) }
//    val density = LocalDensity.current
//
//    Row(modifier = Modifier.fillMaxWidth().padding(2.dp)) {
//        Column( modifier = Modifier.height(rightHeight).width(IntrinsicSize.Min).padding(end = 8.dp)){
//            Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
//                content = { columnLeft()},)
//            Column { button() }
//        }
//        Column( content = { columnRight() },
//            modifier = Modifier.weight(1f).padding(horizontal = 1.dp)
//                .onGloballyPositioned { rightHeight = with(density) { it.size.height.toDp() } }
//        )
//    }
//}
@Composable fun CarcassCardDistance(
    distancePole: @Composable ()->Unit,
    restPole: @Composable ()->Unit
){
    Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        distancePole()
        Spacer(modifier = Modifier.weight(1f))
        restPole()
        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable fun CarcassCardDuration(
    durationPole: @Composable ()->Unit,
    weightPole: @Composable ()->Unit,
    restPole: @Composable ()->Unit
){
    Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        durationPole()
        Spacer(modifier = Modifier.weight(1f))
        weightPole()
        Spacer(modifier = Modifier.weight(1f))
        restPole()
        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable fun CarcassCardCount(
    intervalPole: @Composable ()->Unit,
    weightPole: @Composable ()->Unit,
    restPole: @Composable ()->Unit,
    countPole: @Composable ()->Unit,
    countGroupPole: @Composable ()->Unit
){
    Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        intervalPole()
        Spacer(modifier = Modifier.weight(1f))
        weightPole()
        Spacer(modifier = Modifier.weight(1f))
        restPole()
        Spacer(modifier = Modifier.weight(1f))
    }
    Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
    ) {
//        Spacer(modifier = Modifier.weight(1f))
        countPole()
//        Spacer(modifier = Modifier.weight(1f))
        countGroupPole()
//        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable fun CarcassPoleInputAndUnits(
    headId: Int,
    enableClick: Boolean = true,
    parameter: Parameter,
    onChange: (Parameter)-> Unit,
){
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .animateContentSize()
            .background(color = colorScheme.onSecondary, shape = shapes.small)
            .width(Dimen.widthParameter)
            .padding(top = 2.dp, bottom = 6.dp, start = interval_between_pole, end = 4.dp)
    ) {
        TextApp(text = stringResource(headId), textAlign = TextAlign.Center, style = Dimen.typeLabel())
        TextFieldApp(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            edit = true,
            beginValueEmpty = true,
            typeKeyboard = TypeKeyboard.DIGIT,
            contentAlignment = Alignment.Center,
            textStyle = typography.titleLarge.copy(textAlign = TextAlign.Center),
            onChangeFocus = { onChange( Parameter.changeValue(parameter, it)) },
            placeholder = "${ parameter.value.discard(2)}",
        )
        Text(
            text = stringResource(parameter.unit.id),
            modifier = Modifier.padding(start = 2.dp, top = 2.dp)
                .clickable(enableClick) { onChange( Parameter.changeUnit(parameter)) },
            style = Dimen.typeUnit(),
            color = colorScheme.outline
        )
    }
}
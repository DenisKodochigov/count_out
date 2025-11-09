package com.count_out.presentation.view_element.icons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.enums.Goal
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.models.Dimen.TAB_FADE_IN_ANIMATION_DELAY
import com.count_out.presentation.models.Dimen.TAB_FADE_IN_ANIMATION_DURATION
import com.count_out.presentation.models.Dimen.TAB_FADE_OUT_ANIMATION_DURATION
import com.count_out.presentation.models.Dimen.sizeIcon
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.custom_view.IconQ
import com.count_out.presentation.view_element.custom_view.IconQ.ArrowChordCanvas

@Composable fun IconsGroup(
    onEdit: (() -> Unit)? = null,
    onCopy: (() -> Unit)? = null,
    onSpeech: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    onSetAdd: (() -> Unit)? = null,
    onSetDel: (() -> Unit)? = null,
    onSetSpeech: (() -> Unit)? = null,
    onExerciseAdd: (() -> Unit)? = null,
    onExerciseDel: (() -> Unit)? = null,
    onExerciseSpeech: (() -> Unit)? = null,
    onRingAdd: (() -> Unit)? = null,
    onRingDel: (() -> Unit)? = null,
    onRingSpeech: (() -> Unit)? = null,
    onPlanAdd: (() -> Unit)? = null,
    onToExercise: (() -> Unit)? = null,
    onToRing: (() -> Unit)? = null,
    label: Int = 0
){
    CarcassMultiIcons(label,
        {expanded ->
            ListIcons(expanded,onEdit,onCopy,onSpeech,onDelete,onSetAdd,onSetDel,onSetSpeech,onExerciseAdd,
                    onExerciseDel,onExerciseSpeech,onRingAdd,onRingDel,onRingSpeech,onPlanAdd,
                onToExercise, onToRing, label)
        }
    )
}
@Composable fun CarcassMultiIcons(label:Int, content: @Composable (expanded:()-> Unit)-> Unit){
    var expanded by remember { mutableStateOf(false) }
    Box {
        ButtonOther(label){ expanded = true }
        DropdownMenu(
            modifier = Modifier.padding(horizontal = 12.dp).background(color = colorScheme.surfaceContainer),
            expanded = expanded,
            offset = DpOffset((0).dp, (-48).dp),
            onDismissRequest = { expanded = false },
            content = {
                Row(verticalAlignment = Alignment.CenterVertically)
                    { content { expanded = false } }
            }
        )
    }
}
@Composable fun ButtonOther(label:Int, expanded: ()->Unit,){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconQ.Multi(onClick = expanded)
        if (label > 0) TextApp(text = stringResource(label),
            style = MaterialTheme.typography.labelSmall)
    }
}
@Composable fun ListIcons(
    expanded: ()->Unit,
    onEdit: (() -> Unit)? = null,
    onCopy: (() -> Unit)? = null,
    onSpeech: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    onSetAdd: (() -> Unit)? = null,
    onSetDel: (() -> Unit)? = null,
    onSetSpeech: (() -> Unit)? = null,
    onExerciseAdd: (() -> Unit)? = null,
    onExerciseDel: (() -> Unit)? = null,
    onExerciseSpeech: (() -> Unit)? = null,
    onRingAdd: (() -> Unit)? = null,
    onRingDel: (() -> Unit)? = null,
    onRingSpeech: (() -> Unit)? = null,
    onPlanAdd: (() -> Unit)? = null,
    onToExercise: (() -> Unit)? = null,
    onToRing: (() -> Unit)? = null,
    label: Int = 0
){
    onEdit?.let { IconSingle(image = Icons.Default.Edit, onClick = { it(); expanded()} )}
    onCopy?.let { IconSingle(image = Icons.Default.CopyAll, onClick = { it(); expanded()} ) }
    onSpeech?.let { IconSingle(image = R.drawable.waveform, onClick = { it(); expanded()} )}
    onDelete?.let { IconSingle(image = Icons.Default.DeleteOutline, onClick = { it(); expanded()} ) }
    onSetAdd?.let { IconCopyText("S",onClick = { it(); expanded()})}
    onSetDel?.let { IconDelText("S",onClick = { it(); expanded()})}
    onSetSpeech?.let { IconSpeechText("S",onClick = { it(); expanded()})}
    onExerciseAdd?.let { IconCopyText("E",onClick = { it(); expanded()}) }
    onExerciseDel?.let { IconDelText("E",onClick = { it(); expanded()}) }
    onExerciseSpeech?.let { IconSpeechText("E",onClick = { it(); expanded()}) }
    onRingAdd?.let { IconCopyText("R",onClick = { it(); expanded()})}
    onRingDel?.let { IconDelText("R",onClick = { it(); expanded()})}
    onRingSpeech?.let { IconSpeechText("R",onClick = { it(); expanded()})}
    onPlanAdd?.let { IconCopyText("P",onClick = { it(); expanded()})}
    onToExercise?.let { IconRingOrExercise(true, onClick = { it(); expanded()} ) }
    onToRing?.let { IconRingOrExercise(false, onClick = { it(); expanded()} ) }
}
@Composable fun IconSingle(image: ImageVector, onClick:()->Unit = {}, idDescription: Int = 0, label: Int = 0){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = image,
            tint = colorScheme.outline,
            contentDescription = if ( idDescription == 0) "" else stringResource(id = idDescription),
            modifier = Modifier.size(sizeIcon).clickable { onClick() })
        if ( label != 0)
            TextApp(
                text = stringResource(label),
                style = Dimen.typeLabel(),
                overflow = TextOverflow.Clip,
                modifier = Modifier.width(sizeIcon) )
    }
}
@Composable fun IconSingle(image: Int, onClick:()->Unit = {}, idDescription: Int = 0, label: Int = 0){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painter = painterResource(id = image),
            tint = colorScheme.outline,
            contentDescription = if ( idDescription == 0) "" else stringResource(id = idDescription),
            modifier = Modifier.size(sizeIcon).clickable { onClick() })
        if ( label != 0)
            TextApp(
                text = stringResource(label),
                style = Dimen.typeLabel(),
                overflow = TextOverflow.Clip,
                modifier = Modifier.width(sizeIcon) )
    }
}
@Composable fun IconsCollapsing(onClick: ()->Unit, wrap: Boolean) {
    if (wrap) IconQ.Collapsing( onClick = onClick) else IconQ.UnCollapsing( onClick = onClick)
}
@Composable fun IconRingOrExercise(selected: Boolean, onClick:() -> Unit){
    val progress: Float by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = tween(durationMillis = 800, easing = EaseInOut),
        label = "straightAnimation")
    ArrowChordCanvas(progress = progress, onClick)
}
@Composable fun IconAddSet(onClick:()->Unit) = IconAdd(onClick = onClick, text = "S+" )
@Composable fun IconAddRing(onClick:()->Unit) = IconAdd(onClick = onClick, text = "R+" )
@Composable fun IconAddExercise(onClick:()->Unit) = IconAdd(onClick = onClick, text = "E+" )
@Composable fun IconAddPlan(onClick:()->Unit) = IconAdd(onClick = onClick, text = "P+" )

@Composable fun IconAddActivity(onClick:()->Unit) = IconAdd(onClick = onClick, text = "A+" )
@Composable fun IconAdd(onClick:()->Unit, text: String = "+") {
    Box(modifier = Modifier){
        Spacer(modifier = Modifier
            .align(alignment = Alignment.Center)
            .size(size = sizeIcon)
            .clickable { onClick() }
            .clip(shape = CircleShape)
            .border(width = 1.dp, color = colorScheme.outline, shape = CircleShape)
        )
        TextApp(text = text, style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(alignment = Alignment.Center))
    }
}
@Composable fun IconSubscribe(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit = {},
    selected: Boolean = false,
){
    val animationSpec = remember {
        tween<Color>(
            easing = LinearEasing,
            delayMillis = TAB_FADE_IN_ANIMATION_DELAY,
            durationMillis = if (selected) TAB_FADE_IN_ANIMATION_DURATION
            else TAB_FADE_OUT_ANIMATION_DURATION
        )
    }
    val colorIcon = colorScheme.outline
    val colorUnselected = Color(colorIcon.red, colorIcon.green, colorIcon.blue, colorIcon.alpha * 0.4f)
    val iconColor by animateColorAsState(
        label = "",
        animationSpec = animationSpec,
        targetValue = if (selected) colorIcon else colorUnselected,
    )
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally )
    {
        IconButton( onClick = onSelected, modifier = Modifier.testTag(text)){
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = iconColor,
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize()
                    .clearAndSetSemantics { contentDescription = text })
        }
        TextApp(text = text.replaceFirstChar{it.uppercase()}, style = MaterialTheme.typography.labelSmall) // alumBodySmall)
    }
}
@Composable fun IconZone(value: Int, onClick: ()->Unit = {}){
    val xBaseIcon = 25.dp
    val yBaseIcon = 30.dp
    val xDelta = 10.dp
    val yDelta = 3.dp
    val border = 1.dp
    val shape =  shapes.extraSmall
    val color = colorScheme.outline
    val background = colorScheme.primary

    val color1 = Color(color.red, color.green, color.blue, color.alpha * 0.6f)
    val color2 = Color(color.red, color.green, color.blue, color.alpha * 0.3f)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.clickable { onClick() },
            contentAlignment = Alignment.Center,
            propagateMinConstraints = true
        ){
            Row(modifier = Modifier
                .border(border, color = color2, shape = shape)
                .background(color = background, shape = shape)
                .width(xBaseIcon + xDelta * 2)
                .height(yBaseIcon - yDelta * 2),){}
            Row(modifier = Modifier
                .border(border, color = color1, shape = shape)
                .background(color = background, shape = shape)
                .width(xBaseIcon + xDelta)
                .height(yBaseIcon - yDelta),){}
            Row(modifier = Modifier
                .border(border, color = color, shape = shape)
                .background(color = background, shape = shape)
                .width(xBaseIcon)
                .height(yBaseIcon),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){ TextApp(color = color, style = MaterialTheme.typography.titleMedium,
                text = when(value){
                    1->"I"
                    2->"II"
                    3->"III"
                    4->"IV"
                    5->"V"
                    else->""
                }
            )}
        }
        TextApp(
            text = "${ stringResource(R.string.zone) } ",
            textAlign = TextAlign.Start,
            style = Dimen.typeLabel())
    }
}
@Composable fun IconGoal(goal: Goal, onClick: ()->Unit){
    val xBaseIcon = 30.dp
    val yBaseIcon = 30.dp
    val xDelta = 10.dp
    val yDelta = 3.dp
    val border = 1.dp
    val shape =  shapes.extraSmall
    val color = colorScheme.outline
    val background = colorScheme.primary
    val color1 = Color(color.red, color.green, color.blue, color.alpha * 0.6f)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.clickable { onClick() }, contentAlignment = Alignment.Center){
            Row(modifier = Modifier
                .border(border, color = color1, shape = shape)
                .background(color = background, shape = shape)
                .width(xBaseIcon + xDelta)
                .height(yBaseIcon - yDelta),){}
            Row(modifier = Modifier
                .border(border, color = color, shape = shape)
                .background(color = background, shape = shape)
                .width(xBaseIcon)
                .height(yBaseIcon),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                when(goal){
                    Goal.Distance -> IconQ.DistanceOnly()
                    Goal.Duration -> IconQ.DurationOnly()
                    Goal.Count -> IconQ.CountOnly()
                    Goal.CountGroup -> IconQ.CountOnly()
                }
            }
        }
        TextApp(text = stringResource(R.string.goal), style = Dimen.typeLabel() )
    }
}

//@Composable
//@Preview(backgroundColor = 0xFF9FA1AF)
//fun Preview(){
////    IconZoneNew(1)
//    IconGoal(Goal.Count, {})
//}
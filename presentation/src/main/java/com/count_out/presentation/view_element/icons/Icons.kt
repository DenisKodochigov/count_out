package com.count_out.presentation.view_element.icons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
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
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.enums.Goal
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.models.Dimen.TAB_FADE_IN_ANIMATION_DELAY
import com.count_out.presentation.models.Dimen.TAB_FADE_IN_ANIMATION_DURATION
import com.count_out.presentation.models.Dimen.TAB_FADE_OUT_ANIMATION_DURATION
import com.count_out.presentation.models.Dimen.sizeBetweenIcon
import com.count_out.presentation.models.Dimen.sizeIcon
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.custom_view.IconQ
import com.count_out.presentation.view_element.custom_view.IconQ.ArrowChordCanvas

@Composable fun IconsGroup(
    onClickEdit: (() -> Unit)? = null,
    onClickCopy: (() -> Unit)? = null,
    onClickSpeech: (() -> Unit)? = null,
    onClickDelete: (() -> Unit)? = null,
    onClickAddSet: (() -> Unit)? = null,
    onClickAddRing: (() -> Unit)? = null,
    onClickAddExercise: (() -> Unit)? = null,
    onClickAddPlan: (() -> Unit)? = null,
    onClickRingExercise: (() -> Unit)? = null,
    selected: Boolean = true,
    type: Boolean = true,
){
    var expanded by remember { mutableStateOf(false) }
    Box {
        if(type) IconQ.Multi(onClick = { expanded = true }) else IconQ.Multi1(onClick = { expanded = true })
        MaterialTheme( shapes = shapes.copy(extraSmall = shapes.large)) {
            DropdownMenu(
                modifier = Modifier.padding(8.dp),
                expanded = expanded,
                offset = DpOffset((-8).dp, 8.dp),
                onDismissRequest = { expanded = false }
            ) {
                VerIcons(
                    onClickEdit,
                    onClickCopy,
                    onClickSpeech,
                    onClickDelete,
                    onClickAddSet,
                    onClickAddRing,
                    onClickAddExercise,
                    onClickAddPlan,
                    onClickRingExercise,
                    selected,
                ) { expanded = false }
            }
        }
    }
}

@Composable fun AnimateIcon(
    initValue: Dp = sizeIcon,
    targetValue: Dp = 22.dp,
    icon: ImageVector = Icons.Default.Bluetooth,
    animate: Boolean = false,
    onClick: ()->Unit = {},
){
    var target by remember { mutableStateOf(initValue) }
    var iteration by remember { mutableIntStateOf(1) }
    LaunchedEffect(animate) {
        iteration = if (animate) 1000 else 1
        target = if (animate) if (target == initValue) targetValue else initValue else initValue
    }
    val size by animateDpAsState(
        label = "size",
        targetValue = target,
        animationSpec = repeatable(
            iterations = iteration,
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(modifier = Modifier.size( sizeIcon)){
        Icon(imageVector = icon, contentDescription = "",
            tint = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .align(Alignment.Center)
                .size(size)
                .clickable { onClick() })
    }
}
@Composable fun VerIcons(
    onClickEdit: (() -> Unit)? = null,
    onClickCopy: (() -> Unit)? = null,
    onClickSpeech: (() -> Unit)? = null,
    onClickDelete: (() -> Unit)? = null,
    onClickAddSet: (() -> Unit)? = null,
    onClickAddRing: (() -> Unit)? = null,
    onClickAddExercise: (() -> Unit)? = null,
    onClickAddPlan: (() -> Unit)? = null,
    onClickRingExercise: (() -> Unit)? = null,
    selected: Boolean,
    expanded: ()->Unit,
){
    Column( verticalArrangement = Arrangement.SpaceBetween){
//        Spacer(modifier = Modifier.height(sizeBetweenIcon))
        onClickAddSet?.let {
            IconAddSet(onClick = { it(); expanded()})
            Spacer(modifier = Modifier.height(sizeBetweenIcon))}
        onClickAddRing?.let {
            IconAddRing(onClick = { it(); expanded()})
            Spacer(modifier = Modifier.height(sizeBetweenIcon))}
        onClickAddExercise?.let {
            IconAddExercise(onClick = { it(); expanded()})
            Spacer(modifier = Modifier.height(sizeBetweenIcon)) }
        onClickAddPlan?.let {
            IconAddPlan(onClick = { it(); expanded()})
            Spacer(modifier = Modifier.height(sizeBetweenIcon)) }
        onClickEdit?.let {
            IconSingle(image = Icons.Default.Edit, onClick = { it(); expanded()} )
            Spacer(modifier = Modifier.height(sizeBetweenIcon)) }
        onClickCopy?.let {
            IconSingle(image = Icons.Default.CopyAll, onClick = { it(); expanded()} )
            Spacer(modifier = Modifier.height(sizeBetweenIcon)) }
        onClickSpeech?.let {
            IconSingle(image = Icons.Default.GraphicEq, onClick = { it(); expanded()} )
            Spacer(modifier = Modifier.height(sizeBetweenIcon)) }
        onClickDelete?.let {
            IconSingle(image = Icons.Default.DeleteOutline, onClick = { it(); expanded()} )
            Spacer(modifier = Modifier.height(sizeBetweenIcon)) }
        onClickRingExercise?.let {
            IconRingOrExercise(selected, onClick = { it(); expanded()} )
            Spacer(modifier = Modifier.height(sizeBetweenIcon)) }
    }
}

@Composable fun IconSingle(image: ImageVector, onClick:()->Unit = {}, idDescription: Int = 0){
    Icon(imageVector = image,
        tint = MaterialTheme.colorScheme.outline,
        contentDescription = if ( idDescription == 0) "" else stringResource(id = idDescription),
        modifier = Modifier
            .size(sizeIcon)
            .clickable { onClick() })
}
@Composable fun IconSingleLarge(image: ImageVector, onClick:()->Unit){
    Icon(imageVector = image,
        contentDescription = "",
        tint = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .size(Dimen.sizeIconLarge)
            .clickable { onClick() })
}
@Composable fun IconSingleLarge(image: ImageVector){
    Icon(imageVector = image, contentDescription = "",
        tint = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.size(Dimen.sizeIconLarge)
    )
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
//@Composable fun IconAddActivity(onClick:()->Unit) = IconAdd(onClick = onClick, text = "A+" )
@Composable fun IconAdd(onClick:()->Unit, text: String = "+") {
    Box(modifier = Modifier){
        Spacer(modifier = Modifier
            .align(alignment = Alignment.Center)
            .size(size = sizeIcon)
            .clickable { onClick() }
            .clip(shape = CircleShape)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline, shape = CircleShape)
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
    val colorIcon = MaterialTheme.colorScheme.outline
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
@Composable fun IconCount(selected: Boolean, onClick: ()->Unit){
    Column(verticalArrangement = Arrangement.Center){
        IconQ.Count(selected = selected, onClick = onClick)
        TextApp(
            text = "${ stringResource(R.string.counts) } ",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium)
    }
}
@Composable fun IconDuration(selected: Boolean, onClick: ()->Unit){
    Column(verticalArrangement = Arrangement.Center){
        IconQ.Count(selected = selected, onClick = onClick)
        TextApp(
            text = "${ stringResource(R.string.duration) } ",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium)
    }
}
@Composable fun IconDistance(selected: Boolean, onClick: ()->Unit){
    Column(verticalArrangement = Arrangement.Center){
        IconQ.Count(selected = selected, onClick = onClick)
        TextApp(
            text = "${ stringResource(R.string.distance) } ",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium)
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
            ){ TextApp(color = color, style = MaterialTheme.typography.titleLarge,
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
            style = MaterialTheme.typography.titleMedium)
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
        TextApp(text = stringResource(R.string.goal), style = MaterialTheme.typography.titleMedium )
    }
}

fun masValue(current: Int): List<Int> {
    return listOf(0, 1, 2, 3, 4).map { ((it + current + 3) % 5) + 1}
}
@Composable
@Preview(backgroundColor = 0xFF9FA1AF)
fun Preview(){
//    IconZoneNew(1)
    IconGoal(Goal.Count, {})
}
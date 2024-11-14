package com.example.count_out.ui.view_components.icons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.entity.Const.TAB_FADE_IN_ANIMATION_DELAY
import com.example.count_out.entity.Const.TAB_FADE_IN_ANIMATION_DURATION
import com.example.count_out.entity.Const.TAB_FADE_OUT_ANIMATION_DURATION
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.Dimen.sizeBetweenIcon
import com.example.count_out.ui.theme.Dimen.sizeIcon
import com.example.count_out.ui.theme.alumniReg12
import com.example.count_out.ui.theme.mTypography
import com.example.count_out.ui.theme.shapes
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.custom_view.PlayIcon

@Composable fun IconsGroup(
    onClickEdit: (() -> Unit)? = null,
    onClickCopy: (() -> Unit)? = null,
    onClickSpeech: (() -> Unit)? = null,
    onClickDelete: (() -> Unit)? = null,
    onClickAddSet: (() -> Unit)? = null,
    onClickAddRing: (() -> Unit)? = null,
    onClickAddExercise: (() -> Unit)? = null,
){
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconSingle(image = Icons.Default.MoreVert, onClick = { expanded = true }, idDescription = R.string.show_Action)
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
                    onClickAddExercise
                ) { expanded = false }
            }
        }
    }
}
@Composable fun AnimateIcon(
    initValue: Dp = sizeIcon,
    targetValue: Dp = 17.dp,
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
            modifier = Modifier.align(Alignment.Center).size(size).clickable { onClick() })
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
    expanded: ()->Unit
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
            Spacer(modifier = Modifier.height(sizeBetweenIcon))
        }
    }
}
@Composable fun IconSingle(image: ImageVector, onClick:()->Unit = {}, idDescription: Int = 0){
    Icon(imageVector = image,
        tint = MaterialTheme.colorScheme.tertiary,
        contentDescription = if ( idDescription == 0) "" else stringResource(id = idDescription),
        modifier = Modifier.size(sizeIcon).clickable { onClick() })
}
@Composable fun IconSingleLarge(image: ImageVector, onClick:()->Unit){
    Icon(imageVector = image,
        contentDescription = "",
        tint = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.size(Dimen.sizeIconLarge).clickable { onClick() })
}
@Composable fun IconSingleLarge(image: ImageVector){
    Icon(imageVector = image, contentDescription = "",
        tint = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.size(Dimen.sizeIconLarge))
}
@Composable fun IconsCollapsing(onClick: ()->Unit, wrap: Boolean) {
    Icon(imageVector = if (wrap) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
        contentDescription = "",
        modifier = Modifier
            .padding(4.dp)
            .size(sizeIcon)
            .clickable { onClick() }
    )
}
@Composable fun IconAddSet(onClick:()->Unit) = IconAdd(onClick = onClick, text = "S+" )
@Composable fun IconAddRing(onClick:()->Unit) = IconAdd(onClick = onClick, text = "R+" )
@Composable fun IconAddExercise(onClick:()->Unit) = IconAdd(onClick = onClick, text = "E+" )
//@Composable fun IconAddActivity(onClick:()->Unit) = IconAdd(onClick = onClick, text = "A+" )

@Composable fun IconAdd(onClick:()->Unit, text: String = "+") {
    Box(modifier = Modifier){
        Spacer(modifier = Modifier.align(alignment = Alignment.Center)
            .size(size = sizeIcon)
            .clickable { onClick() }
            .clip(shape = CircleShape)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline, shape = CircleShape)
        )
        TextApp(text = text, style = mTypography.titleMedium, modifier = Modifier.align(alignment = Alignment.Center))
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
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        IconButton( onClick = onSelected, modifier = Modifier.testTag(text)
        ){
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = iconColor,
                modifier = Modifier.fillMaxSize().animateContentSize()
                    .clearAndSetSemantics { contentDescription = text }
            )
        }
        TextApp(text = text, style = alumniReg12)
    }
}
@Composable fun IconRun(onClick: () -> Unit){
    Box(modifier = Modifier.clickable { onClick() }){
        PlayIcon(MaterialTheme.colorScheme.secondary) }
}
@Composable fun IconAdd(onClick: () -> Unit){
    Box(modifier = Modifier.clickable { onClick() }){
        PlayIcon(MaterialTheme.colorScheme.secondary) }
}

//@Composable fun HorIcons(
//    onClickEdit: (() -> Unit)? = null,
//    onClickCopy: (() -> Unit)? = null,
//    onClickSpeech: (() -> Unit)? = null,
//    onClickDelete: (() -> Unit)? = null,
//    onClickAddSet: (() -> Unit)? = null,
//    onClickAddRing: (() -> Unit)? = null,
//    onClickAddExercise: (() -> Unit)? = null,
//    expanded: ()->Unit
//){
//    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
//        Spacer(modifier = Modifier.width(sizeBetweenIcon))
//        onClickEdit?.let {  IconSingle(
//            image = Icons.Default.Edit,
//            onClick = { it(); expanded()} )
//            Spacer(modifier = Modifier.width(sizeBetweenIcon)) }
//        onClickCopy?.let {  IconSingle(
//            image = Icons.Default.CopyAll,
//            onClick = { it(); expanded()} )
//            Spacer(modifier = Modifier.width(sizeBetweenIcon)) }
//        onClickSpeech?.let {  IconSingle(
//            image = Icons.Default.GraphicEq,
//            onClick = { it(); expanded()} )
//            Spacer(modifier = Modifier.width(sizeBetweenIcon)) }
//        onClickDelete?.let {  IconSingle(
//            image = Icons.Default.DeleteOutline,
//            onClick = { it(); expanded()} )
//            Spacer(modifier = Modifier.width(sizeBetweenIcon)) }
//        onClickAddSet?.let {  IconSingle(
//            image = Icons.Default.AddRoad,
//            onClick = { it(); expanded()} )
//            Spacer(modifier = Modifier.width(sizeBetweenIcon))}
//        onClickAddRing?.let {  IconSingle(
//            image = Icons.Default.AddCircleOutline,
//            onClick = { it();expanded()} )
//            Spacer(modifier = Modifier.width(sizeBetweenIcon))}
//        onClickAddExercise?.let {  IconSingle(
//            image = Icons.Default.LibraryAdd,
//            onClick = { it(); expanded()} )
//            Spacer(modifier = Modifier.width(sizeBetweenIcon)) }
//    }
//}

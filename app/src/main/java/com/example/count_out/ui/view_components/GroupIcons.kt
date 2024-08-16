package com.example.count_out.ui.view_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.AddRoad
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.Dimen.sizeBetweenIcon
import com.example.count_out.ui.theme.shapes

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
    Box() {
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

@Composable fun HorIcons(
    onClickEdit: (() -> Unit)? = null,
    onClickCopy: (() -> Unit)? = null,
    onClickSpeech: (() -> Unit)? = null,
    onClickDelete: (() -> Unit)? = null,
    onClickAddSet: (() -> Unit)? = null,
    onClickAddRing: (() -> Unit)? = null,
    onClickAddExercise: (() -> Unit)? = null,
    expanded: ()->Unit
){
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
        Spacer(modifier = Modifier.width(sizeBetweenIcon))
        onClickEdit?.let {  IconSingle(
            image = Icons.Default.Edit,
            onClick = { it(); expanded()} )
            Spacer(modifier = Modifier.width(sizeBetweenIcon)) }
        onClickCopy?.let {  IconSingle(
            image = Icons.Default.CopyAll,
            onClick = { it(); expanded()} )
            Spacer(modifier = Modifier.width(sizeBetweenIcon)) }
        onClickSpeech?.let {  IconSingle(
            image = Icons.Default.GraphicEq,
            onClick = { it(); expanded()} )
            Spacer(modifier = Modifier.width(sizeBetweenIcon)) }
        onClickDelete?.let {  IconSingle(
            image = Icons.Default.DeleteOutline,
            onClick = { it(); expanded()} )
            Spacer(modifier = Modifier.width(sizeBetweenIcon)) }
        onClickAddSet?.let {  IconSingle(
            image = Icons.Default.AddRoad,
            onClick = { it(); expanded()} )
            Spacer(modifier = Modifier.width(sizeBetweenIcon))}
        onClickAddRing?.let {  IconSingle(
            image = Icons.Default.AddCircleOutline,
            onClick = { it();expanded()} )
            Spacer(modifier = Modifier.width(sizeBetweenIcon))}
        onClickAddExercise?.let {  IconSingle(
            image = Icons.Default.LibraryAdd,
            onClick = { it(); expanded()} )
            Spacer(modifier = Modifier.width(sizeBetweenIcon)) }
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
        onClickAddSet?.let {  IconSingle(
            image = Icons.Default.AddRoad,
            onClick = { it(); expanded()} )
            Spacer(modifier = Modifier.height(sizeBetweenIcon))}
        onClickAddRing?.let {  IconSingle(
            image = Icons.Default.AddCircleOutline,
            onClick = { it();expanded()} )
            Spacer(modifier = Modifier.height(sizeBetweenIcon))}
        onClickAddExercise?.let {  IconSingle(
            image = Icons.Default.LibraryAdd,
            onClick = { it(); expanded()} )
            Spacer(modifier = Modifier.height(sizeBetweenIcon)) }
        onClickEdit?.let {  IconSingle(
            image = Icons.Default.Edit,
            onClick = { it(); expanded()} )
            Spacer(modifier = Modifier.height(sizeBetweenIcon)) }
        onClickCopy?.let {  IconSingle(
            image = Icons.Default.CopyAll,
            onClick = { it(); expanded()} )
            Spacer(modifier = Modifier.height(sizeBetweenIcon)) }
        onClickSpeech?.let {  IconSingle(
            image = Icons.Default.GraphicEq,
            onClick = { it(); expanded()} )
            Spacer(modifier = Modifier.height(sizeBetweenIcon)) }
        onClickDelete?.let {  IconSingle(
            image = Icons.Default.DeleteOutline,
            onClick = { it(); expanded()} )
            Spacer(modifier = Modifier.height(sizeBetweenIcon))
        }
    }
}
@Composable fun IconSingle(image: ImageVector, onClick:()->Unit, idDescription: Int = 0){
    Icon(imageVector = image,
        contentDescription = if ( idDescription == 0) "" else stringResource(id = idDescription),
        modifier = Modifier.size(Dimen.sizeIcon).clickable { onClick() })
}

@Composable fun IconSingleLarge(image: ImageVector){
    Icon(imageVector = image, contentDescription = "", modifier = Modifier.size(Dimen.sizeIconLarge))
}

@Composable
fun IconsCollapsing(onClick: ()->Unit, wrap: Boolean)
{
    Icon(imageVector = if (wrap) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
        contentDescription = "",
        modifier = Modifier
            .padding(4.dp)
            .size(Dimen.sizeIcon)
            .clickable { onClick() }
    )
}


//@Composable fun IconsCopySpeechDel(onCopy: ()->Unit, onSpeech: ()->Unit, onDel: ()->Unit ) {
//    Icon(
//        painter = painterResource(id = R.drawable.ic_copy),
//        contentDescription = "",
//        modifier = Modifier
//            .padding(0.dp)
//            .size(Dimen.sizeIcon)
//            .clickable { onCopy() }
//    )
//    Spacer(modifier = Modifier.width(sizeBetweenIcon))
//    Icon(
//        painter = painterResource(id = R.drawable.ic_speech),
//        contentDescription = "",
//        modifier = Modifier
//            .padding(0.dp)
//            .size(Dimen.sizeIcon)
//            .clickable { onSpeech() }
//    )
//    Spacer(modifier = Modifier.width(sizeBetweenIcon))
//    Icon(
//        painter = painterResource(id = R.drawable.ic_del),
//        contentDescription = "",
//        modifier = Modifier
//            .padding(0.dp)
//            .size(Dimen.sizeIcon)
//            .clickable { onDel() }
//    )
//}

//@Composable fun IconsCollapsingCopySpeechDel(
//    onCopy: ()->Unit,
//    onSpeech: ()->Unit,
//    onDel: ()->Unit,
//    onCollapsing:()->Unit,
//    wrap: Boolean)
//{
//    IconsCollapsing1( onCollapsing = onCollapsing, wrap = wrap)
//    Spacer(modifier = Modifier.width(sizeBetweenIcon))
//    IconsCopySpeechDel(onCopy, onSpeech, onDel)
//}
//@Composable
//fun IconCollapsingSpeech(
//    onSpeech: ()->Unit,
//    onCollapsing:() -> Unit,
//    idIconCollapsing: Int
//){
//    Icon(
//        painter = painterResource(id = idIconCollapsing),
//        contentDescription = "",
//        modifier = Modifier
//            .padding(4.dp)
//            .size(Dimen.sizeIcon)
//            .clickable { onCollapsing() }
//    )
//    Spacer(modifier = Modifier.width(sizeBetweenIcon))
//    Icon(
//        painter = painterResource(id = R.drawable.ic_speech), contentDescription = "",
//        modifier = Modifier
//            .padding(4.dp)
//            .size(Dimen.sizeIcon)
//            .clickable { onSpeech() }
//    )
//
//}
//@Composable
//fun IconSpeechDel(
//    onSpeech: ()->Unit,
//    onDelete:() -> Unit,
//){
//    Icon(
//        painter = painterResource(id = R.drawable.ic_speech),
//        contentDescription = "",
//        modifier = Modifier
//            .padding(4.dp)
//            .size(Dimen.sizeIcon)
//            .clickable { onSpeech() }
//    )
//    Spacer(modifier = Modifier.width(8.dp))
//    Icon(
//        painter = painterResource(id = R.drawable.ic_del1), contentDescription = "",
//        modifier = Modifier
//            .padding(4.dp)
//            .size(Dimen.sizeIcon)
//            .clickable { onDelete() }
//    )
//}

//@Composable fun IconAddItem(textId: Int, onAdd: ()->Unit){
//    Row(
//        horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.clickable { onAdd.invoke() }
//    ) {
//        TextApp(
//            text = stringResource(id = textId),
//            style = interLight12,
//            modifier = Modifier.padding(horizontal = 4.dp)
//        )
//        Icon(
//            painter = painterResource(id = R.drawable.ic_add),
//            contentDescription = "",
//            modifier = Modifier
//                .padding(4.dp)
//                .size(Dimen.sizeIcon)
//        )
//    }
//}
//
//@Composable fun IconSingle(image: ImageVector){
//    Icon(imageVector = image, contentDescription = "", modifier = Modifier.size(Dimen.sizeIcon))
//}
//@Composable fun IconSingle(image: Painter){
//    Icon(painter = image, contentDescription = "", modifier = Modifier.size(Dimen.sizeIcon))
//}


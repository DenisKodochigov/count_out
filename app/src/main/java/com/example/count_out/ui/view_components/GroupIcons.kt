package com.example.count_out.ui.view_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.Dimen.sizeBetweenIcon
import com.example.count_out.ui.theme.interLight12

@Composable fun IconsCopySpeechDel(onCopy: ()->Unit, onSpeech: ()->Unit, onDel: ()->Unit ) {
    Icon(
        painter = painterResource(id = R.drawable.ic_copy),
        contentDescription = "",
        modifier = Modifier.padding(0.dp).size(Dimen.sizeIcon).clickable{ onCopy() }
    )
    Spacer(modifier = Modifier.width(sizeBetweenIcon))
    Icon(
        painter = painterResource(id = R.drawable.ic_speech),
        contentDescription = "",
        modifier = Modifier.padding(0.dp).size(Dimen.sizeIcon).clickable{ onSpeech() }
    )
    Spacer(modifier = Modifier.width(sizeBetweenIcon))
    Icon(
        painter = painterResource(id = R.drawable.ic_del),
        contentDescription = "",
        modifier = Modifier.padding(0.dp).size(Dimen.sizeIcon).clickable{ onDel() }
    )
}

@Composable fun IconsCollapsingCopySpeechDel(
    onCopy: ()->Unit,
    onSpeech: ()->Unit,
    onDel: ()->Unit,
    onCollapsing:()->Unit,
    wrap: Boolean)
{
    IconsCollapsing( onCollapsing = onCollapsing, wrap = wrap)
    Spacer(modifier = Modifier.width(sizeBetweenIcon))
    IconsCopySpeechDel(onCopy, onSpeech, onDel)
}
@Composable fun IconsCollapsing(
    onCollapsing:()->Unit,
    wrap: Boolean)
{
    val idIcon = if (wrap) R.drawable.ic_wrap1 else R.drawable.ic_wrap
    Icon(
        painter = painterResource(id = idIcon),
        contentDescription = "",
        modifier = Modifier.padding(0.dp).size(Dimen.sizeIcon).clickable{ onCollapsing() }
    )
}
@Composable
fun IconCollapsingSpeech(
    onSpeech: ()->Unit,
    onCollapsing:() -> Unit,
    idIconCollapsing: Int
){
    Icon(
        painter = painterResource(id = idIconCollapsing),
        contentDescription = "",
        modifier = Modifier.padding(4.dp).size(Dimen.sizeIcon).clickable{ onCollapsing() }
    )
    Spacer(modifier = Modifier.width(sizeBetweenIcon))
    Icon(
        painter = painterResource(id = R.drawable.ic_speech), contentDescription = "",
        modifier = Modifier.padding(4.dp).size(Dimen.sizeIcon).clickable{ onSpeech() }
    )

}
@Composable
fun IconSpeechDel(
    onSpeech: ()->Unit,
    onDelete:() -> Unit,
){
    Icon(
        painter = painterResource(id = R.drawable.ic_speech),
        contentDescription = "",
        modifier = Modifier.padding(4.dp).size(Dimen.sizeIcon).clickable{ onSpeech() }
    )
    Spacer(modifier = Modifier.width(8.dp))
    Icon(
        painter = painterResource(id = R.drawable.ic_del1), contentDescription = "",
        modifier = Modifier.padding(4.dp).size(Dimen.sizeIcon).clickable{ onDelete() }
    )
}

@Composable
fun IconSelectActivity(onClick: ()->Unit)
{
    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "",
        modifier = Modifier.padding(4.dp).size(Dimen.sizeIcon).clickable{ onClick() }
    )
}

@Composable fun IconAddItem(textId: Int, onAdd: ()->Unit){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onAdd.invoke() }
    ) {
        TextApp(
            text = stringResource(id = textId),
            style = interLight12,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "",
            modifier = Modifier.padding(4.dp).size(Dimen.sizeIcon)
        )
    }
}
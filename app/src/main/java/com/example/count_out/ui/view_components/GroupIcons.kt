package com.example.count_out.ui.view_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.ui.theme.Dimen

@Composable fun IconsCopySpeechDel(onCopy: ()->Unit, onSpeech: ()->Unit, onDel: ()->Unit ) {
    Icon(
        painter = painterResource(id = R.drawable.ic_copy),
        contentDescription = "",
        modifier = Modifier.padding(4.dp).size(Dimen.sizeIcon).clickable{ onCopy() }
    )
    Icon(
        painter = painterResource(id = R.drawable.ic_ecv),
        contentDescription = "",
        modifier = Modifier.padding(4.dp).size(Dimen.sizeIcon).clickable{ onSpeech() }
    )
    Icon(
        painter = painterResource(id = R.drawable.ic_del),
        contentDescription = "",
        modifier = Modifier.padding(4.dp).size(Dimen.sizeIcon).clickable{ onDel() }
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
        modifier = Modifier.padding(4.dp).size(Dimen.sizeIcon).clickable{ onCollapsing() }
    )
}
@Composable
fun IconCollapsingSpeech(
    onSpeech: ()->Unit,
    onCollapsing:() -> Unit,
    idIconCollapsing: Int
){
    IconButton(
        modifier = Modifier.width(24.dp).height(24.dp),
        onClick = onCollapsing )
    {
        Icon(
            painter = painterResource(id = idIconCollapsing),
            contentDescription = "",
            modifier = Modifier.padding(4.dp)
        )
    }
    Spacer(modifier = Modifier.width(8.dp))
    IconButton(
        modifier = Modifier.width(24.dp).height(24.dp),
        onClick = onSpeech )
    {
        Icon(
            painter = painterResource(id = R.drawable.ic_ecv), contentDescription = "",
            modifier = Modifier.padding(4.dp)
        )
    }
}
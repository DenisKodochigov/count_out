package com.example.count_out.ui.view_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.ui.theme.Dimen

@Composable fun GroupIcons(onCopy: ()->Unit, onSpeech: ()->Unit, onDel: ()->Unit ) {
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

@Composable fun GroupIcons4(
    onCopy: ()->Unit,
    onSpeech: ()->Unit,
    onDel: ()->Unit,
    onCollapsing:()->Unit,
    wrap: Boolean)
{
    val idIcon = if (wrap) R.drawable.ic_wrap1 else R.drawable.ic_wrap
     Icon(
        painter = painterResource(id = idIcon),
        contentDescription = "",
        modifier = Modifier.padding(4.dp).size(Dimen.sizeIcon).clickable{ onCollapsing() }
    )
    GroupIcons(onCopy, onSpeech, onDel)
}
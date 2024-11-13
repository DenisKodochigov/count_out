package com.example.count_out.ui.dialog

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.entity.UPDOWN
import com.example.count_out.ui.theme.interBold16
import com.example.count_out.ui.theme.massColor
import com.example.count_out.ui.theme.shapes
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.TextButtonOK
import com.example.count_out.ui.view_components.icons.ArrowDown
import com.example.count_out.ui.view_components.icons.ArrowLeft
import com.example.count_out.ui.view_components.icons.ArrowNoneHor
import com.example.count_out.ui.view_components.icons.ArrowNoneVer
import com.example.count_out.ui.view_components.icons.ArrowRight
import com.example.count_out.ui.view_components.icons.ArrowUp

@Composable
fun ChangeColorSectionDialog(
    colorItem: Int,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit,
){
    val color = remember { mutableIntStateOf(colorItem) }
    AlertDialog(
        modifier = Modifier.border(width = 1.dp, shape = shapes.small, color = MaterialTheme.colorScheme.primary),
        onDismissRequest = onDismiss,
        shape = shapes.small,
        confirmButton = { TextButtonOK( onConfirm = { onConfirm( color.intValue) } ) },
        title = {
            TextApp(
                text = stringResource(R.string.Workout),
                textAlign = TextAlign.Center,
                style = interBold16)
        },
        text = { ChangeColorSectionLayout(color) },
    )
}

@Composable
fun ChangeColorSectionLayout(color: MutableState<Int>)
{
    val selectColor = remember{ mutableStateOf(Color.Transparent) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row( verticalAlignment = Alignment.CenterVertically)
        {
            TextApp(
                text = stringResource(id = R.string.Workout) ,
                textAlign = TextAlign.Center, style = interBold16)
            Spacer(Modifier.width(12.dp))
            Spacer(modifier = Modifier
                .size(size = 35.dp)
                .clip(shape = CircleShape)
                .background(color = selectColor.value, shape = CircleShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                )
            )
        }
        SelectColor(doSelectedColor = { color.value = it})
    }
}

@Composable fun SelectColor( doSelectedColor: (Int) -> Unit)
{
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .verticalScroll(ScrollState(0))
    ) {

        massColor.forEach { list ->
            val lazyState = rememberLazyListState()
            val showArrowStart = remember {
                derivedStateOf { lazyState.layoutInfo.visibleItemsInfo.firstOrNull()?.index != 0 }
            }.value
            val showArrowEnd = remember {
                derivedStateOf {
                    lazyState.layoutInfo.visibleItemsInfo.lastOrNull()?.index !=
                            lazyState.layoutInfo.totalItemsCount - 1
                }
            }.value
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ShowArrowHor(
                    direction = UPDOWN.START,
                    enable = showArrowStart && list.isNotEmpty(),
                    drawLine = false
                )
                LazyRow(state = lazyState, modifier = Modifier.weight(1f)) {
                    items(list) { item ->
                        Spacer(modifier = Modifier.width(2.dp))
                        Spacer(modifier = Modifier
                            .size(size = 35.dp)
                            .clip(shape = CircleShape)
                            .background(color = item, shape = CircleShape)
                            .clickable { doSelectedColor(item.toArgb()) }
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = CircleShape
                            )
                        )
                    }
                }
                ShowArrowHor(
                    direction = UPDOWN.END,
                    enable = showArrowEnd && list.isNotEmpty(),
                    drawLine = false
                )
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable fun ShowArrowVer(enable:Boolean, direction: UPDOWN, drawLine: Boolean)
{
    Column(modifier = Modifier.fillMaxWidth()){
        if (direction == UPDOWN.UP && drawLine) HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )

        Row(modifier = Modifier.fillMaxWidth(),  horizontalArrangement = Arrangement.Center) {
            if( enable ) {
                if (direction == UPDOWN.UP) ArrowUp() else ArrowDown()
            }
            else ArrowNoneVer()
        }
        if (direction == UPDOWN.DOWN && drawLine) HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
@Composable fun ShowArrowHor(enable:Boolean, direction: UPDOWN, drawLine: Boolean)
{
    Row(modifier = Modifier.fillMaxHeight(),
        horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically)
    {
        if (direction == UPDOWN.START && drawLine){
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp), color = MaterialTheme.colorScheme.primary
            )
        }

        if( enable ) { if (direction == UPDOWN.START) ArrowLeft() else ArrowRight()
        }
        else ArrowNoneHor()
        if (direction == UPDOWN.END && drawLine){
            HorizontalDivider(modifier =
                Modifier.fillMaxHeight().width(1.dp), color = MaterialTheme.colorScheme.primary)
        }
    }
}

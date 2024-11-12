package com.example.count_out.ui.view_components.blanks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.count_out.ui.theme.AppTheme


@Composable fun Layout(){
    AppTheme {
        Column( modifier = Modifier.padding(1.dp)){
            Frame(content = {Test()})
            Spacer(modifier = Modifier.height(4.dp))
            FrameHorVer(content = {Test()}, type = true)
            Spacer(modifier = Modifier.height(4.dp))
            FrameHorVer(content = {Test()}, type = false)
        }
    }
}

@Composable
private fun Frame(
    widthBorder:Dp = 1.dp,
    shapeBorder: Dp = 8.dp,
    colorBorder:Color = Color.Gray,
    content:@Composable ()->Unit){
    Box( modifier = Modifier.clip(RoundedCornerShape(shapeBorder)).border(widthBorder, colorBorder),
        content = { Column( modifier = Modifier.padding(4.dp)) {  content() }})
}

@Composable
private fun FrameHorVer(
    widthBorder:Dp = 1.dp,
    shapeBorder: Dp = 8.dp,
    colorBorder:Color = Color.Gray,
    background: Color = MaterialTheme.colorScheme.background,
    type: Boolean = true,
    content:@Composable ()->Unit)
{
    val size: MutableState<IntSize> = remember {mutableStateOf(IntSize(0,0))}
    Box{
        Box( modifier = Modifier
            .clip(RoundedCornerShape(shapeBorder))
            .align( alignment = Alignment.Center)
            .border(widthBorder, colorBorder)
            .height( with(LocalDensity.current) {
                size.value.height.toDp() + if (type) widthBorder * 3 else 0.dp})
            .width ( with(LocalDensity.current) {
                size.value.width.toDp() + if (!type) widthBorder * 3 else 0.dp}),
            content = { })
        Box( content = { content() },
            modifier = Modifier
                .align( alignment = Alignment.Center)
                .onGloballyPositioned { size.value = it.size }
                .background(color = background))
        Text(text = " ${size.value}")

    }
}


@Composable fun Test(){
    val size1: MutableState<IntSize> = remember { mutableStateOf(IntSize(0,0)) }
    val size2: MutableState<IntSize> = remember { mutableStateOf(IntSize(0,0)) }
    val size3: MutableState<IntSize> = remember { mutableStateOf(IntSize(0,0)) }
    Column(modifier = Modifier.padding(vertical = 2.dp, horizontal = 12.dp)){
        Text( text = "", style = MaterialTheme.typography.bodyLarge,)
        Text( text = "Size 1", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.onGloballyPositioned { size1.value = it.size})
//        Text( text = "size1 = ${size1.value}", style = MaterialTheme.typography.bodyLarge)
        Text( text = "Size 2 --------------", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.onGloballyPositioned { size2.value = it.size})
//        Text( text = "size2 = ${size2.value}", style = MaterialTheme.typography.bodyLarge)
//        Text( text = "Size 3 ----------", style = MaterialTheme.typography.displayLarge, modifier = Modifier.onGloballyPositioned { size3.value = it.size})
//        Text( text = "size3 = ${size3.value}", style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview (showBackground = true)
@Composable fun PreviewLayout() { Layout()}

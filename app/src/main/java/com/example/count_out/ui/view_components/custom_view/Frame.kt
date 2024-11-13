package com.example.count_out.ui.view_components.custom_view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.count_out.ui.theme.AppTheme
import com.example.count_out.ui.theme.dividerBottomShape
import com.example.count_out.ui.theme.dividerEndShape
import com.example.count_out.ui.theme.dividerStartShape
import com.example.count_out.ui.theme.dividerTopShape


@Composable fun Layout(){
    AppTheme {
        Column( modifier = Modifier.padding(1.dp).width(150.dp).height(150.dp)){
//            Divider(Direction.Top)
//            Divider(Direction.Start)
//            Test()
//            Divider(Direction.Bottom)
//            Frame(content = {Test()})
//            Spacer(modifier = Modifier.height(4.dp))
//            FrameHorVer(content = {Test()}, type = true)
//            Spacer(modifier = Modifier.height(4.dp))
//            FrameHorVer(content = {Test()}, type = false)
            Frame1 { Test() }
        }
    }
}

enum class Direction{ Top, Start, Bottom, End }
@Composable fun Divider(direction: Direction){
    when (direction){
        Direction.Top -> { LineHorApp( dividerTopShape )}
        Direction.Start -> { LineVerApp( dividerStartShape )}
        Direction.Bottom -> { LineHorApp( dividerBottomShape )}
        Direction.End -> { LineVerApp( dividerEndShape )}
    }
}

@Composable fun Frame1(content:@Composable ()->Unit){
    Column {
        LineHorApp( dividerTopShape )
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.background(Color.Green)) {
            LineVerApp( dividerStartShape)
            Spacer(modifier = Modifier.weight(1f))
            content()
            Spacer(modifier = Modifier.weight(1f))
//            LineVerApp( dividerEndShape )
        }
        Spacer(modifier = Modifier.weight(1f))
        LineHorApp( dividerBottomShape )
    }
}

@Composable fun LineHorApp(shape: Shape, color:Color = Color.Gray){
    Box(modifier = Modifier.padding(horizontal = 4.dp)){
        Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(color = color, shape = shape)) }
}
@Composable fun LineVerApp(shape: Shape, color:Color = Color.Gray){
    Box(modifier = Modifier.padding(vertical = 4.dp)){
        Box(modifier = Modifier.fillMaxHeight().width(2.dp).background(color = color, shape = shape))}
}
@Composable
fun Frame(widthBorder:Dp = 2.dp, shapeBorder: Dp = 16.dp, colorBorder:Color = Color.Gray,
    content:@Composable ()->Unit){
    Box( modifier = Modifier
        .clip(RoundedCornerShape(shapeBorder))
        .background(color = MaterialTheme.colorScheme.background)
        .border(widthBorder, colorBorder),
        content = { Column( modifier = Modifier.padding(4.dp)) {  content() }})
}

@Composable
fun FrameHorVer(
    widthBorder:Dp = 1.dp,
    shapeBorder: Dp = 8.dp,
    colorBorder:Color = Color.Gray,
    background: Color = MaterialTheme.colorScheme.background,
    type: Boolean = true,
    fill: Boolean = false,
    content:@Composable ()->Unit)
{
    val size: MutableState<IntSize> = remember {mutableStateOf(IntSize(0,0))}

    Box{
        Box( content = { },
            modifier = Modifier
                .clip(RoundedCornerShape(shapeBorder))
                .align(alignment = Alignment.Center)
                .border(widthBorder, colorBorder)
                .height(with(LocalDensity.current) {
                    size.value.height.toDp() + if (type) widthBorder * 3 else 0.dp
                })
                .width(with(LocalDensity.current) {
                    size.value.width.toDp() + if (!type) widthBorder * 3 else 0.dp
                }),
            )
        Box( content = { content() },
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .onGloballyPositioned { size.value = it.size }
                .background(color = background))
    }
}


@Composable fun Test(){
    Column(modifier = Modifier.padding(vertical = 2.dp, horizontal = 12.dp)){
        Text( text = "test", style = MaterialTheme.typography.displayLarge,)
    }
}

@Preview (showBackground = true)
@Composable fun PreviewLayout() { Layout()}

package com.example.count_out.ui.view_components.custom_view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable fun Frame(
    color:Color = Color.Gray,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 8.dp, bottomEnd = 8.dp ),
    contour: PaddingValues = PaddingValues(top = 1.dp, start = 1.dp, end = 1.dp, bottom = 1.dp),
    content:@Composable ()->Unit)
{
    AnimatedVisibility(modifier = Modifier, visible = true) {
    Box( content = { content() },
        modifier = modifier
        .background(color, shape = shape)
        .padding(contour)// отступ от границ фрагмента
        .background( MaterialTheme.colorScheme.background ))}
}

//@Composable fun Divider(direction: Direction){
//    when (direction){
//        Direction.Top -> { LineHorApp( dividerTopShape )}
//        Direction.Start -> { LineVerApp( dividerStartShape )}
//        Direction.Bottom -> { LineHorApp( dividerBottomShape )}
//        Direction.End -> { LineVerApp( dividerEndShape )}
//    }
//}

//@Composable fun Frame1(content:@Composable ()->Unit){
//    Column {
//        LineTop( dividerTopShape )
//        Spacer(modifier = Modifier.weight(1f))
//        Row(modifier = Modifier) {
////            LineVerApp( dividerStartShape)
//            Spacer(modifier = Modifier.weight(1f))
//            content()
//            Spacer(modifier = Modifier.weight(1f))
////            LineVerApp( dividerEndShape )
//        }
//        Spacer(modifier = Modifier.weight(1f))
//        LineBottom( dividerBottomShape )
//    }
//}
//@Composable fun LineHorApp(shape: Shape, color:Color = Color.Gray){
//    Box(modifier = Modifier.padding(horizontal = 4.dp).clip(RoundedCornerShape(24.dp))){
//        Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(color = color, shape = shape)) }
//}
//@Composable fun LineTop(shape: Shape, color:Color = Color.Gray){
//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .padding(2.dp)     // отступ от границ контейнера
////        .clip(shape= RoundedCornerShape(12.dp))
////        .border(width = 2.dp, color = Color.White)
//        .background(color, shape = MaterialTheme.shapes.large)
//        .padding(top = 5.dp)    // отступ от границ фрагмента
//        .clip(shape= RectangleShape)
//        .border(width = 2.dp, color = Color.White)
//        .background(Color.White)
//        .padding(4.dp)     // отступ между границей во фрагменте и текстом
//    ){}
//}
//@Composable fun LineBottom(shape: Shape, color:Color = Color.Gray){
//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .padding(2.dp)     // отступ от границ контейнера
////        .clip(shape= RoundedCornerShape(12.dp))
////        .border(width = 2.dp, color = Color.White)
//        .background(color, shape = MaterialTheme.shapes.large)
//        .padding(bottom = 5.dp)    // отступ от границ фрагмента
//        .clip(shape= RectangleShape)
//        .border(width = 2.dp, color = Color.White)
//        .background(Color.White)
//        .padding(4.dp)     // отступ между границей во фрагменте и текстом
//    ){}
//}
//@Composable fun LineVerApp(shape: Shape, color:Color = Color.Gray){
//    Box(modifier = Modifier.padding(vertical = 4.dp)){
//        Box(modifier = Modifier.fillMaxHeight().width(2.dp).background(color = color, shape = shape))}
//}
//@Composable
//fun Frame(widthBorder:Dp = 2.dp, shapeBorder: Dp = 16.dp, colorBorder:Color = Color.Gray,
//    content:@Composable ()->Unit){
//    Box( modifier = Modifier
//        .clip(RoundedCornerShape(shapeBorder))
//        .background(color = MaterialTheme.colorScheme.background)
//        .border(widthBorder, colorBorder),
//        content = { Column( modifier = Modifier.padding(4.dp)) {  content() }})
//}

//@Composable
//fun FrameHorVer(
//    widthBorder:Dp = 1.dp,
//    shapeBorder: Dp = 8.dp,
//    colorBorder:Color = Color.Gray,
//    background: Color = MaterialTheme.colorScheme.background,
//    type: Boolean = true,
//    fill: Boolean = false,
//    content:@Composable ()->Unit)
//{
//    val size: MutableState<IntSize> = remember {mutableStateOf(IntSize(0,0))}
//
//    Box{
//        Box( content = { },
//            modifier = Modifier
//                .clip(RoundedCornerShape(shapeBorder))
//                .align(alignment = Alignment.Center)
//                .border(widthBorder, colorBorder)
//                .height(with(LocalDensity.current) {
//                    size.value.height.toDp() + if (type) widthBorder * 3 else 0.dp
//                })
//                .width(with(LocalDensity.current) {
//                    size.value.width.toDp() + if (!type) widthBorder * 3 else 0.dp
//                }),
//            )
//        Box( content = { content() },
//            modifier = Modifier
//                .align(alignment = Alignment.Center)
//                .onGloballyPositioned { size.value = it.size }
//                .background(color = background))
//    }
//}



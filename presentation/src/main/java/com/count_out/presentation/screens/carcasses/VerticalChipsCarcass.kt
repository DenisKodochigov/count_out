package com.count_out.presentation.screens.carcasses

//@Composable fun CarcassLeftList(
//    dataState: PlansState,
//    list: List<Domain>,
//    textItem: @Composable (Int)-> String,
//    onLongClick: (Domain)-> Unit = {},
//){
//    Column {
//        list.forEachIndexed { ind, item ->
//            CarcassLeftListItems(dataState = dataState,
//                item = item,
//                text = textItem(ind),
//                onLongClick = { println("Долгое нажатие")},
//                onClick = { setSelecting( dataState,item,list) })
//        }
//    }
//}

//@Composable fun CarcassLeftListItems(
//    dataState: PlansState,
//    item: Domain,
//    text: String,
//    onLongClick: () ->Unit,
//    onClick: () ->Unit,
//){
//    val selected = getSelecting(dataState, item)
//    Row( horizontalArrangement = Arrangement.Start,
//        modifier= Modifier
//            .borderMy( selected, color = colorScheme.surfaceContainerLow)
//            .combinedClickable(onLongClick = onLongClick, onClick = onClick ,)
//    ) {
//        TextApp( modifier = Modifier.width(60.dp).padding(horizontal = 2.dp),
//            textAlign = TextAlign.Center,
//            text = text,
//            style = typography.titleMedium)
//    }
//}
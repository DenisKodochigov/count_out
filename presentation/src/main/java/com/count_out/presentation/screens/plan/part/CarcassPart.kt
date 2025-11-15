//package com.count_out.presentation.screens.plan.part
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.count_out.presentation.models.Dimen.contourHor2
//import com.count_out.presentation.screens.carcasses.CarcassTitle
//import com.count_out.presentation.view_element.custom_view.Frame
//
//@Composable fun PartCarcass(
//    onCollapsing: ()->Unit,
//    nameItem: @Composable ()->Unit,
//    infoItem: @Composable ()->Unit,
//    actionItem: @Composable ()->Unit,
//    listRing: @Composable ()->Unit,
//){
//    Frame(colorAlpha = 0.8f, contour = contourHor2){
//        Column( modifier = Modifier.padding(start = 0.dp, bottom = 2.dp, top = 4.dp)){
//            CarcassTitle(
//                startIcon = {},
//                nameItem = nameItem,
//                infoItem = infoItem,
//                actionItem = actionItem,
//                onSetCollaps = onCollapsing
//            )
//            listRing()
//        }
//    }
//}
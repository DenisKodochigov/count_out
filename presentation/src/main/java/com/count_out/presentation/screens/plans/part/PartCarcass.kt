package com.count_out.presentation.screens.plans.part

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.count_out.presentation.models.Dimen.contourAll2
import com.count_out.presentation.screens.carcasses.CarcassTitle
import com.count_out.presentation.view_element.custom_view.Frame

@Composable fun PartCarcass(
    getCollaps: ()-> Boolean,
    setCollaps: ()-> Unit,
    nameItem: @Composable ()->Unit,
    infoItem: @Composable ()->Unit,
    actionItem: @Composable ()->Unit,
    listRing: @Composable ()->Unit,
){
    Frame(colorAlpha = 0.8f, contour = contourAll2,
        modifier = Modifier.padding(start = 4.dp, bottom = 12.dp, end = 4.dp)){
        Column{
            CarcassTitle(
                startIcon = {},
                nameItem = nameItem,
                infoItem = infoItem,
                actionItem = { Box(modifier = Modifier.padding(end = 8.dp)){ actionItem() } },
                onSetCollaps = setCollaps
            )
            AnimatedVisibility(modifier = Modifier.padding(horizontal = 4.dp),
                visible = getCollaps()) {
                Column { listRing() }
            }
        }
    }
}
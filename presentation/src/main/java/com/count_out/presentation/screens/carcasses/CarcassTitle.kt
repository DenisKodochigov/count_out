package com.count_out.presentation.screens.carcasses

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable fun CarcassTitle(
    startIcon: @Composable (Modifier) ->Unit,
    nameItem: @Composable () ->Unit,
    infoItem: @Composable () ->Unit,
    actionItem: @Composable () ->Unit,
    onSetCollaps: ()-> Unit,
    onChangeSequence: ()-> Unit,
){
    Row( verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(start = 6.dp)
    ){
        startIcon(Modifier.padding(start = 4.dp, end = 12.dp))
        Column(modifier = Modifier.weight(1f).padding(start = 14.dp)
            .combinedClickable(onLongClick = onChangeSequence, onClick = onSetCollaps)) {
            Row{nameItem()}
            infoItem()
        }
        actionItem()
    }
}
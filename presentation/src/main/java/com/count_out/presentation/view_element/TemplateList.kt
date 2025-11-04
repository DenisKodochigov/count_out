package com.count_out.presentation.view_element

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable fun TemplateList1(
    columnRight: @Composable () -> Unit,
    columnLeft: @Composable () -> Unit,
    button: @Composable () -> Unit
){
    var rightHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Row(modifier = Modifier.fillMaxWidth().padding(2.dp)) {
        Column( modifier = Modifier.height(rightHeight).width(IntrinsicSize.Min).padding(end = 8.dp)){
            Column(
                content = { columnLeft()},
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()))
            Column( ) { button() }
        }
        Column( content = { columnRight() },
            modifier = Modifier.weight(1f).padding(horizontal = 1.dp)
                .onGloballyPositioned { rightHeight = with(density) { it.size.height.toDp() } }
        )
    }
}


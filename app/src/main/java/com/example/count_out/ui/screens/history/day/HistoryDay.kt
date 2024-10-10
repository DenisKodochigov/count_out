package com.example.count_out.ui.screens.history.day

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.count_out.ui.screens.history.HistoryScreenState
import com.example.count_out.ui.theme.colors3
import com.example.count_out.ui.theme.elevationNull
import com.example.count_out.ui.theme.mTypography
import com.example.count_out.ui.theme.shapes
import com.example.count_out.ui.view_components.TextApp


@Composable
fun HistoryDay(uiState: HistoryScreenState, day: String) {
    CardDay(uiState, day = day)
}

@SuppressLint("DefaultLocale")
@Composable
fun CardDay(uiState: HistoryScreenState, day: String) {
    Card(
        elevation = elevationNull(),
        shape = shapes.extraSmall,
        border =  BorderStroke(1.dp, colors3.primary),
        modifier = Modifier.padding(4.dp).clickable { uiState.onClickDay(day) }
    ){
        TextApp(text = day, style = mTypography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp).width(20.dp))
    }
}


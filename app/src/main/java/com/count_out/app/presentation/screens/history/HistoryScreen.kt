package com.count_out.app.presentation.screens.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.count_out.app.presentation.theme.mTypography
import com.count_out.app.presentation.view_components.TextApp
import com.count_out.app.presentation.screens.history.day.HistoryDay
import com.count_out.app.presentation.screens.history.month.HistoryMonth
import com.count_out.app.presentation.screens.history.week.HistoryWeek
import com.count_out.domain.entity.enums.UnitTime
import com.example.count_out.ui.screens.history.year.HistoryYear

@Composable fun HistoryScreen(){
    val viewModel: HistoryViewModel = hiltViewModel()
    HistoryScreenCreateView( viewModel = viewModel )
}

@Composable fun HistoryScreenCreateView(viewModel: HistoryViewModel){
    val uiState by viewModel.historyScreenState.collectAsStateWithLifecycle()
    HistoryScreenLayout(uiState)
}

@Composable fun HistoryScreenLayout(uiState: HistoryScreenState) {
    val pagerState = rememberPagerState( pageCount = { enumValues<UnitTime>().count()})
    Column(Modifier.fillMaxSize()){
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            ContentUnitTime(uiState, page) }
        SelectUnitTime(pagerState.currentPage)
    }
}

@Composable fun ContentUnitTime(uiState: HistoryScreenState, currentPage: Int) {
    Column ( modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        TextApp("Content unit time ${UnitTime.entries[currentPage].name}", style = mTypography.bodyMedium)
        uiState.unitTime.value = UnitTime.entries[currentPage]
        when (UnitTime.entries[currentPage]){
            UnitTime.Year -> HistoryYear(uiState)
            UnitTime.Month -> HistoryMonth(uiState, 1)
            UnitTime.Week -> HistoryWeek(uiState)
            UnitTime.Days -> HistoryDay(uiState)
        }
    }
}
@Composable fun SelectUnitTime(currentPage: Int, modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.Center,
        modifier =  Modifier.wrapContentHeight().fillMaxWidth().padding(bottom = 8.dp)){
        enumValues<UnitTime>().forEachIndexed { index, unitTime ->
            TextApp(text = unitTime.name, style = mTypography.bodyMedium,
                modifier = Modifier.padding(horizontal = 12.dp),
                fontWeight = if (currentPage == index) FontWeight.Bold else FontWeight.Normal,
                textDecoration = if (currentPage == index) TextDecoration.Underline else TextDecoration.None
            )
        }
    }
}
@Preview
@Composable
fun PreviewHistoryScreen(){
    HistoryScreenLayout(HistoryScreenState())
}

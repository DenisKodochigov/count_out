package com.example.count_out.ui.screens.settings

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.R
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.entity.no_use.Workout
import com.example.count_out.ui.bottomsheet.BottomSheetAddActivity
import com.example.count_out.ui.joint.active_view.CardActivity
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.interBold14
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.theme.interReg12
import com.example.count_out.ui.theme.shapesApp
import com.example.count_out.ui.view_components.IconsCollapsing
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.drag_drop.LazyListDragDrop

@SuppressLint("UnrememberedMutableState")
@Composable fun SettingScreen( onBaskScreen:() -> Unit
){
    val viewModel: SettingViewModel = hiltViewModel()
    LaunchedEffect( key1 = true, block = { viewModel.init() })
    SettingScreenCreateView( viewModel = viewModel )
}
@Composable fun SettingScreenCreateView( viewModel: SettingViewModel
){
    val uiState = viewModel.settingScreenState.collectAsState()
    uiState.value.onDismissAddActivity = { uiState.value.showBottomSheetAddActivity.value = false }
    uiState.value.onConfirmAddActivity = { activity->
        uiState.value.onAddActivity(activity)
        uiState.value.showBottomSheetAddActivity.value = false }
    SettingScreenLayout( uiState = uiState.value)
    if (uiState.value.showBottomSheetAddActivity.value) BottomSheetAddActivity(uiState.value)
}
@Composable fun SettingScreenLayout( uiState: SettingScreenState
){
    val listItems = remember{ mutableStateOf(
        listOf("Item 0", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7",
            "Item 8", "Item 9", "Item 10", "Item 11", "Item 12", "Item 13", "Item 14", "Item 15",
            "Item 16", "Item 17", "Item 18", "Item 19", "Item 20"))}
    Column(
        Modifier
            .padding(8.dp)
            .fillMaxHeight()
//            .verticalScroll(rememberScrollState())
    ){
        ActiveSection(uiState = uiState,)
        LazyListDragDrop(
            items = listItems,
            viewItem = { item -> ElementColumBasket(item)})
    }
}

@Composable
fun <T>ElementColumBasket (item:T, modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .clip(shape = shapesApp.extraSmall)
            .heightIn(min = 24.dp, max = 56.dp)
            .fillMaxWidth()
            .background(color = Color.Gray)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        TextApp(
            text = item.toString(),
            textAlign = TextAlign.Left,
            style = interReg12,
            modifier = modifier.weight(1f)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable fun ActiveSection(uiState: SettingScreenState){

    Card ( elevation = elevationTraining(), shape = MaterialTheme.shapes.extraSmall
    ){
        Box {
            Column( modifier = Modifier.padding(start = 6.dp),)
            {
                SectionTitle(uiState = uiState)
                SectionBody(uiState = uiState)
                SectionBottom(uiState = uiState)
            }
        }
    }
}

@Composable fun SectionTitle(uiState: SettingScreenState){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp, end = 8.dp)
    ){
        TextApp(text = stringResource(id = R.string.list_activity), style = interBold14)
        Spacer(modifier = Modifier.weight(1f))
        IconsCollapsing(
            onCollapsing = { uiState.collapsingActivity.value = !uiState.collapsingActivity.value },
            wrap = uiState.collapsingActivity.value
        )
    }
}
@Composable fun SectionBody(uiState: SettingScreenState){
    Column(modifier = Modifier.padding(end = 8.dp)){
        ListActivity( uiState = uiState )
        Spacer(modifier = Modifier.height(4.dp))
    }
}
@Composable fun SectionBottom(uiState: SettingScreenState){
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth())
    {
        Spacer(modifier = Modifier.weight(1f))
        PoleAddActivity(uiState = uiState)
    }
}
@Composable fun ListActivity(uiState: SettingScreenState){

    val showActivity = uiState.collapsingActivity.value && uiState.activities.isNotEmpty()
    uiState.activities.forEach { activity ->
        AnimatedVisibility(
            modifier = Modifier.padding(top = 8.dp),
            visible = showActivity,
            content = { CardActivity(uiState, activity) }
        )
    }
}


@Composable fun PoleAddActivity(uiState: SettingScreenState){
    Row (horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                uiState.activity.value = ActivityDB()
                uiState.showBottomSheetAddActivity.value = true }
        ) {
            TextApp(
                text = stringResource(id = R.string.add_activity),
                style = interLight12,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "",
                modifier = Modifier
                    .padding(4.dp)
                    .size(Dimen.sizeIcon)
            )
        }
    }
}
@Composable fun IconStart(uiState: SettingScreenState){}
@Composable fun IconEnd(uiState: SettingScreenState){}
@Composable fun NameWorkout(item: Workout, uiState: SettingScreenState,){}
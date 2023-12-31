package com.example.count_out.ui.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.count_out.R
import com.example.count_out.data.room.tables.WorkoutDB
import com.example.count_out.entity.TagsTesting.DIALOG_EDIT_BASKET_INPUT_NAME
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.entity.TypeText
import com.example.count_out.entity.no_use.Workout
import com.example.count_out.ui.theme.shapesApp
import com.example.count_out.ui.theme.styleApp
import com.example.count_out.ui.view_components.MyOutlinedTextFieldWithoutIcon
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.TextButtonOK

@Composable
fun EditWorkoutName(
    workout: WorkoutDB,
    onConfirm: (Workout) -> Unit,
    onDismiss: () -> Unit,)
{
    val nameBasket = remember{ mutableStateOf(workout.name) }

    AlertDialog(
        onDismissRequest = onDismiss ,
        modifier = Modifier.testTag("1"),
        shape = shapesApp.small,
        title = { TextApp( text = stringResource(R.string.change_name_workout),
            style = styleApp(nameStyle = TypeText.EDIT_TEXT)) },
        text = { EditBasketNameDialogLayout(nameBasket) },
        confirmButton = {
            TextButtonOK( onConfirm = {
                workout.name = nameBasket.value
                onConfirm(workout) } )
        }
    )
}

@Composable
fun EditBasketNameDialogLayout( enterValue: MutableState<String>){
    Column {
        TextApp(text = "", style = styleApp( nameStyle = TypeText.EDIT_TEXT))
        MyOutlinedTextFieldWithoutIcon( enterValue = enterValue, typeKeyboard =  TypeKeyboard.TEXT,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(DIALOG_EDIT_BASKET_INPUT_NAME) )
    }
}
package com.example.count_out.ui.view_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.entity.TagsTesting.BUTTON_OK
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.entity.TypeText
import com.example.count_out.entity.UPDOWN
import com.example.count_out.ui.theme.colorApp
import com.example.count_out.ui.theme.interReg12
import com.example.count_out.ui.theme.shapesApp
import com.example.count_out.ui.theme.styleApp

@Composable fun HeaderScreen(text: String ) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        TextApp(text = text, style = styleApp(nameStyle = TypeText.NAME_SCREEN))
    }
}

@Composable fun TextApp(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    style: TextStyle,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = colorApp.onSurface
) {
    Text(
        text = text,
        style = style,
        maxLines = 1,
        fontWeight = fontWeight,
        overflow = TextOverflow.Ellipsis,
        textAlign = textAlign,
        modifier = modifier,
        color = color
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable fun MyOutlinedTextFieldWithoutIcon(
    modifier: Modifier,
    enterValue: MutableState<String>,
    typeKeyboard: TypeKeyboard,
    label:  @Composable (() -> Unit)? = null,
    keyboardActionsOnDone: (() -> Unit)? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current
    var enterText by remember { mutableStateOf(enterValue.value) }

    OutlinedTextField(
        modifier = modifier.background(color = colorApp.surface),
        value = enterText,
        singleLine = true,
        textStyle = styleApp(nameStyle = TypeText.EDIT_TEXT),
        label = label,
        onValueChange = {
            enterText = it
            enterValue.value = it
        },
        keyboardOptions = keyBoardOpt(typeKeyboard),
        keyboardActions = KeyboardActions(
            onDone =
            {
                localFocusManager.clearFocus()
                enterValue.value = enterText
                enterText = ""
                if (keyboardActionsOnDone != null) keyboardActionsOnDone.invoke()
                keyboardController?.hide()
            }
        ),
//        leadingIcon = { enterText = onAddIconEditText(onNewArticle,enterText) },
//        trailingIcon = { enterText = onAddIconEditText(onNewArticle,enterText) }
    )
}

//@Composable
//fun MyOutlinedTextFieldWithoutIconClearing(
//    modifier: Modifier,
//    enterValue: MutableState<String>,
//    typeKeyboard: TypeKeyboard,
//    title: String = ""
//) {
//    val localFocusManager = LocalFocusManager.current
//    var focusItem by remember { mutableStateOf(false) }
//    var enterText by remember { mutableStateOf("") }
//    enterText = if (!focusItem) enterValue.value else ""
////    val keyboardController = LocalSoftwareKeyboardController.current
//
//    Column(modifier = modifier) {
//        TextApp(
//            text = title,
//            style = styleApp(TypeText.NAME_SLIDER),
//            modifier = Modifier.padding(start = 12.dp)
//        )
//        OutlinedTextField(
//            modifier = modifier
//                .onFocusChanged { focusItem = it.isFocused }
//                .background(color = colorApp.surface),
//            value = enterText,
//            singleLine = true,
//            textStyle = styleApp(nameStyle = TypeText.EDIT_TEXT),
//            onValueChange = {
//                focusItem = false
//                enterText = it
//                enterValue.value = it
//            },
//            keyboardOptions = keyBoardOpt(typeKeyboard),
//            keyboardActions = KeyboardActions(
//                onDone = {
//                    localFocusManager.moveFocus(FocusDirection.Next)
//                    enterValue.value = enterText
////                keyboardController?.hide()
//                }
//            ),
//        )
//    }
//}

//@Composable fun showFABs(
//    startScreen: Boolean,
//    isSelected: Boolean,
//    modifier: Modifier,
//    doDeleted: ()->Unit,
//    doChangeSection: ()->Unit,
//    doUnSelected:()->Unit):Boolean
//{
//    var startScreenLocal = startScreen
//    if (isSelected) {
//        startScreenLocal = true
//        ShowFABs_(show = true,
//            modifier = modifier,
//            doDeleted = doDeleted,
//            doChangeSection = doChangeSection,
//            doUnSelected = doUnSelected)
//    } else if (startScreenLocal){
//        ShowFABs_(show = false, doDeleted = {}, doChangeSection = {}, doUnSelected = {}, modifier = modifier)
//    }
//    return startScreenLocal
//}
//
//@Composable fun ShowFABs_(
//    show: Boolean,
//    modifier: Modifier,
//    doDeleted: ()->Unit,
//    doChangeSection: ()->Unit,
//    doUnSelected:()->Unit)
//{
//    val offset = 8.dp + sizeApp(sizeElement = SizeElement.SIZE_FAB)
//    Box( modifier = modifier.height(sizeApp(SizeElement.HEIGHT_FAB_BOX))) {
////        FabAnimation(show = show, offset = 0.dp, icon = Icons.Filled.Delete, onClick = doDeleted)
////        FabAnimation(show = show, offset = offset, icon = Icons.Filled.Dns, onClick = doChangeSection)
////        FabAnimation(show = show, offset = offset * 2, icon = Icons.Filled.RemoveDone, onClick = doUnSelected)
//    }
//}

//@SuppressLint("UnrememberedMutableState")
//@Composable
//fun SwitcherButton(doChangeSorting: (SortingBy) -> Unit) {
//
//    val cornerDp = 4.dp
//
//    var sortingPosition by remember { mutableStateOf(true) }
//
//    Row( verticalAlignment = Alignment.CenterVertically,  horizontalArrangement = Arrangement.Center,
//        modifier = Modifier
//            .padding(horizontal = 12.dp)
//            .fillMaxWidth()
//            .background(color = Color.Transparent, shape = RoundedCornerShape(cornerDp))
//            .clickable {
//                if (sortingPosition) doChangeSorting(SortingBy.BY_SECTION) else doChangeSorting(
//                    SortingBy.BY_NAME
//                )
//                sortingPosition = !sortingPosition
//            }
//    ) {
//        TextApp(
//            text = stringResource(id = R.string.by_name),
//            fontWeight = if (sortingPosition) FontWeight.Bold else FontWeight.Normal,
//            style = styleApp(nameStyle = TypeText.TEXT_IN_LIST_SMALL)
//        )
//        Spacer(modifier = Modifier.width(24.dp))
//        TextApp(
//            text = stringResource(id = R.string.by_section),
//            fontWeight = if (!sortingPosition) FontWeight.Bold else FontWeight.Normal,
//            style = styleApp(nameStyle = TypeText.TEXT_IN_LIST_SMALL)
//        )
//    }
//}

@Composable fun ShowArrowVer(enable:Boolean, direction: UPDOWN, drawLine: Boolean)
{
    Column(modifier = Modifier.fillMaxWidth()){
        if (direction == UPDOWN.UP && drawLine) Divider(color = colorApp.primary, thickness = 1.dp)

        Row(modifier = Modifier.fillMaxWidth(),  horizontalArrangement = Arrangement.Center) {
            if( enable ) {
                if (direction == UPDOWN.UP) ArrowUp() else ArrowDown()
            }
            else ArrowNoneVer()
        }
        if (direction == UPDOWN.DOWN && drawLine) Divider(color = colorApp.primary, thickness = 1.dp)
    }
}
@Composable fun ShowArrowHor(enable:Boolean, direction: UPDOWN, drawLine: Boolean)
{
    Row(modifier = Modifier.fillMaxHeight(),
        horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically)
    {
        if (direction == UPDOWN.START && drawLine){
            Divider(color = colorApp.primary, modifier = Modifier
                .fillMaxHeight()
                .width(1.dp))
        }

        if( enable ) { if (direction == UPDOWN.START) ArrowLeft() else ArrowRight()}
        else ArrowNoneHor()
        if (direction == UPDOWN.END && drawLine){
            Divider(color = colorApp.primary, modifier = Modifier
                .fillMaxHeight()
                .width(1.dp))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable fun TextFieldApp(
    modifier: Modifier = Modifier,
    textAlign: TextAlign,
    enterValue: MutableState<String>,
    typeKeyboard: TypeKeyboard,
    textStyle:TextStyle ,
    onChangeValue:(String)->Unit = {}
){
    val keyboardController = LocalSoftwareKeyboardController.current
    var focusItem by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = enterValue.value,
        enabled = true,
        singleLine = true,
        maxLines = 1,
        modifier = modifier.onFocusChanged { focusItem = it.isFocused }.focusable(),
        keyboardOptions = keyBoardOpt(typeKeyboard),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
            log(true, "BasicTextField enterValue: ${enterValue.value}")
            onChangeValue(enterValue.value)
            keyboardController?.hide() }),
        onValueChange = { enterValue.value = it },
        textStyle = textStyle,
        decorationBox = {
            Row( verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)){ it() }
        },
    )
}
@Composable fun TextFieldAppBorder(
    modifier: Modifier = Modifier,
    textAlign: TextAlign,
    enterValue: MutableState<String>,
    typeKeyboard: TypeKeyboard
){
    TextFieldApp(
        textAlign = textAlign,
        enterValue = enterValue,
        textStyle = interReg12,
        typeKeyboard = typeKeyboard,
        modifier = Modifier.background(color = colorApp.surfaceVariant, shape = shapesApp.extraSmall)
            .border(width = 1.dp, color = colorApp.onPrimaryContainer, shape = shapesApp.extraSmall)
    )
}

@Composable fun ButtonApp(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier =Modifier,
    enabled: Boolean = true,
){
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors (
            containerColor= colorApp.tertiaryContainer,
            contentColor = colorApp.onTertiaryContainer ,
            disabledContainerColor = colorApp.surface,
            disabledContentColor = colorApp.onSurface
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 0.dp,
            focusedElevation = 8.dp,
            hoveredElevation = 6.dp,
            disabledElevation= 6.dp
        ),
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Composable fun ButtonCircle(modifier: Modifier, iconButton: ImageVector, onClick: () -> Unit) {
    val radius = 25.dp
    IconButton (
        modifier = modifier
            .clip(RoundedCornerShape(radius, radius, radius, radius))
            .size(60.dp),
        onClick = { onClick() }) {
        Icon(
            imageVector = iconButton, null,
            tint = colorApp.primary ,
            modifier = Modifier.size(60.dp))
    }
}
@Composable
fun TextButtonOK(onConfirm: () -> Unit, enabled: Boolean = true) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(onClick = onConfirm, enabled = enabled) {
            TextApp(
                text = stringResource(R.string.ok),
                modifier = Modifier.testTag(BUTTON_OK),
                style = styleApp(nameStyle = TypeText.TEXT_IN_LIST)
            )
        }
    }
}
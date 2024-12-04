package com.example.count_out.ui.view_components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.entity.TagsTesting.BUTTON_OK
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.mTypography


@Composable fun ButtonApp(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
){
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors (
            containerColor= MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer ,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 0.dp,
            focusedElevation = 8.dp,
            hoveredElevation = 6.dp,
            disabledElevation= 6.dp
        ),
        modifier = modifier,
    ) {
        Text(text = text)
    }
}

@Composable fun TextButtonOK(onConfirm: () -> Unit, enabled: Boolean = true)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(onClick = onConfirm, enabled = enabled) {
            TextApp(
                text = stringResource(R.string.ok),
                modifier = Modifier.testTag(BUTTON_OK),
                style = mTypography.titleMedium
            )
        }
    }
}
@Composable fun TextFieldApp(
    modifier: Modifier = Modifier,
    typeKeyboard: TypeKeyboard,
    textStyle: TextStyle,
    contentAlignment:Alignment = Alignment.BottomCenter,
    placeholder: String = "",
    showLine: Boolean = true,
    onLossFocus: Boolean = true,
    maxLines: Int = 1,
    onChangeValue:(String)->Unit = {},
    edit: Boolean = false,
    colorLine: Color = MaterialTheme.colorScheme.surfaceBright,
    width: Dp = 30.dp
){
    val widthField = remember {mutableStateOf(0f)}
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var text by rememberSaveable { mutableStateOf(if (edit) placeholder else "") }
//    val interactionSource = remember { MutableInteractionSource() }
//    val enabled = typeKeyboard != TypeKeyboard.NONE
//    val enabled = edit
    BasicTextField(
        value = text,
        enabled = edit, //enabled,
        singleLine = maxLines == 1,
        maxLines = maxLines,
        textStyle = LocalTextStyle.current.merge(textStyle.copy(color = LocalContentColor.current,)),
//        interactionSource = interactionSource,
        visualTransformation = VisualTransformation.None,
        modifier = modifier
//            .width(IntrinsicSize.Min)
            .focusable()
            .onGloballyPositioned { widthField.value = it.size.width.toFloat()}
            .onFocusChanged {
                if ((!it.isFocused || !onLossFocus) && text.isNotEmpty()) { onChangeValue(text) } },
        keyboardOptions = keyBoardOpt(typeKeyboard),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
            onChangeValue(text)
            keyboardController?.hide() }),
        onValueChange = {
            text = it
            if (!onLossFocus) onChangeValue(text)},
        decorationBox = {
            Row( verticalAlignment = Alignment.CenterVertically){
                Box( contentAlignment = contentAlignment,
                    modifier = Modifier
//                        .widthIn(min = width)
                        .drawBehind {
                            val y = size.height - 1.dp.toPx() / 2
                            if (edit && showLine) {
                                drawLine(
                                    color = colorLine,
                                    start = Offset(0f, y),
                                    end = Offset(widthField.value, y),
                                    strokeWidth = 1.dp.toPx()) } }
                ){
                    if (text.isEmpty()) Text(text = if (edit) "" else placeholder, style = textStyle)
                    it()
                }
            }
        },
    )
}
@Composable fun ButtonConfirm(onConfirm: ()->Unit)
{
    Spacer(modifier = Modifier.height(Dimen.bsItemPaddingVer))
    ButtonApp(text = stringResource(id = R.string.ok), onClick = onConfirm )
}
@Composable fun ButtonsOkCancel(onConfirm: ()->Unit, onDismiss: ()->Unit)
{
    Spacer(modifier = Modifier.height(Dimen.bsItemPaddingVer))
    Row(horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 24.dp)){
        ButtonApp(text = stringResource(id = R.string.yes), onClick = onConfirm )
        Spacer(Modifier.weight(1f))
        ButtonApp(text = stringResource(id = R.string.no), onClick = onDismiss)
    }
}

//@Composable fun TextStringAndField(
//    text:String,
//    fieldTextAlign: TextAlign = TextAlign.Center,
//    placeholder: String,
//    onChangeValue:(String)->Unit = {},
//    editing: Boolean,
//){
//    Row(verticalAlignment = Alignment.CenterVertically) {
//        TextApp(
//            text = text,
//            modifier = Modifier.weight(1f),
//            maxLines = 2,
//            style = interReg12,
//            textAlign = TextAlign.Start,
//        )
//        TextFieldApp(
//            placeholder = placeholder,
//            typeKeyboard = if (editing) TypeKeyboard.DIGIT else TypeKeyboard.NONE,
//            modifier = Modifier.width(50.dp),
//            textStyle = interLight12.copy(textAlign = fieldTextAlign),
//            onChangeValue = onChangeValue,
//        )
//    }
//}
//@Composable fun RadioButtonApp(
//    radioButtonId: Int,
//    state: Int,
//    onClick:()->Unit,
//    contextRight: @Composable ()->Unit,
//    contextBottom: @Composable ()->Unit
//){
//    val sizeRadioButton = 12.dp
//    Column(){
//        Row(verticalAlignment = Alignment.CenterVertically)
//        {
//            RadioButton(
//                selected = radioButtonId == state,
//                enabled = true,
//                onClick = onClick,
//                modifier = Modifier
//                    .size(sizeRadioButton)
//                    .scale(0.8f),
//                colors = RadioButtonDefaults.colors(
//                    selectedColor = MaterialTheme.colorScheme.onPrimary,
//                    unselectedColor = MaterialTheme.colorScheme.onPrimary
//                )
//            )
//            Spacer(modifier = Modifier.width(6.dp))
//            contextRight()
//        }
//    }
//    contextBottom()
//}
//@Composable fun MyOutlinedTextFieldWithoutIcon(
//    modifier: Modifier,
//    enterValue: MutableState<String>,
//    typeKeyboard: TypeKeyboard,
//    label:  @Composable (() -> Unit)? = null,
//    keyboardActionsOnDone: (() -> Unit)? = null
//) {
//    val keyboardController = LocalSoftwareKeyboardController.current
//    val localFocusManager = LocalFocusManager.current
//    var enterText by remember { mutableStateOf(enterValue.value) }
//
//    OutlinedTextField(
//        modifier = modifier.background(color = MaterialTheme.colorScheme.surface),
//        value = enterText,
//        singleLine = true,
//        textStyle = mTypography.titleMedium,
//        label = label,
//        onValueChange = {
//            enterText = it
//            enterValue.value = it
//        },
//        keyboardOptions = keyBoardOpt(typeKeyboard),
//        keyboardActions = KeyboardActions(
//            onDone =
//            {
//                localFocusManager.clearFocus()
//                enterValue.value = enterText
//                enterText = ""
//                keyboardActionsOnDone?.invoke()
//                keyboardController?.hide()
//            }
//        ),
//    )
//}
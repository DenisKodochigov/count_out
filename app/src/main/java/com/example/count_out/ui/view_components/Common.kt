package com.example.count_out.ui.view_components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.entity.TagsTesting.BUTTON_OK
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.theme.interReg12
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
@Composable fun TextStringAndField(
    text:String,
    fieldTextAlign: TextAlign = TextAlign.Center,
    placeholder: String,
    onChangeValue:(String)->Unit = {},
    editing: Boolean,
    visibleField: Boolean = true
){
    Row(verticalAlignment = Alignment.CenterVertically) {
        TextApp(
            text = text,
            modifier = Modifier.weight(1f),
            maxLines = 2,
            style = interReg12,
            textAlign = TextAlign.Start,
        )
        TextFieldApp(
            placeholder = placeholder,
            typeKeyboard = if (editing) TypeKeyboard.DIGIT else TypeKeyboard.NONE,
            modifier = Modifier.width(50.dp),
            textStyle = interLight12.copy(textAlign = fieldTextAlign),
            onChangeValue = onChangeValue,
            visible = visibleField
        )
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
    visible: Boolean = true,
    width: Dp = 40.dp
){

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    var text by rememberSaveable { mutableStateOf(if (edit) placeholder else "") }
    val enabled = typeKeyboard != TypeKeyboard.NONE
    val colorLine = if (visible) MaterialTheme.colorScheme.outline else Color.Transparent
    val mergedStyle = LocalTextStyle.current.merge(textStyle.copy(color = LocalContentColor.current,))
    BasicTextField(
        value = text,
        enabled = enabled,
        singleLine = maxLines == 1,
        maxLines = maxLines,
        textStyle = mergedStyle,
        interactionSource = interactionSource,
        visualTransformation = VisualTransformation.None,
        modifier = modifier
            .width(IntrinsicSize.Min)
            .focusable()
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
                        .widthIn(min = width)
                        .drawBehind {
                            val strokeWidth = 1.dp.toPx()
                            val y = size.height - strokeWidth / 2
                            if (enabled && showLine) {
                                drawLine(
                                    color = colorLine,
                                    start = Offset(0f, y),
                                    end = Offset(size.width, y),
                                    strokeWidth = strokeWidth) } }
                ){
                    if (text.isEmpty()) {
                        Text(text = if (edit || !visible) "" else placeholder, style = textStyle)
                    }
                    it()
                }
            }
        },
    )
}
@Composable fun RadioButtonApp(
    radioButtonId: Int,
    state: Int,
    onClick:()->Unit,
    contextRight: @Composable ()->Unit,
    contextBottom: @Composable ()->Unit
){
    val sizeRadioButton = 12.dp
    Column(){
        Row(verticalAlignment = Alignment.CenterVertically)
        {
            RadioButton(
                selected = radioButtonId == state,
                enabled = true,
                onClick = onClick,
                modifier = Modifier
                    .size(sizeRadioButton)
                    .scale(0.8f),
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedColor = MaterialTheme.colorScheme.onPrimary
                )
            )
            Spacer(modifier = Modifier.width(6.dp))
            contextRight()
        }
    }
    contextBottom()
}
@Composable
fun ButtonConfirm(onConfirm: ()->Unit)
{
    Spacer(modifier = Modifier.height(Dimen.bsItemPaddingVer))
    ButtonApp(text = stringResource(id = R.string.ok), onClick = onConfirm )
}
@Composable
fun ButtonsOkCancel(onConfirm: ()->Unit, onDismiss: ()->Unit)
{
    Spacer(modifier = Modifier.height(Dimen.bsItemPaddingVer))
    Row(horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 24.dp)){
        ButtonApp(text = stringResource(id = R.string.yes), onClick = onConfirm )
        Spacer(Modifier.weight(1f))
        ButtonApp(text = stringResource(id = R.string.no), onClick = onDismiss)
    }
}

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
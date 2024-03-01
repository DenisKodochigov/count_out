package com.example.count_out.ui.view_components

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.count_out.R
import com.example.count_out.entity.TagsTesting.BUTTON_OK
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.entity.TypeText
import com.example.count_out.ui.theme.colorApp
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.theme.interReg12
import com.example.count_out.ui.theme.styleApp

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
                keyboardActionsOnDone?.invoke()
                keyboardController?.hide()
            }
        ),
//        leadingIcon = { enterText = onAddIconEditText(onNewArticle,enterText) },
//        trailingIcon = { enterText = onAddIconEditText(onNewArticle,enterText) }
    )
}

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
                style = styleApp(nameStyle = TypeText.TEXT_IN_LIST)
            )
        }
    }
}
@Composable fun TextStringAndField(
    text:String,
    fieldTextAlign: TextAlign = TextAlign.Center,
    placeholder: String,
    onChangeValue:(String)->Unit = {},
    editing: Boolean
){
    Row(verticalAlignment = Alignment.CenterVertically){
        TextApp(
            text = text,
            style = interReg12,
            textAlign = TextAlign.Start,)
        TextFieldApp(
            placeholder = placeholder,
            typeKeyboard = if (editing) TypeKeyboard.DIGIT else TypeKeyboard.NONE,
            modifier = Modifier.width(50.dp),
            textStyle = interLight12.copy(textAlign = fieldTextAlign),
            onChangeValue = onChangeValue
        )
    }
}
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable fun TextFieldApp(
    modifier: Modifier = Modifier,
    typeKeyboard: TypeKeyboard,
    textStyle:TextStyle ,
    contentAlignment:Alignment = Alignment.BottomCenter,
    placeholder: String = "",
    showLine: Boolean = true,
    onLossFocus: Boolean = true,
    maxLines: Int = 1,
    onChangeValue:(String)->Unit = {},
    edit: Boolean = false
){
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    var text by if (edit) rememberSaveable { mutableStateOf(placeholder) }
                    else rememberSaveable { mutableStateOf("") }
    val enabled = typeKeyboard != TypeKeyboard.NONE
    val paddingHor = if (textStyle.fontSize > 14.sp) 8.dp else 4.dp
    val paddingVer = if (textStyle.fontSize > 14.sp) 6.dp else 2.dp
    val colorLine = MaterialTheme.colorScheme.onBackground
    val colors = TextFieldDefaults.colors(
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent)

    BasicTextField(
        value = text,
        enabled = enabled,
        singleLine = maxLines == 1,
        maxLines = maxLines,
        modifier = modifier
            .indicatorLine(
                enabled = enabled,
                isError = false,
                colors = colors,
                interactionSource = interactionSource,
                focusedIndicatorLineThickness = 0.dp,
                unfocusedIndicatorLineThickness = 0.dp
            )
            .onFocusChanged { if ((!it.isFocused || !onLossFocus) && text.isNotEmpty()) onChangeValue(text) }
            .focusable(),
        keyboardOptions = keyBoardOpt(typeKeyboard),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
            onChangeValue(text)
            keyboardController?.hide() }),
        onValueChange = {
            text = it
            if (!onLossFocus) onChangeValue(text)},
        visualTransformation = VisualTransformation.None,
        textStyle = textStyle,
//        interactionSource = interactionSource,
        decorationBox = {
            Row( verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(horizontal = paddingHor, vertical = paddingVer)
            ) {
                Box(contentAlignment = contentAlignment,
                    modifier = modifier
                        .drawBehind {
                            val strokeWidth = 1 * density
                            val y = size.height - strokeWidth / 2
                            if (enabled && showLine) {
                                drawLine(
                                    color = colorLine,
                                    start = Offset(0f, y),
                                    end = Offset(size.width, y),
                                    strokeWidth = strokeWidth
                                )
                            }
                        }
                )
                {
                    if (text.isEmpty()) Text( text = if (edit) "" else placeholder, style = textStyle)
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
    context: @Composable ()->Unit
){
    val sizeRadioButton = 0.dp
    Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Start)
    {
        RadioButton(
            selected = radioButtonId == state,
            enabled = true,
            onClick = onClick,
            modifier = Modifier
                .size(sizeRadioButton)
                .scale(1f)
                .padding(8.dp),
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.onPrimary,
                unselectedColor = MaterialTheme.colorScheme.onPrimary
            ))
        Spacer(modifier = Modifier.width(24.dp))
        context()
    }
}


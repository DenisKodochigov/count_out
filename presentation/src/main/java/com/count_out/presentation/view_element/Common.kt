package com.count_out.presentation.view_element

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.models.TypeKeyboard


@Composable fun ButtonApp(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
){
    val color = with( MaterialTheme.colorScheme.outline){
        Color(red, green, blue, alpha = if (enabled) alpha else alpha * 0.6f)}
    val colorDisable = with( MaterialTheme.colorScheme.outline){
        Color(red, green, blue, alpha = alpha * 0.6f)}
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors (
            containerColor= color,
            contentColor = MaterialTheme.colorScheme.outlineVariant ,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = colorDisable
        ),
//        elevation = ButtonDefaults.buttonElevation(
//            defaultElevation = 6.dp,
//            pressedElevation = 0.dp,
//            focusedElevation = 8.dp,
//            hoveredElevation = 6.dp,
//            disabledElevation= 6.dp
//        ),
        modifier = modifier,
    ) {
        Text(text = text)
    }
}

@Composable fun TextButtonOK(onConfirm: () -> Unit, enabled: Boolean = true){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ){
        TextButton(onClick = onConfirm, enabled = enabled) {
            TextApp(
                text = stringResource(R.string.ok),
//                modifier = Modifier.testTag(BUTTON_OK),
                style = MaterialTheme.typography.titleMedium
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
    beginValueZero: Boolean = false,
    colorLine: Color = MaterialTheme.colorScheme.surfaceBright,
){
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var text by rememberSaveable { mutableStateOf(if (!beginValueZero) placeholder else "") }
    BasicTextField(
        value = text,
        enabled = edit, //enabled,
        singleLine = maxLines == 1,
        maxLines = maxLines,
        textStyle = LocalTextStyle.current.merge(textStyle.copy(color = LocalContentColor.current,)),
        visualTransformation = VisualTransformation.None,
        modifier = modifier
            .width(IntrinsicSize.Max)
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
                    modifier = modifier.drawBehind {
                            val y = size.height - 1.dp.toPx() / 2
                            if (edit && showLine) {
                                drawLine(
                                    color = colorLine,
                                    start = Offset(0f, y),
                                    end = Offset(size.width, y),
                                    strokeWidth = 1.dp.toPx()) } }
                ){
                    if (text.isEmpty()) Text(text = if (!beginValueZero) "" else placeholder, style = textStyle)
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

package com.example.count_out.ui.view_components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.example.count_out.entity.models.TypeKeyboard

@Composable fun keyBoardOpt(typeKeyboard: TypeKeyboard): KeyboardOptions {
    return when (typeKeyboard) {
        TypeKeyboard.TEXT -> {
            KeyboardOptions(keyboardType = KeyboardType.Password,
                capitalization = KeyboardCapitalization.Sentences).copy(imeAction = ImeAction.Done) }
        TypeKeyboard.DIGIT -> {
            KeyboardOptions(keyboardType = KeyboardType.Decimal).copy(imeAction = ImeAction.Done) }
        TypeKeyboard.PASS -> {
            KeyboardOptions(keyboardType = KeyboardType.Password).copy(imeAction = ImeAction.Done) }
        else -> { KeyboardOptions.Default.copy(imeAction = ImeAction.Done) }
    }
}
package com.example.count_out.entity

import androidx.compose.runtime.MutableState
import com.example.count_out.entity.no_use.Workout

interface BottomSheetInterface {

    val articles: MutableState<List<Workout>>
    val sections: MutableState<List<Round>>
    val unitApp: MutableState<List<Set>>
    val selectedProduct: MutableState<Workout?>
    val enteredNameProduct: MutableState<String>
    val selectedSection: MutableState<Round?>
    val enteredNameSection: MutableState<String>
    val selectedUnit: MutableState<Set?>
    val enteredNameUnit: MutableState<String>
    val enteredAmount: MutableState<String>
    val buttonDialogSelectArticleProduct: MutableState<Boolean>
    val buttonDialogSelectSection: MutableState<Boolean>
    val buttonDialogSelectUnit: MutableState<Boolean>
    var onDismissSelectArticleProduct:() -> Unit
    var onDismissSelectSection:() -> Unit
    var onDismissSelectUnit:() -> Unit
    var onConfirmationSelectArticleProduct:(BottomSheetInterface) -> Unit
    var onConfirmationSelectSection:(BottomSheetInterface) -> Unit
    var oConfirmationSelectUnit:(BottomSheetInterface) -> Unit
    val weightButton: Float
    var onConfirmation: (Workout) -> Unit
}

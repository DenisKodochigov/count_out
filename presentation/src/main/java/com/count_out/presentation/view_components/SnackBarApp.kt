package com.count_out.presentation.view_components

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun ShowSnackBar(
    message: String,
    actionLabel: String,
    actionPerformed: ()->Unit,
){
    val snackBarHostState = SnackbarHostState()
    LaunchedEffect(key1 = Unit) {
        when (
            snackBarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite,)
        ) {
            SnackbarResult.Dismissed -> {}
            SnackbarResult.ActionPerformed -> { actionPerformed() }
        }
    }
}



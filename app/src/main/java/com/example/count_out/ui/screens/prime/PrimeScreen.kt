package com.example.count_out.ui.screens.prime

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable fun <T : Any> PrimeScreen(loader: ScreenState<T>, onSuccess: @Composable (T) -> Unit) {
    when (loader) {
        is ScreenState.Loading -> { Loading() }
        is ScreenState.Error -> { Error(loader.errorMessage) }
        is ScreenState.Success -> { onSuccess(loader.dataState) }
    }
}

@Composable
fun Error(errorMessage: String) {
    Column( modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom,
        content = { Snackbar { Text(text = "CommonScreen $errorMessage") } })
}

@Composable
fun Loading() {
    Column( modifier = Modifier.fillMaxSize(),content =  { CircularProgressIndicator() },
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,)
}
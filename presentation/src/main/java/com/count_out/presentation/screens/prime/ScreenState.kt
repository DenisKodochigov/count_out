package com.count_out.presentation.screens.prime

sealed class ScreenState<out T: Any> {
    data object Loading: ScreenState<Nothing>()
    data class Error(val errorMessage: String) : ScreenState<Nothing>()
    data class Success<T: Any>(val dataState: T) : ScreenState<T>()
}
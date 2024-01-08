package com.example.count_out.ui.view_components

import android.util.Log
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.unit.IntOffset

fun log(showLog: Boolean, text: String){
    if (showLog) Log.d("KDS", text)
}

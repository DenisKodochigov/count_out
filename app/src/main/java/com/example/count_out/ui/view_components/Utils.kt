package com.example.count_out.ui.view_components

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

fun log(showLog: Boolean, text: String){
    if (showLog) Log.d("KDS", text)
}
@Composable
fun ToastApp (text: String){
    Toast.makeText(LocalContext.current, text, Toast.LENGTH_SHORT).show()
}
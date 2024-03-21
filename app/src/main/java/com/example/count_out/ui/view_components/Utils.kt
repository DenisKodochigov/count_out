package com.example.count_out.ui.view_components

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

fun log(showLog: Boolean, text: String){
    if (showLog) Log.d("KDS", text)
}
fun lg( text: String){
    Log.d("KDS", text)
}
@Composable
fun ToastApp (text: String){
    Toast.makeText(LocalContext.current, text, Toast.LENGTH_SHORT).show()
}
fun String.toDoubleMy(): Double {
    return if (this.isNotEmpty()) this.toDouble() else 0.0
}
fun String.toIntMy(): Int {
    return if (this.isNotEmpty()) this.toInt() else 0
}
fun Double.toPositive(): Double{
    return if (this < 0) 0.0 else this
}
fun String.toNumeric(): Double{
    var result = ""
    return if (this.isNotEmpty()){
        this.forEach { char->
            if (char in '0'..'9' || char == '.') result += char
        }
        if (result.isNotEmpty()) result.toDouble() else 0.0
    } else 0.0
}
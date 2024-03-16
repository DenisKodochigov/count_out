package com.example.count_out.ui.view_components

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.count_out.entity.Set
import com.example.count_out.entity.Training
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

fun log(showLog: Boolean, text: String){
    if (showLog) Log.d("KDS", text)
}

fun logC( text: String, flow: MutableStateFlow<Training>){
    val coroutine = CoroutineScope(Dispatchers.Default)
    coroutine.launch {
        flow.collect {
            val set: Set = it.rounds[0].exercise[0].sets[0]
            Log.d("KDS", text + "${set.intervalReps}")
        }
    }
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
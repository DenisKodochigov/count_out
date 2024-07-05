package com.example.count_out.ui.view_components

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

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

fun <T>lgF(flow: Flow<T>){
    runBlocking { flow.collect{ lg("BleService.startScannerBLEDevices valOut.listDevice: $it") } } }

fun <T>lgF(flow: Flow<List<T>>, text: String){
    runBlocking {
        flow.collect{
            if ( !it.isNullOrEmpty()) lg("$text ${it[0]}") } } }
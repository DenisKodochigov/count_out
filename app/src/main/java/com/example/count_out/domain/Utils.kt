package com.example.count_out.domain

import android.content.Context
import android.content.Context.VIBRATOR_MANAGER_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatTime(
    seconds: String, minutes: String, hours: String
): String = "$hours:$minutes:$seconds"

fun Int.pad(): String = this.toString().padStart(2, '0')
fun String.toDoubleMy(): Double = if (this.isNotEmpty()) this.toDouble() else 0.0
fun String.toIntMy(): Int = if (this.isNotEmpty()) this.toInt() else 0
fun Double.toPositive(): Double = if (this < 0) 0.0 else this
fun String.toNumeric(): Double{
    var result = ""
    return if (this.isNotEmpty()){
        this.forEach { char->
            if (char in '0'..'9' || char == '.') result += char
        }
        if (result.isNotEmpty()) result.toDouble() else 0.0
    } else 0.0
}
fun Long.getTime(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(date)
}
fun vibrate(context: Context){
    val vibrateApp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService( VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(VIBRATOR_SERVICE) as Vibrator
    }
    vibrateApp.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE) )
}
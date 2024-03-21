package com.example.count_out.domain

fun formatTime(
    seconds: String, minutes: String, hours: String
): String = "$hours:$minutes:$seconds"

fun Int.pad(): String = this.toString().padStart(2, '0')
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
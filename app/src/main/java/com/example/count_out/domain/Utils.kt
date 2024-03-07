package com.example.count_out.domain

fun formatTime(
    seconds: String, minutes: String, hours: String
): String = "$hours:$minutes:$seconds"

fun Int.pad(): String = this.toString().padStart(2, '0')
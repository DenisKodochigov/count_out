package com.count_out.domain.entity

sealed class Setting{
    data class SpeechDescription(val value: Boolean): Setting()
    data class BleAddress(val value: String): Setting()
    data class BleName(val value: String): Setting()
}
package com.count_out.domain.entity

sealed class Settings {
    data class SpeechDescription(val item: Boolean) : Settings()
    data class AddressBle(val item: String) : Settings()
    data class NameBle(val item: String) : Settings()
}
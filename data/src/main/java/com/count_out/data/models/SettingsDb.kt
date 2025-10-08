package com.count_out.data.models

sealed class SettingsDb: Data {
    data class SpeechDescription(val item: Boolean) : SettingsDb()
    data class AddressBle(val item: String) : SettingsDb()
    data class NameBle(val item: String) : SettingsDb()
}
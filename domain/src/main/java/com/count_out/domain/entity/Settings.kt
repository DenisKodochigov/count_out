package com.count_out.domain.entity

import com.count_out.domain.entity.workout.Domain

sealed class Settings: Domain {
    data class SpeechDescription(val item: Boolean) : Settings()
    data class AddressBle(val item: String) : Settings()
    data class NameBle(val item: String) : Settings()
}
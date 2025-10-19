package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.workout.Domain

sealed class SettingsDb: Data {
    data class SpeechDescription(val item: Boolean) : SettingsDb() {
        override fun toDomain(ind: Int): Domain = object: Domain {}
        companion object {
            fun fromDomain(domain: Domain): SettingsDb.SpeechDescription {
                return if (domain is Settings.SpeechDescription) {
                    SettingsDb.SpeechDescription(domain.item)
                } else throw IllegalArgumentException("Unsupported domain type")
            }
        }
    }
    data class AddressBle(val item: String) : SettingsDb() {
        override fun toDomain(ind: Int): Domain = object: Domain {}
        companion object {
            fun fromDomain(domain: Domain): SettingsDb.AddressBle {
                return if (domain is Settings.AddressBle) {
                    SettingsDb.AddressBle(domain.item)
                } else throw IllegalArgumentException("Unsupported domain type")
            }
        }
    }
    data class NameBle(val item: String) : SettingsDb() {
        override fun toDomain(ind: Int): Domain = object: Domain {}
        companion object {
            fun fromDomain(domain: Domain): SettingsDb.NameBle {
                return if (domain is Settings.NameBle) {
                    SettingsDb.NameBle(domain.item)
                } else throw IllegalArgumentException("Unsupported domain type")
            }
        }
    }
}
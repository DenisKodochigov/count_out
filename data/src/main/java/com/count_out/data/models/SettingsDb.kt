package com.count_out.data.models

import com.count_out.domain.entity.workout.Domain

sealed class SettingsDb: Data {
    data class SpeechDescription(val item: Boolean) : SettingsDb() {
        override fun toResultData(): ResultData<Data> = ResultData.Success(this)
        override fun toDomain(ind: Int): Domain = object: Domain{}
    }
    data class AddressBle(val item: String) : SettingsDb() {
        override fun toResultData(): ResultData<Data> = ResultData.Success(this)
        override fun toDomain(ind: Int): Domain = object: Domain{}
    }
    data class NameBle(val item: String) : SettingsDb() {
        override fun toResultData(): ResultData<Data> = ResultData.Success(this)
        override fun toDomain(ind: Int): Domain = object: Domain{}
    }
}
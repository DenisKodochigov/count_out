package com.count_out.data.models

import com.count_out.data.models.types_data.LongDb
import com.count_out.data.models.types_data.StringDb
import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.types_domai.StringDm
import com.count_out.domain.entity.workout.Domain

interface Data {
    fun toResultData(): ResultData<Data>
    fun toDomain(ind: Int = 0): Domain

    companion object {
        fun toData(domain: Domain): Data {
            return when (domain::class) {
                StringDm::class -> StringDb((domain as StringDm).item) as Data
                LongDm::class -> LongDb((domain as LongDm).item) as Data
//                List<SpeechDb>::class->listOf(it.toDomain() as )
                else -> error("Unsupported type: ${domain::class}")
            }
        }
    }
}
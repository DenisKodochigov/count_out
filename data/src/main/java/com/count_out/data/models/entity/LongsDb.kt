package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.domain.entity.workout.Domain

@JvmInline
value class LongsDb(val item: List<Long>): Data {
    override fun toDomain(ind: Int): Domain = object: Domain {}
}
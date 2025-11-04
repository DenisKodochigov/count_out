package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.domain.entity.supportive.NameId
import com.count_out.domain.entity.workout.Domain

data class NameIdDb(val name: String, val id: Long): Data {
    override fun toDomain(ind: Int): Domain = object: Domain {}
    companion object {
        fun fromDomain(domain: Domain): NameIdDb {
            return when (domain) {
                is NameId -> NameIdDb (name = domain.name, id = domain.id)
                else -> EMPTY
            }
        }
        val EMPTY = NameIdDb (name = "", id = 0)
    }
}
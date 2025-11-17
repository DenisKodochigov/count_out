package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.domain.entity.SetViewId
import com.count_out.domain.entity.workout.Domain

interface SetViewIdDb: Data {
    val owner: Domain?
    val idOwner: Long
    val from: Int
    val to: Int
    override fun toDomain(ind: Int): Domain = object: Domain {}
    companion object {
        fun fromDomain(domain: Domain): SetViewIdDb {
            return if (domain is SetViewId) {
                object: SetViewIdDb{
                    override val idOwner: Long = domain.idOwner
                    override val from: Int = domain.from
                    override val to: Int = domain.to
                    override val owner: Domain? = domain.owner
                }} else EMPTY
        }
        val EMPTY = object: SetViewIdDb{
            override val idOwner: Long = 0
            override val from: Int = 0
            override val to: Int = 0
            override val owner: Domain? = null
        }
    }
}
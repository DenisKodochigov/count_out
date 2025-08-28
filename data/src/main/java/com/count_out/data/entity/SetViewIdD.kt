package com.count_out.data.entity

import com.count_out.domain.entity.SetViewId

class SetViewIdD (
    val roundId: Long = 0,
    val from: Int = 0,
    val to: Int = 0,
){
    constructor(item: SetViewId): this(
        item.roundId,
        item.from,
        item.to
    )
}
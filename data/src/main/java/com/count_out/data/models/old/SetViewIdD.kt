package com.count_out.data.models.old

import com.count_out.domain.entity.SetViewId

class SetViewIdD (
    val ringId: Long = 0,
    val from: Int = 0,
    val to: Int = 0,
){
    constructor(item: SetViewId): this(
        item.ringId,
        item.from,
        item.to
    )
}
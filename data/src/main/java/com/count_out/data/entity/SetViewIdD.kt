package com.count_out.data.entity

import com.count_out.domain.entity.SetViewId

class SetViewIdD (
    val roundId: Long = 0,
    val viewId: Long = 0,
    val newViewId: Long = 0,
){
    constructor(item: SetViewId): this(
        item.roundId,
        item.viewId,
        item.newViewId
    )
}
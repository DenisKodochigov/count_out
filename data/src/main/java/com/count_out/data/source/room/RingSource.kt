package com.count_out.data.source.room

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData

interface RingSource {
    fun del(ring: Data): ResultData<Data>
    fun insert(ring: Data): ResultData<Data>
    fun update(ring: Data): ResultData<Data>
    fun changeSequence(setViewId: Data): ResultData<Data>
}

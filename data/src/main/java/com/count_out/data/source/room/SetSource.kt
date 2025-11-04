package com.count_out.data.source.room

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData

interface SetSource {
    fun insert(set: Data): ResultData<Data>
    fun del( set: Data): ResultData<Data>
    fun update( set: Data): ResultData<Data>
}
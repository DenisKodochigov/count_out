package com.count_out.data.source.room

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData

interface RingSource {
    fun del(ring: Data): ResultData<Data>
    fun copy(ring: Data): ResultData<Data>
    fun update(ring: Data): ResultData<Data>
}
//    fun get(round: TypeSource): Flow<ResultSource<TypeSource>>
//    fun gets(trainingId: TypeSource): Flow<ResultSource<TypeSource>>
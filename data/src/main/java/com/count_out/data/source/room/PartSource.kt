package com.count_out.data.source.room

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData

interface PartSource {
    fun del(part: Data): ResultData<Data>
    fun copy(part: Data): ResultData<Data>
    fun update(part: Data): ResultData<Data>
}
//    fun get(round: TypeSource): Flow<ResultSource<TypeSource>>
//    fun gets(trainingId: TypeSource): Flow<ResultSource<TypeSource>>
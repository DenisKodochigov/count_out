package com.count_out.service.service_count_out

import com.count_out.data.models.Data
import com.count_out.data.models.throwable.ResultData
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.models.types_data.BooleanDb
import com.count_out.data.source.services.CountOutServiceSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountOutServiceSourceImpl @Inject constructor(
    private val service: CountOutServiceBind
): CountOutServiceSource {
    override fun bind(): Flow<ResultData<Data>> {
        service.bindService()
        return flow { emit(ResultData.Success(BooleanDb(service.isBound)))}
    }
    override fun unbind(): Flow<ResultData<Data>> {
        service.unbindService()
        return flow { emit(ResultData.Success(BooleanDb(service.isBound)))}
    }
}
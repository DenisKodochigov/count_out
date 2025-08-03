package com.count_out.service.service_count_out

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.services.CountOutServiceSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountOutServiceSourceImpl @Inject constructor(
    private val service: CountOutServiceBind
): CountOutServiceSource {
    override fun bind(): Flow<ResultSource<TypeSource>> {
        service.bindService()
        return flow { emit(ResultSource.Success(TypeSource.BooleanT(service.isBound)))}
    }
    override fun unbind(): Flow<ResultSource<TypeSource>> {
        service.unbindService()
        return flow { emit(ResultSource.Success(TypeSource.BooleanT(service.isBound)))}
    }
}
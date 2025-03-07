package com.example.count_out.services.count_out

import com.example.count_out.data.source.services.CountOutServiceSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountOutServiceSourceImpl @Inject constructor(
    private val service: CountOutServiceBind
): CountOutServiceSource {
    override fun bind(): Flow<Boolean> {
        service.bindService()
        return flow { emit(service.isBound) }
    }
    override fun unbind(): Flow<Boolean> {
        service.unbindService()
        return flow { emit(service.isBound) }
    }
}
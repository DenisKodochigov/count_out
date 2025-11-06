package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.workout.Domain

@JvmInline
value class BleDataMapDb(val item: Map<String, DeviceBle>): Data {
    fun toResultData(): ResultData<Data> =
        if (this.item.isNotEmpty()) ResultData.Success(this)
        else ResultData.Error(ThrowableDS.ErrorBleIsEmpty())
    override fun toDomain(ind: Int): Domain = object: Domain {}
}
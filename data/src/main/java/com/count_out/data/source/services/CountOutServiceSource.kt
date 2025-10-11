package com.count_out.data.source.services

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import kotlinx.coroutines.flow.Flow

interface CountOutServiceSource {
    fun bind(): Flow<ResultData<Data>>
    fun unbind(): Flow<ResultData<Data>>
}
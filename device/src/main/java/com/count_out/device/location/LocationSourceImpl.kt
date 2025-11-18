package com.count_out.device.location

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.source.framework.LocationSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationSourceImpl @Inject constructor(private val site: LocationWithOutGoogle): LocationSource {
    override fun getLocation(): Flow<ResultData<Data>> = site.startService().map { ResultData.Success(it) }
    override fun stopService(): Flow<ResultData<Data>> = site.cancelService()

}
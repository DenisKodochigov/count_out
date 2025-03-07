package com.example.count_out.data.repository

import com.example.count_out.data.source.framework.LocationSource
import com.example.count_out.domain.repository.LocationRepo
import javax.inject.Inject

class LocationRepoImpl @Inject constructor(private val locationSource: LocationSource):
    LocationRepo {
    override fun getLocation() {
        TODO("Not yet implemented")
    }
}
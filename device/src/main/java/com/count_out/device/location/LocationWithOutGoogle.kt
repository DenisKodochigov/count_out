package com.count_out.device.location

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Looper
import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.entity.BooleanDb
import com.count_out.data.models.entity.CoordinateDb
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.device.permission.PermissionApp
import com.count_out.domain.entity.lg
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LocationWithOutGoogle @Inject constructor(
     private val locationManager : LocationManager,
     val permission: PermissionApp
){
    val coordinate: MutableStateFlow<CoordinateDb> = MutableStateFlow(CoordinateDb.EMPTY)
    private val listener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            lg("location ${location.longitude} ${location.latitude}")
            coordinate.value = CoordinateDb.new(
                latitude = location.latitude,
                longitude = location.longitude,
                altitude = location.altitude,
                timeLocation = location.time,
                accuracy = location.accuracy,
//                    distance = location.distanceTo(),
                speed = location.speed
            )
        }
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
    private fun requestLocation(){
        try {
//            if (permission.checkLocation()) { lg("NO PERMISSION") }
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 500L, 0f, listener, Looper.getMainLooper())
        } catch(ex: SecurityException) {
            lg("Security Exception, no location available $ex")
        }
    }
    fun startService(): Flow<CoordinateDb> {
        requestLocation()
        return coordinate
    }
    fun cancelService(): Flow<ResultData<Data>>{
        return flowOf( try {
            locationManager.removeUpdates(listener)
            ResultData.Success(BooleanDb(true))
        } catch (e: Exception) { ResultData.Error(throwable = ThrowableDS.extract(e))})
    }
}
package com.count_out.device.location

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import com.count_out.domain.entity.router.models.DataFromSite
import javax.inject.Inject

class LocationWithOutGoogle @Inject constructor(val context: Context,
//                                                val permission: PermissionApp
){

    private lateinit var locationManager : LocationManager
    private lateinit var locationListener: LocationListener

    private fun initService(dataFromSite: DataFromSite){
        locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
//                dataFromSite.coordinate.value = TemporaryDB(
//                    latitude = location.latitude,
//                    longitude = location.longitude,
//                    altitude = location.altitude,
//                    timeLocation = location.time,
//                    accuracy = location.accuracy,
//                    speed = location.speed
//                )
            }
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
    }
    private fun getLocation(){
        try {
//            locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
//            lg("Location Request Successful")
        } catch(ex: SecurityException) {
//            lg("Security Exception, no location available")
        }
    }
    fun startService(dataFromSite: DataFromSite){
        initService(dataFromSite)
        getLocation()
    }
    fun cancelService(){
        locationManager.removeUpdates(locationListener)
    }
}
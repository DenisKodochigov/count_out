package com.example.count_out.service_count_out.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.example.count_out.data.room.tables.CoordinateDB
import com.example.count_out.entity.router.DataForSite
import com.example.count_out.entity.router.DataFromSite
import com.example.count_out.permission.PermissionApp
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import javax.inject.Inject

class Site @Inject constructor(val context: Context, val permission: PermissionApp) {

    private lateinit var locationListener: LocationListener
    private lateinit var locationRequest: LocationRequest
    private val fusedClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun start(dataForSite: DataForSite, dataFromSite: DataFromSite){
        locationRequest = LocationRequest.Builder(1000L).apply {
            this.setGranularity(Granularity.GRANULARITY_FINE)
//            this.setMinUpdateDistanceMeters(1F)
//            this.setMaxUpdateDelayMillis(your_choice)
            this.setWaitForAccurateLocation(false)
            this.setMinUpdateIntervalMillis(500)
            this.setMaxUpdateDelayMillis(1000)
        }.build()
        locationListener = LocationListener { location ->
            dataFromSite.coordinate.value = CoordinateDB(
                latitude = location.latitude,
                longitude = location.longitude,
                altitude = location.altitude,
                time = location.time,
                accuracy = location.accuracy,
                speed = location.speed
            )
        }
        if (!permission.checkLocation()) return
        fusedClient.requestLocationUpdates(locationRequest, locationListener, Looper.getMainLooper())
    }

    @SuppressLint("MissingPermission")
    fun stop(){
        if(permission.checkLocation()) fusedClient.removeLocationUpdates(locationListener)
    }
}

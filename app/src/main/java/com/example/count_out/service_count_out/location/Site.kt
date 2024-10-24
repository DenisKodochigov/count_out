package com.example.count_out.service_count_out.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Looper
import com.example.count_out.data.room.tables.TemporaryDB
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
    fun start( dataFromSite: DataFromSite){
        locationRequest = LocationRequest.Builder(1000L).apply {
            this.setGranularity(Granularity.GRANULARITY_FINE)
//            this.setMinUpdateDistanceMeters(1F)
//            this.setMaxUpdateDelayMillis(your_choice)
            this.setWaitForAccurateLocation(false)
            this.setMinUpdateIntervalMillis(500)
            this.setMaxUpdateDelayMillis(1000)
        }.build()
        locationListener = LocationListener { location ->
            dataFromSite.coordinate.value = TemporaryDB(
                latitude = location.latitude,
                longitude = location.longitude,
                altitude = location.altitude,
                timeLocation = location.time,
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
    fun getAddressFromLocation(latitude: Double, longitude: Double): String{
        val geocoder = Geocoder(context)
        var list: MutableList<Address>? = mutableListOf()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(latitude, longitude, 1
            ) { addresses -> list = addresses }
        } else {
            list = geocoder.getFromLocation(latitude, longitude, 1)
        }
        val address = if (list.isNullOrEmpty()) "" else {
            list!![0].locality + ", " + list!![0].countryName
        }
        return address
    }
}

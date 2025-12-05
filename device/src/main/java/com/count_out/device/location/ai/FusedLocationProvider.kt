package com.count_out.device.location.ai

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class FusedLocationProvider @Inject constructor(context: Context) : LocationProvider {
    private val client = LocationServices.getFusedLocationProviderClient(context)

    override val locationFlow = callbackFlow {
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000L).build()

        val cb = object : LocationCallback() {
            override fun onLocationResult(res: LocationResult) {
                res.lastLocation?.let {
                    trySend(
                        LocationData(
                            it.latitude,
                            it.longitude,
                            System.currentTimeMillis()
                        )
                    )
                }
            }
        }

        @SuppressLint("MissingPermission")
        client.requestLocationUpdates(request, cb, null)

        awaitClose { client.removeLocationUpdates(cb) }
    }

    override suspend fun start() {}
    override suspend fun stop() { client.removeLocationUpdates(object : LocationCallback() {}) }
}
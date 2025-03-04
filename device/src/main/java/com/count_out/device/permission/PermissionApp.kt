package com.count_out.device.permission

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.BLUETOOTH
import android.Manifest.permission.BLUETOOTH_SCAN
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import javax.inject.Inject

class PermissionApp @Inject constructor (val context: Context) {

    fun checkBleScan(granted: () -> Any): Any? = check(BLUETOOTH_SCAN, 31, granted)
    fun checkBle(granted: () -> Any): Any? = check(BLUETOOTH, 28, granted)
    fun checkLocation(): Boolean {
        return (activityCheck(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                activityCheck(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) }
    private fun check(permission: String, requiredBuild: Int, granted: () -> Any = {}): Any? {
        return if (Build.VERSION.SDK_INT < requiredBuild ||
            activityCheck(permission) == PackageManager.PERMISSION_GRANTED){ granted() } else null }
    private fun activityCheck(permission: String) = ActivityCompat.checkSelfPermission(context, permission)
}

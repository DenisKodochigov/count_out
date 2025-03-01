package com.count_out.app.permission

import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.Manifest.permission.ACTIVITY_RECOGNITION
import android.Manifest.permission.BLUETOOTH
import android.Manifest.permission.BLUETOOTH_ADMIN
import android.Manifest.permission.BLUETOOTH_ADVERTISE
import android.Manifest.permission.BLUETOOTH_CONNECT
import android.Manifest.permission.BLUETOOTH_SCAN
import android.Manifest.permission.CHANGE_NETWORK_STATE
import android.Manifest.permission.CHANGE_WIFI_MULTICAST_STATE
import android.Manifest.permission.CHANGE_WIFI_STATE
import android.Manifest.permission.FOREGROUND_SERVICE
import android.Manifest.permission.FOREGROUND_SERVICE_CONNECTED_DEVICE
import android.Manifest.permission.FOREGROUND_SERVICE_DATA_SYNC
import android.Manifest.permission.INTERNET
import android.Manifest.permission.NFC
import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
import android.Manifest.permission.TRANSMIT_IR
import android.Manifest.permission.UWB_RANGING
import android.Manifest.permission.VIBRATE
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import javax.inject.Inject

//class PermissionApp @Inject constructor (val context: Context) {
//
//    fun checkBleScan(granted: () -> Any): Any? = check(BLUETOOTH_SCAN, 31, granted)
//    fun checkBle(granted: () -> Any): Any? = check(BLUETOOTH, 28, granted)
//    fun checkLocation(): Boolean {
//        return (activityCheck(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
//                activityCheck(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) }
//    private fun check(permission: String, requiredBuild: Int, granted: () -> Any = {}): Any? {
//        return if (Build.VERSION.SDK_INT < requiredBuild ||
//            activityCheck(permission) == PackageManager.PERMISSION_GRANTED){ granted() } else null }
//    private fun activityCheck(permission: String) = ActivityCompat.checkSelfPermission(context, permission)
//}


@Composable
fun RequestPermissionsAll(){
    val permissions1 = listOf(
        ACCESS_COARSE_LOCATION,
        ACCESS_FINE_LOCATION,
        INTERNET,
        ACCESS_NETWORK_STATE,
        FOREGROUND_SERVICE,                     //with 28
        FOREGROUND_SERVICE_DATA_SYNC,           //with 34
        FOREGROUND_SERVICE_CONNECTED_DEVICE,    //with 34
        POST_NOTIFICATIONS,                     //with 33
        ACTIVITY_RECOGNITION,                   //with 29
        VIBRATE,
        REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
        CHANGE_NETWORK_STATE,
        CHANGE_WIFI_STATE,
        CHANGE_WIFI_MULTICAST_STATE,
        NFC,
        TRANSMIT_IR,
        UWB_RANGING,                             //with 31
        BLUETOOTH,
        BLUETOOTH_SCAN,                          //with 31
        BLUETOOTH_ADMIN,
        BLUETOOTH_CONNECT,                      //with 31
        BLUETOOTH_ADVERTISE,                    //with 31
    )
    val permissions2 = listOf(ACCESS_BACKGROUND_LOCATION)

    RequestPermissions(permissions1)
    RequestPermissions(permissions2)
//    Const.permissions1.forEach{ PrintPermission(it) }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissions(permissions: List<String>){
    val permissionsState = rememberMultiplePermissionsState(permissions = permissions)
    LaunchedEffect(key1 = Unit ){ permissionsState.launchMultiplePermissionRequest() }
    if (!permissionsState.allPermissionsGranted || permissionsState.shouldShowRationale) {
//        lg("permissions not granted")
    }

//@Composable fun PrintPermission(permission: String){
//    val permissionState = rememberPermissionState(permission = permission)
//    lg("permission ${permission.padStart(55, ' ')}; " +
//            "granted: ${permissionState.status.isGranted.toString().padStart(5, ' ')}; " +
//            "rationale: ${permissionState.status.shouldShowRationale}")
//}

}
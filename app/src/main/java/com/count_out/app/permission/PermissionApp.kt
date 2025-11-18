package com.count_out.app.permission

import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.Manifest.permission.ACTIVITY_RECOGNITION
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
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.count_out.domain.entity.lg
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

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
        BLUETOOTH_SCAN,                          //with 31
        BLUETOOTH_CONNECT,                      //with 31
        BLUETOOTH_ADVERTISE,                    //with 31
    )
    val permissions2 = listOf(ACCESS_BACKGROUND_LOCATION)

    RequestPermissions(listOf(ACCESS_COARSE_LOCATION))
    RequestPermissions(listOf(ACCESS_FINE_LOCATION))
    RequestPermissions(listOf(INTERNET))
    RequestPermissions(listOf(ACCESS_NETWORK_STATE))
    RequestPermissions(listOf(FOREGROUND_SERVICE))
    RequestPermissions(listOf(FOREGROUND_SERVICE_DATA_SYNC))
    RequestPermissions(listOf(FOREGROUND_SERVICE_CONNECTED_DEVICE))
    RequestPermissions(listOf(POST_NOTIFICATIONS))
    RequestPermissions(listOf(ACTIVITY_RECOGNITION))
    RequestPermissions(listOf(VIBRATE))
    RequestPermissions(listOf(REQUEST_IGNORE_BATTERY_OPTIMIZATIONS))
    RequestPermissions(listOf(CHANGE_NETWORK_STATE))
    RequestPermissions(listOf(CHANGE_WIFI_STATE))
    RequestPermissions(listOf(CHANGE_WIFI_MULTICAST_STATE))
    RequestPermissions(listOf(NFC))
    RequestPermissions(listOf(TRANSMIT_IR))
    RequestPermissions(listOf(UWB_RANGING))
    RequestPermissions(listOf(BLUETOOTH_SCAN))
    RequestPermissions(listOf(BLUETOOTH_CONNECT))
    RequestPermissions(listOf(BLUETOOTH_ADVERTISE))
    RequestPermissions(listOf(ACCESS_BACKGROUND_LOCATION))
//    Const.permissions1.forEach{ PrintPermission(it) }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissions(permissions: List<String>){
    val context = LocalContext.current
    val permissionsState = rememberMultiplePermissionsState(permissions = permissions)
    LaunchedEffect(key1 = Unit ){ permissionsState.launchMultiplePermissionRequest() }
    if (!permissionsState.allPermissionsGranted || permissionsState.shouldShowRationale) {
        if (ACCESS_BACKGROUND_LOCATION in permissions){
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                ("package:" + context.packageName).toUri()
            )
        }
        lg("RequestPermissions permissions not granted $permissions")
    }

//@Composable fun PrintPermission(permission: String){
//    val permissionState = rememberPermissionState(permission = permission)
//    lg("permission ${permission.padStart(55, ' ')}; " +
//            "granted: ${permissionState.status.isGranted.toString().padStart(5, ' ')}; " +
//            "rationale: ${permissionState.status.shouldShowRationale}")
//}
}
@Composable fun ReqPermission(){
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        if (perms[ACCESS_FINE_LOCATION] == true || perms[ACCESS_COARSE_LOCATION] == true) {
            lg("ReqPermission permissions not granted")
        }
    }

    LaunchedEffect(Unit) { launcher.launch(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)) }
}
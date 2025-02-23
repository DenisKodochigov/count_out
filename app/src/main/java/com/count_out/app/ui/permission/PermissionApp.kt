package com.count_out.app.ui.permission

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.BLUETOOTH
import android.Manifest.permission.BLUETOOTH_SCAN
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import com.count_out.app.ui.view_components.lg
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

//class PermissionApp (val context: Context) {
//
//    fun checkBleScan(granted: () -> Any): Any? = check(BLUETOOTH_SCAN, 31, granted)
//
//    fun checkBle(granted: () -> Any): Any? = check(BLUETOOTH, 28, granted)
//
//    fun checkLocation(): Boolean {
//        return (activityCheck(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
//                activityCheck(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//    }
//
//    private fun check(permission: String, requiredBuild: Int, granted: () -> Any = {}): Any? {
//        return if (Build.VERSION.SDK_INT < requiredBuild ||
//            activityCheck(permission) == PackageManager.PERMISSION_GRANTED){ granted() } else null
//    }
//    private fun activityCheck(permission: String) = ActivityCompat.checkSelfPermission(context, permission)
//}
//
//@Composable
//fun RequestPermissionsAll(){
//    RequestPermissions(Const.permissions1)
//    RequestPermissions(Const.permissions2)
////    Const.permissions1.forEach{ PrintPermission(it) }
//}
//
//@OptIn(ExperimentalPermissionsApi::class)
//@Composable fun PrintPermission(permission: String){
//    val permissionState = rememberPermissionState(permission = permission)
//    lg("permission ${permission.padStart(55, ' ')}; " +
//            "granted: ${permissionState.status.isGranted.toString().padStart(5, ' ')}; " +
//            "rationale: ${permissionState.status.shouldShowRationale}")
//}
//
//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun RequestPermissions(permissions: List<String>){
//    val permissionsState = rememberMultiplePermissionsState(permissions = permissions)
//    LaunchedEffect(key1 = Unit ){ permissionsState.launchMultiplePermissionRequest() }
//    if (!permissionsState.allPermissionsGranted || permissionsState.shouldShowRationale) {
//        lg("permissions not granted")
//    }
//}

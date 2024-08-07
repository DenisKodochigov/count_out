package com.example.count_out.permission

import android.Manifest.permission.BLUETOOTH_SCAN
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import com.example.count_out.entity.Const
import com.example.count_out.ui.view_components.lg
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

class PermissionApp (val context: Context) {
    @Composable
    fun RequestPermissions(){
        RequestPermission(Const.permissions1)
        Const.permissions1.forEach{ CheckPermission(it) }
//        RequestPermission(Const.permissions2)
//        Const.permissions2.forEach{ CheckPermission(it) }
    }
    @OptIn(ExperimentalPermissionsApi::class)
    @SuppressLint("MissingPermission")
    @Composable
    fun RequestPermission(permissions: List<String>){
        val permissionsState = rememberMultiplePermissionsState(permissions = permissions)
        LaunchedEffect(key1 = Unit ){ permissionsState.launchMultiplePermissionRequest() }
        if (!permissionsState.allPermissionsGranted || permissionsState.shouldShowRationale) {
            lg("permissions not granted")
        }
    }
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable fun CheckPermission(permission: String){
    val permissionState = rememberPermissionState(permission = permission)
    lg("permission ${permission.padStart(50, ' ')}; " +
            "granted: ${permissionState.status.isGranted.toString().padStart(5, ' ')}; " +
            "rationale: ${permissionState.status.shouldShowRationale}")
}
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun checkPermission(permission: String, requiredBuild: Int, granted: @Composable ()-> String): String{
        val permissionsState = rememberPermissionState(permission = permission)
        return if ( Build.VERSION.SDK_INT < requiredBuild || permissionsState.status.isGranted) { granted() } else ""
    }
    private fun check(permission: String, requiredBuild: Int, granted:()-> Any): Any?{
        return if ( Build.VERSION.SDK_INT < requiredBuild ||
            ActivityCompat.checkSelfPermission( context, permission) == PackageManager.PERMISSION_GRANTED
        ) { granted() } else null
    }
    fun checkBleScan(granted:()-> Any): Any? = check(BLUETOOTH_SCAN, 31, granted)
}
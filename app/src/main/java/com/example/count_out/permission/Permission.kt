package com.example.count_out.permission

import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.example.count_out.MainActivity
import com.example.count_out.ui.view_components.ToastApp
import com.example.count_out.ui.view_components.lg
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(permission: String, requiredSDK: Int){
    if (Build.VERSION.SDK_INT >= requiredSDK) {
        val permissionState = rememberPermissionState(permission = permission)
        LaunchedEffect(key1 = Unit ){ permissionState.launchPermissionRequest() }
        if (!permissionState.status.isGranted){
            lg("permission $permission ${permissionState.status.shouldShowRationale}")
            if (permissionState.status.shouldShowRationale) {
                Toast(LocalContext.current, )
            } else {
                ToastApp("Please, get permission on $permission.")
            }
        }
    }
}
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission1(activity: MainActivity, permission: String, requiredSDK: Int){
    if (Build.VERSION.SDK_INT >= requiredSDK) {
        lg("RequestPermission1 permission $permission ")
        val permissionState = rememberPermissionState(permission = permission)
        LaunchedEffect(key1 = Unit ){ permissionState.launchPermissionRequest() }
        if (!permissionState.status.isGranted){
            lg("permission $permission ${permissionState.status.shouldShowRationale}")
            if (permissionState.status.shouldShowRationale) {
                Toast(LocalContext.current, )
            } else {
//                ToastApp("Please, get permission on $permission.")
                lg("ActivityCompat.requestPermissions $permission")
                ActivityCompat.requestPermissions(activity, arrayOf(permission), 1)
            }
        }
    }
}

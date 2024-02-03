package com.example.count_out.permission

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.count_out.ui.view_components.ToastApp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(permission: String){
    val permissionState = rememberPermissionState(permission = permission)
    LaunchedEffect(key1 = Unit ){
        permissionState.launchPermissionRequest()
    }

    if (!permissionState.status.isGranted){
        if (permissionState.status.shouldShowRationale) {
            Toast(LocalContext.current, )
        } else {
            ToastApp("Please, get permission on notification.")
        }
    }
}
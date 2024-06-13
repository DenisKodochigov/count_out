package com.example.count_out.permission

import android.Manifest.permission.BLUETOOTH_SCAN
import android.os.Build
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

//@Composable
//fun RequestPermissions(){
//    RequestPermission(permissions1)
//    permissions1.forEach{ CheckPermission(it) }
//    RequestPermission(permissions2)
//    permissions2.forEach{ CheckPermission(it) }
//}
//@OptIn(ExperimentalPermissionsApi::class)
//@SuppressLint("MissingPermission")
//@Composable
//fun RequestPermission(permissions: List<String>){
//    val permissionsState = rememberMultiplePermissionsState(permissions = permissions)
//    LaunchedEffect(key1 = Unit ){ permissionsState.launchMultiplePermissionRequest() }
//    if (!permissionsState.allPermissionsGranted || permissionsState.shouldShowRationale) {
////        ShowSnackBar(
////            message = "Missing required permissions",
////            actionLabel = "Grant",
////            actionPerformed = { permissionsState.launchMultiplePermissionRequest() }
////        )
//    }
//}


//@OptIn(ExperimentalPermissionsApi::class)
//@Composable fun CheckPermission(permission: String){
//    val permissionState = rememberPermissionState(permission = permission)
//    lg("permission ${permission.padStart(50, ' ')}; " +
//            "granted: ${permissionState.status.isGranted.toString().padStart(5, ' ')}; " +
//            "rationale: ${permissionState.status.shouldShowRationale}")
//}
//fun checkPermission(context: Context, permission: String, requiredBuild: Int, granted:()-> Any): Any?{
//    return if ( Build.VERSION.SDK_INT < requiredBuild ||
//        ActivityCompat.checkSelfPermission( context, permission) == PackageManager.PERMISSION_GRANTED
//    ) { granted() } else null
//
//}
@Composable
fun checkBleScan(): Boolean = checkPermission(BLUETOOTH_SCAN, 31,)
@OptIn(ExperimentalPermissionsApi::class)
@Composable fun checkPermission( permission: String, requiredBuild: Int): Boolean{
    return Build.VERSION.SDK_INT < requiredBuild ||
            rememberPermissionState(permission = permission).status.isGranted
}

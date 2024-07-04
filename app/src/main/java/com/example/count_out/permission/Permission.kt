package com.example.count_out.permission

import android.Manifest
import android.Manifest.permission.BLUETOOTH_SCAN
import android.content.Context
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
/**
 * Determine whether the current [Context] has been granted the relevant [Manifest.permission].
 */
//fun Context.hasPermission(permissionType: String): Boolean {
//    return ContextCompat.checkSelfPermission(this, permissionType) ==
//            PackageManager.PERMISSION_GRANTED
//}
///**
// * Determine whether the current [Context] has been granted the relevant permissions to perform
// * Bluetooth operations depending on the mobile device's Android version.
// */
//fun Context.hasRequiredBluetoothPermissions(): Boolean {
//    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//        hasPermission(BLUETOOTH_SCAN) && hasPermission(Manifest.permission.BLUETOOTH_CONNECT)
//    } else {
//        hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//    }
//}
//
//private fun startBleScan() {
//    if (!hasRequiredBluetoothPermissions()) {
//        requestRelevantRuntimePermissions()
//    } else { /* TODO: Actually perform scan */ }
//}
//private fun Activity.requestRelevantRuntimePermissions() {
//    if (hasRequiredBluetoothPermissions()) { return }
//    when {
//        Build.VERSION.SDK_INT < Build.VERSION_CODES.S -> {
//            requestLocationPermission()
//        }
//        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            requestBluetoothPermissions()
//        }
//    }
//}
//private fun requestLocationPermission() = runOnUiThread {
//    AlertDialog.Builder(this)
//        .setTitle("Location permission required")
//        .setMessage(
//            "Starting from Android M (6.0), the system requires apps to be granted " +
//                    "location access in order to scan for BLE devices."
//        )
//        .setCancelable(false)
//        .setPositiveButton(android.R.string.ok) { _, _ ->
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                PERMISSION_REQUEST_CODE
//            )
//        }
//        .show()
//}
//@RequiresApi(Build.VERSION_CODES.S)
//private fun requestBluetoothPermissions() = runOnUiThread {
//    AlertDialog.Builder(this)
//        .setTitle("Bluetooth permission required")
//        .setMessage(
//            "Starting from Android 12, the system requires apps to be granted " +
//                    "Bluetooth access in order to scan for and connect to BLE devices."
//        )
//        .setCancelable(false)
//        .setPositiveButton(android.R.string.ok) { _, _ ->
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(
//                    Manifest.permission.BLUETOOTH_SCAN,
//                    Manifest.permission.BLUETOOTH_CONNECT
//                ),
//                PERMISSION_REQUEST_CODE
//            )
//        }
//        .show()
//}
//
//override fun onRequestPermissionsResult(
//    requestCode: Int,
//    permissions: Array<out String>,
//    grantResults: IntArray
//) {
//    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    if (requestCode != PERMISSION_REQUEST_CODE) return
//    val containsPermanentDenial = permissions.zip(grantResults.toTypedArray()).any {
//        it.second == PackageManager.PERMISSION_DENIED &&
//                !ActivityCompat.shouldShowRequestPermissionRationale(this, it.first)
//    }
//    val containsDenial = grantResults.any { it == PackageManager.PERMISSION_DENIED }
//    val allGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
//    when {
//        containsPermanentDenial -> {
//            // TODO: Handle permanent denial (e.g., show AlertDialog with justification)
//            // Note: The user will need to navigate to App Settings and manually grant
//            // permissions that were permanently denied
//        }
//        containsDenial -> {
//            requestRelevantRuntimePermissions()
//        }
//        allGranted && hasRequiredBluetoothPermissions() -> {
//            startBleScan()
//        }
//        else -> {
//            // Unexpected scenario encountered when handling permissions
//            recreate()
//        }
//    }
//}


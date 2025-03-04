package com.count_out.app

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import com.count_out.app.permission.RequestPermissionsAll
import com.count_out.app.presentation.StartApp
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RequestPermissionsAll()
            StartApp()
        }
    }
    override fun onStart() {
        super.onStart()
        ignoreBatteryOptimisation()
    }
    @SuppressLint("BatteryLife")
    private fun ignoreBatteryOptimisation(){
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        intent.data = Uri.parse("package:" + this.packageName)
        ActivityCompat.startActivityForResult(this, intent, 1, null)
    }
}

//    private fun checkBluetoothEnable(): Boolean {
//        if (ActivityCompat.checkSelfPermission(this,BLUETOOTH) == PackageManager.PERMISSION_GRANTED){
//            if ( !bluetoothAdapter.isEnabled) {
//                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//                ActivityCompat.startActivityForResult(this, enableBtIntent, 1, null)
//            }
//        }
//        return bluetoothAdapter.isEnabled
//    }
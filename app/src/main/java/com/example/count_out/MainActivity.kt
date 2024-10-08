package com.example.count_out

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import com.example.count_out.entity.MessageApp
import com.example.count_out.permission.RequestPermissionsAll
import com.example.count_out.service_count_out.CountOutServiceBind
import com.example.count_out.ui.StartApp
import com.example.count_out.ui.view_components.lg
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity()
{
    @Inject lateinit var bluetoothAdapter: BluetoothAdapter
    @Inject lateinit var messageApp: MessageApp
    @Inject lateinit var serviceBind: CountOutServiceBind

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
        serviceBind.bindService()
        ignoreBatteryOptimisation()
        if ( !checkBluetoothEnable()) messageApp.errorApi(R.string.bluetootj_not_available)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceBind.unbindService()
        lg(" Activity destroy")
    }
    private fun checkBluetoothEnable(): Boolean {
        if ( !bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            ActivityCompat.startActivityForResult(this, enableBtIntent, 1, null)
        }
        return bluetoothAdapter.isEnabled
    }
    @SuppressLint("BatteryLife")
    private fun ignoreBatteryOptimisation(){
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        intent.data = Uri.parse("package:" + this.packageName)
        ActivityCompat.startActivityForResult(this, intent, 1, null)
    }
}

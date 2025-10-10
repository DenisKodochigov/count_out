package com.count_out.app

import android.Manifest.permission.BLUETOOTH
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.count_out.app.permission.RequestPermissionsAll
import com.count_out.app.presentation.StartApp
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.use_case.bluetooth.LastBleDeviceUC
import com.count_out.domain.use_case.other.CountOutServiceBindUC
import com.count_out.domain.use_case.other.CountOutServiceUnBindUC
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    @Inject lateinit var bluetoothAdapter: BluetoothAdapter
    @Inject lateinit var lastHearthRateDevice: LastBleDeviceUC
    @Inject lateinit var countOutServiceBind: CountOutServiceBindUC
    @Inject lateinit var countOutServiceUnBind: CountOutServiceUnBindUC
    var bindingWorkOut: ResultDomain<CountOutServiceBindUC.Response>? = null
    var unBindingWorkOut: ResultDomain<CountOutServiceUnBindUC.Response>? = null
    var connectedBleDevice: ResultDomain<LastBleDeviceUC.Response>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            countOutServiceBind.execute(CountOutServiceBindUC.Request).collect{
                bindingWorkOut = it}
            lastHearthRateDevice.execute(LastBleDeviceUC.Request).collect{
                connectedBleDevice = it}
        }
//        enableEdgeToEdge()
        setContent {
            RequestPermissionsAll()
            StartApp()
        }
    }
    override fun onStart() {
        super.onStart()
        ignoreBatteryOptimisation()
        checkBluetoothEnable()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.launch {
            countOutServiceUnBind.execute(CountOutServiceUnBindUC.Request).collect{
                unBindingWorkOut = it } }
    }
    @SuppressLint("BatteryLife")
    private fun ignoreBatteryOptimisation(){
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        intent.data = "package: ${this.packageName}".toUri()
        ActivityCompat.startActivityForResult(this, intent, 1, null)
    }
    private fun checkBluetoothEnable(): Boolean {
        if (ActivityCompat.checkSelfPermission(this,BLUETOOTH) == PackageManager.PERMISSION_GRANTED){
            if ( !bluetoothAdapter.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                ActivityCompat.startActivityForResult(this, enableBtIntent, 1, null)
            }
        }
        return bluetoothAdapter.isEnabled
    }
}


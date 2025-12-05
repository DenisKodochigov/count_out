package com.count_out.app

import android.Manifest.permission.BLUETOOTH
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.count_out.app.permission.RequestPermissionsAll
import com.count_out.app.presentation.StartApp
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.use_case.workout_service.CountOutServiceBindUC
import com.count_out.domain.use_case.workout_service.CountOutServiceUnBindUC
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    @Inject lateinit var bluetoothAdapter: BluetoothAdapter
    @Inject lateinit var serviceBindUC: CountOutServiceBindUC
    @Inject lateinit var serviceUnBindUC: CountOutServiceUnBindUC
    private val batteryOptLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {}
    var bindingWorkOut: ResultDomain<CountOutServiceBindUC.Response>? = null
    var unBindingWorkOut: ResultDomain<CountOutServiceUnBindUC.Response>? = null
    private val enableBtLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
            serviceBindUC.execute(CountOutServiceBindUC.Request).collect { bindingWorkOut = it }}
        }
//        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            RequestPermissionsAll(this)
            StartApp()
        }
    }
    override fun onStart() {
        super.onStart()
        ignoreBatteryOptimization()
        checkBluetoothEnable()
    }
    override fun onStop() {
        super.onStop()
        lifecycleScope.launch { serviceUnBindUC.execute(
            CountOutServiceUnBindUC.Request).collect { unBindingWorkOut = it } }
    }

    @SuppressLint("BatteryLife")
    private fun ignoreBatteryOptimization() {
        val pm = getSystemService(PowerManager::class.java)
        val pkg = packageName
        if (!pm.isIgnoringBatteryOptimizations(pkg)) {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            intent.data = "package:$pkg".toUri()
            batteryOptLauncher.launch(intent)
        }
    }
    private fun checkBluetoothEnable() {
        if (ActivityCompat.checkSelfPermission(this,BLUETOOTH) == PackageManager.PERMISSION_GRANTED){
            if ( !bluetoothAdapter.isEnabled) {
                enableBtLauncher.launch(
                    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
            }
        }
    }
}


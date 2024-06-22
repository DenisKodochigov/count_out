package com.example.count_out.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service.bluetooth.BleApp
import com.example.count_out.service.bluetooth.BleConnect
import com.example.count_out.service.bluetooth.BleScanner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BluetoothModule {

    @Singleton
    @Provides
    fun provideBluetoothManager(@ApplicationContext context: Context): BluetoothManager {
        return context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    }
    @Singleton
    @Provides
    fun provideBluetoothAdapter(bluetoothManager: BluetoothManager): BluetoothAdapter {
        return bluetoothManager.adapter
    }

    @Singleton
    @Provides
    fun provideBluetoothScanner(
        @ApplicationContext context: Context, bluetoothAdapter: BluetoothAdapter, permissionApp: PermissionApp
    ): BleScanner {
        return BleScanner(context, bluetoothAdapter, permissionApp)
    }
    @Singleton
    @Provides
    fun provideBluetoothConnect(@ApplicationContext context: Context, permissionApp: PermissionApp): BleConnect {
        return BleConnect(context, permissionApp)
    }
    @Singleton
    @Provides
    fun provideBluetoothApp(
        @ApplicationContext context: Context,
        bluetoothAdapter: BluetoothAdapter,
        bleScanner: BleScanner,
        bleConnect: BleConnect
    ): BleApp {
        return BleApp(context, bluetoothAdapter, bleScanner, bleConnect)
    }
}
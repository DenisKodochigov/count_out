package com.count_out.device.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.count_out.device.bluetooth.BleConnecting
import com.count_out.device.bluetooth.BleScanner
import com.count_out.device.bluetooth.Bluetooth
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
        @ApplicationContext context: Context,
        bluetoothAdapter: BluetoothAdapter,
//        permissionApp: PermissionApp
    ): BleScanner = BleScanner(context, bluetoothAdapter) //, permissionApp)

    @Singleton
    @Provides
    fun provideBluetoothConnect(
        @ApplicationContext context: Context,
//        permissionApp: PermissionApp,
    ): BleConnecting = BleConnecting(context)//, permissionApp,)

    @Singleton
    @Provides
    fun provideBluetooth(
        bleScanner: BleScanner,
        bleConnecting: BleConnecting,
        bluetoothAdapter: BluetoothAdapter,
    ): Bluetooth = Bluetooth(bleScanner, bleConnecting, bluetoothAdapter)
}
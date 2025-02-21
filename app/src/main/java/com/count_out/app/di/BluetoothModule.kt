package com.count_out.app.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.count_out.app.device.bluetooth.BleConnecting
import com.count_out.app.device.bluetooth.BleScanner
import com.count_out.app.device.bluetooth.Bluetooth
import com.count_out.app.entity.MessageApp
import com.count_out.app.ui.permission.PermissionApp
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
        permissionApp: PermissionApp
    ): BleScanner = BleScanner(context, bluetoothAdapter, permissionApp)

    @Singleton
    @Provides
    fun provideBluetoothConnect(
        @ApplicationContext context: Context,
        permissionApp: PermissionApp,
        messengerA: MessageApp
    ): BleConnecting = BleConnecting(context, permissionApp, messengerA)

    @Singleton
    @Provides
    fun provideBluetooth(
        bleScanner: BleScanner,
        messengerA: MessageApp,
        bleConnecting: BleConnecting,
        bluetoothAdapter: BluetoothAdapter,
    ): Bluetooth = Bluetooth(bleScanner, bleConnecting, messengerA, bluetoothAdapter)
}
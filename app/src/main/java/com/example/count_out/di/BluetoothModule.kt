package com.example.count_out.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.example.count_out.entity.MessageApp
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service_count_out.bluetooth.BleConnecting
import com.example.count_out.service_count_out.bluetooth.BleScanner
import com.example.count_out.service_count_out.bluetooth.Bluetooth
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
    ): Bluetooth = Bluetooth(bleScanner, messengerA, bleConnecting, bluetoothAdapter)
}
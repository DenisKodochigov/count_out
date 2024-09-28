package com.example.count_out.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.example.count_out.entity.MessageApp
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service.ServiceUtils
import com.example.count_out.service.bluetooth.BleConnecting
import com.example.count_out.service.bluetooth.BleBind
import com.example.count_out.service.bluetooth.BleScanner
import com.example.count_out.service.bluetooth.BleService
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
    fun provideBluetoothApp(@ApplicationContext context: Context, serviceUtils: ServiceUtils) =
        BleBind(context, serviceUtils)

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
    fun provideBleService() = BleService()
}
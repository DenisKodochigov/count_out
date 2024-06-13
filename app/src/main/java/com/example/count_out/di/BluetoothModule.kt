package com.example.count_out.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service.bluetooth.BluetoothApp
import com.example.count_out.service.bluetooth.BluetoothConnect
import com.example.count_out.service.bluetooth.BluetoothScanner
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
    ): BluetoothScanner {
        return BluetoothScanner(context, bluetoothAdapter, permissionApp)
    }
    @Singleton
    @Provides
    fun provideBluetoothConnect(@ApplicationContext context: Context, permissionApp: PermissionApp): BluetoothConnect {
        return BluetoothConnect(context, permissionApp)
    }
    @Singleton
    @Provides
    fun provideBluetoothApp(
        @ApplicationContext context: Context,
        bluetoothAdapter: BluetoothAdapter,
        bluetoothScanner: BluetoothScanner,
        bluetoothConnect: BluetoothConnect
    ): BluetoothApp {
        return BluetoothApp(context, bluetoothAdapter, bluetoothScanner, bluetoothConnect)
    }
}
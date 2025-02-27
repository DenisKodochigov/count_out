package com.count_out.app.old.di

//
//@Module
//@InstallIn(SingletonComponent::class)
//class BluetoothModule {
//
//    @Singleton
//    @Provides
//    fun provideBluetoothManager(@ApplicationContext context: Context): BluetoothManager {
//        return context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
//    }
//
//    @Singleton
//    @Provides
//    fun provideBluetoothAdapter(bluetoothManager: BluetoothManager): BluetoothAdapter {
//        return bluetoothManager.adapter
//    }
//
//    @Singleton
//    @Provides
//    fun provideBluetoothScanner(
//        @ApplicationContext context: Context,
//        bluetoothAdapter: BluetoothAdapter,
//        permissionApp: PermissionApp
//    ): BleScanner = BleScanner(context, bluetoothAdapter, permissionApp)
//
//    @Singleton
//    @Provides
//    fun provideBluetoothConnect(
//        @ApplicationContext context: Context,
//        permissionApp: PermissionApp,
//        messengerA: MessageApp
//    ): BleConnecting = BleConnecting(context, permissionApp, messengerA)
//
//    @Singleton
//    @Provides
//    fun provideBluetooth(
//        bleScanner: BleScanner,
//        messengerA: MessageApp,
//        bleConnecting: BleConnecting,
//        bluetoothAdapter: BluetoothAdapter,
//    ): Bluetooth = Bluetooth(bleScanner, bleConnecting, messengerA, bluetoothAdapter)
//}
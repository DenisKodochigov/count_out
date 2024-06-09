//package com.example.count_out.service.bluetooth.example1
//
//import android.bluetooth.BluetoothAdapter
//import android.bluetooth.BluetoothDevice
//import android.bluetooth.BluetoothDevice.BOND_BONDED
//import android.bluetooth.BluetoothDevice.BOND_BONDING
//import android.bluetooth.BluetoothDevice.BOND_NONE
//import android.bluetooth.BluetoothGatt
//import android.bluetooth.BluetoothGattCallback
//import android.bluetooth.BluetoothGattCharacteristic
//import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_INDICATE
//import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_NOTIFY
//import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_READ
//import android.bluetooth.BluetoothGattDescriptor
//import android.bluetooth.BluetoothGattService
//import android.bluetooth.BluetoothProfile
//import android.bluetooth.BluetoothStatusCodes
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.os.Build
//import android.os.Looper
//import android.os.SystemClock
//import java.lang.reflect.InvocationTargetException
//import java.util.Collections
//import java.util.Queue
//import java.util.UUID
//import java.util.concurrent.ConcurrentLinkedQueue
//import kotlin.concurrent.Volatile
//
//
//@Suppress("SpellCheckingInspection", "unused")
//class BluetoothPeripheral internal constructor(
//    context: Context,
//    device: BluetoothDevice,
//    listener: InternalCallback,
//    peripheralCallback: BluetoothPeripheralCallback,
//    callbackHandler: Handler,
//    transport: Transport
//) {
//    private val context: Context = Objects.requireNonNull(context, "no valid context provided")
//    private val callbackHandler: Handler
//    private var device: BluetoothDevice
//
//    private val peripheralType: PeripheralType
//
//    private val listener: InternalCallback
//    protected var peripheralCallback: BluetoothPeripheralCallback
//    private val commandQueue: Queue<Runnable> = ConcurrentLinkedQueue()
//
//    @Nullable
//    @Volatile
//    private var bluetoothGatt: BluetoothGatt? = null
//    private var cachedName = ""
//    private var currentWriteBytes = ByteArray(0)
//    private var currentCommand = IDLE
//    private val notifyingCharacteristics: MutableSet<BluetoothGattCharacteristic> = HashSet()
//    private val mainHandler: Handler = Handler(Looper.getMainLooper())
//
//    @Nullable
//    private var timeoutRunnable: Runnable? = null
//
//    @Nullable
//    private var discoverServicesRunnable: Runnable? = null
//
//    @Volatile
//    private var commandQueueBusy = false
//    private var isRetrying = false
//    private var bondLost = false
//    private var manuallyBonding = false
//
//    @Volatile
//    private var peripheralInitiatedBonding = false
//    private var discoveryStarted = false
//
//    @Volatile
//    private var state = BluetoothProfile.STATE_DISCONNECTED
//    private var nrTries = 0
//    private var connectTimestamp: Long = 0
//
//    /**
//     * Returns the currently set MTU
//     *
//     * @return the MTU
//     */
//    var currentMtu: Int = DEFAULT_MTU
//        private set
//    private val transport: Transport
//
//    /**
//     * This abstract class is used to implement BluetoothGatt callbacks.
//     */
//    private val bluetoothGattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {
//        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
//            if (newState != BluetoothProfile.STATE_CONNECTING) cancelConnectionTimer()
//            val previousState = state
//            state = newState
//
//            val hciStatus: HciStatus = HciStatus.fromValue(status)
//            if (hciStatus === HciStatus.SUCCESS) {
//                when (newState) {
//                    BluetoothProfile.STATE_CONNECTED -> successfullyConnected()
//                    BluetoothProfile.STATE_DISCONNECTED -> successfullyDisconnected(previousState)
//                    BluetoothProfile.STATE_DISCONNECTING -> {
//                        Logger.i(
//                            TAG, "peripheral '%s' is disconnecting",
//                            this.address
//                        )
//                        listener.disconnecting(this@BluetoothPeripheral)
//                    }
//
//                    BluetoothProfile.STATE_CONNECTING -> {
//                        Logger.i(
//                            TAG, "peripheral '%s' is connecting",
//                            this.address
//                        )
//                        listener.connecting(this@BluetoothPeripheral)
//                    }
//
//                    else -> Logger.e(TAG, "unknown state received")
//                }
//            } else {
//                connectionStateChangeUnsuccessful(hciStatus, previousState, newState)
//            }
//        }
//
//        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
//            val gattStatus: GattStatus = GattStatus.fromValue(status)
//            if (gattStatus !== GattStatus.SUCCESS) {
//                Logger.e(
//                    TAG,
//                    "service discovery failed due to internal error '%s', disconnecting",
//                    gattStatus
//                )
//                disconnect()
//                return
//            }
//
//            val services = gatt.services
//            Logger.i(
//                TAG, "discovered %d services for '%s'", services.size,
//                this.name
//            )
//
//            // Issue 'connected' since we are now fully connect incl service discovery
//            listener.connected(this@BluetoothPeripheral)
//
//            callbackHandler.post(Runnable { peripheralCallback.onServicesDiscovered(this@BluetoothPeripheral) })
//        }
//
//        override fun onDescriptorWrite(
//            gatt: BluetoothGatt,
//            descriptor: BluetoothGattDescriptor,
//            status: Int
//        ) {
//            val gattStatus: GattStatus = GattStatus.fromValue(status)
//            val parentCharacteristic = descriptor.characteristic
//            if (gattStatus !== GattStatus.SUCCESS) {
//                Logger.e(
//                    TAG,
//                    "failed to write <%s> to descriptor of characteristic <%s> for device: '%s', status '%s' ",
//                    asHexString(currentWriteBytes),
//                    parentCharacteristic.uuid,
//                    this.address,
//                    gattStatus
//                )
//                if (failureThatShouldTriggerBonding(gattStatus)) return
//            }
//
//            val value = currentWriteBytes
//            currentWriteBytes = ByteArray(0)
//
//            // Check if this was the Client Characteristic Configuration Descriptor
//            if (descriptor.uuid == CCC_DESCRIPTOR_UUID) {
//                if (gattStatus === GattStatus.SUCCESS) {
//                    if (value.contentEquals(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE) || value.contentEquals(
//                            BluetoothGattDescriptor.ENABLE_INDICATION_VALUE
//                        )
//                    ) {
//                        notifyingCharacteristics.add(parentCharacteristic)
//                    } else if (value.contentEquals(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)) {
//                        notifyingCharacteristics.remove(parentCharacteristic)
//                    }
//                }
//
//                callbackHandler.post(Runnable {
//                    peripheralCallback.onNotificationStateUpdate(
//                        this@BluetoothPeripheral,
//                        parentCharacteristic,
//                        gattStatus
//                    )
//                })
//            } else {
//                callbackHandler.post(Runnable {
//                    peripheralCallback.onDescriptorWrite(
//                        this@BluetoothPeripheral,
//                        value,
//                        descriptor,
//                        gattStatus
//                    )
//                })
//            }
//            completedCommand()
//        }
//
//        // NOTE the signature of this method is inconsistent with the other callbacks, i.e. position of status
//        override fun onDescriptorRead(
//            gatt: BluetoothGatt,
//            descriptor: BluetoothGattDescriptor,
//            status: Int,
//            @Nullable value: ByteArray
//        ) {
//            val gattStatus: GattStatus = GattStatus.fromValue(status)
//            if (gattStatus !== GattStatus.SUCCESS) {
//                Logger.e(
//                    TAG,
//                    "reading descriptor <%s> failed for device '%s, status '%s'",
//                    descriptor.uuid,
//                    this.address,
//                    gattStatus
//                )
//                if (failureThatShouldTriggerBonding(gattStatus)) return
//            }
//
//            val safeValue = nonnullOf(value)
//            callbackHandler.post(Runnable {
//                peripheralCallback.onDescriptorRead(
//                    this@BluetoothPeripheral,
//                    safeValue,
//                    descriptor,
//                    gattStatus
//                )
//            })
//            completedCommand()
//        }
//
//        override fun onDescriptorRead(
//            gatt: BluetoothGatt,
//            descriptor: BluetoothGattDescriptor,
//            status: Int
//        ) {
//            if (Build.VERSION.SDK_INT < 33) {
//                onDescriptorRead(gatt, descriptor, status, descriptor.value)
//            }
//        }
//
//        override fun onCharacteristicChanged(
//            gatt: BluetoothGatt,
//            characteristic: BluetoothGattCharacteristic,
//            @Nullable value: ByteArray
//        ) {
//            val safeValue = nonnullOf(value)
//            callbackHandler.post(Runnable {
//                peripheralCallback.onCharacteristicUpdate(
//                    this@BluetoothPeripheral,
//                    safeValue,
//                    characteristic,
//                    GattStatus.SUCCESS
//                )
//            })
//        }
//
//        override fun onCharacteristicChanged(
//            gatt: BluetoothGatt,
//            characteristic: BluetoothGattCharacteristic
//        ) {
//            if (Build.VERSION.SDK_INT < 33) {
//                onCharacteristicChanged(gatt, characteristic, characteristic.value)
//            }
//        }
//
//        override fun onCharacteristicRead(
//            gatt: BluetoothGatt,
//            characteristic: BluetoothGattCharacteristic,
//            @Nullable value: ByteArray,
//            status: Int
//        ) {
//            val gattStatus: GattStatus = GattStatus.fromValue(status)
//            if (gattStatus !== GattStatus.SUCCESS) {
//                Logger.e(
//                    TAG,
//                    "read failed for characteristic <%s>, status '%s'",
//                    characteristic.uuid,
//                    gattStatus
//                )
//                if (failureThatShouldTriggerBonding(gattStatus)) return
//            }
//
//            val safeValue = nonnullOf(value)
//            callbackHandler.post(Runnable {
//                peripheralCallback.onCharacteristicUpdate(
//                    this@BluetoothPeripheral,
//                    safeValue,
//                    characteristic,
//                    gattStatus
//                )
//            })
//            completedCommand()
//        }
//
//        override fun onCharacteristicRead(
//            gatt: BluetoothGatt,
//            characteristic: BluetoothGattCharacteristic,
//            status: Int
//        ) {
//            if (Build.VERSION.SDK_INT < 33) {
//                onCharacteristicRead(gatt, characteristic, characteristic.value, status)
//            }
//        }
//
//        override fun onCharacteristicWrite(
//            gatt: BluetoothGatt,
//            characteristic: BluetoothGattCharacteristic,
//            status: Int
//        ) {
//            val gattStatus: GattStatus = GattStatus.fromValue(status)
//            if (gattStatus !== GattStatus.SUCCESS) {
//                Logger.e(
//                    TAG,
//                    "writing <%s> to characteristic <%s> failed, status '%s'",
//                    asHexString(currentWriteBytes),
//                    characteristic.uuid,
//                    gattStatus
//                )
//                if (failureThatShouldTriggerBonding(gattStatus)) return
//            }
//
//            val value = currentWriteBytes
//            currentWriteBytes = ByteArray(0)
//            callbackHandler.post(Runnable {
//                peripheralCallback.onCharacteristicWrite(
//                    this@BluetoothPeripheral,
//                    value,
//                    characteristic,
//                    gattStatus
//                )
//            })
//            completedCommand()
//        }
//
//        private fun failureThatShouldTriggerBonding(gattStatus: GattStatus): Boolean {
//            if (gattStatus === GattStatus.AUTHORIZATION_FAILED || gattStatus === GattStatus.INSUFFICIENT_AUTHENTICATION || gattStatus === GattStatus.INSUFFICIENT_ENCRYPTION) {
//                // Characteristic/descriptor is encrypted and needs bonding, bonding should be in progress already
//                // Operation must be retried after bonding is completed.
//                // This only seems to happen on Android 5/6/7.
//                // On newer versions Android will do retry internally
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                    Logger.i(
//                        TAG,
//                        "operation will be retried after bonding, bonding should be in progress"
//                    )
//                    return true
//                }
//            }
//            return false
//        }
//
//        override fun onReadRemoteRssi(gatt: BluetoothGatt, rssi: Int, status: Int) {
//            val gattStatus: GattStatus = GattStatus.fromValue(status)
//            if (gattStatus !== GattStatus.SUCCESS) {
//                Logger.e(TAG, "reading RSSI failed, status '%s'", gattStatus)
//            }
//
//            callbackHandler.post(Runnable {
//                peripheralCallback.onReadRemoteRssi(
//                    this@BluetoothPeripheral,
//                    rssi,
//                    gattStatus
//                )
//            })
//            completedCommand()
//        }
//
//        override fun onMtuChanged(gatt: BluetoothGatt, mtu: Int, status: Int) {
//            val gattStatus: GattStatus = GattStatus.fromValue(status)
//            if (gattStatus !== GattStatus.SUCCESS) {
//                Logger.e(TAG, "change MTU failed, status '%s'", gattStatus)
//            }
//
//            currentMtu = mtu
//            callbackHandler.post(Runnable {
//                peripheralCallback.onMtuChanged(
//                    this@BluetoothPeripheral,
//                    mtu,
//                    gattStatus
//                )
//            })
//
//            // Only complete the command if we initiated the operation. It can also be initiated by the remote peripheral...
//            if (currentCommand == REQUEST_MTU_COMMAND) {
//                currentCommand = IDLE
//                completedCommand()
//            }
//        }
//
//        override fun onPhyRead(gatt: BluetoothGatt, txPhy: Int, rxPhy: Int, status: Int) {
//            val gattStatus: GattStatus = GattStatus.fromValue(status)
//            if (gattStatus !== GattStatus.SUCCESS) {
//                Logger.e(TAG, "read Phy failed, status '%s'", gattStatus)
//            } else {
//                Logger.i(
//                    TAG,
//                    "updated Phy: tx = %s, rx = %s",
//                    PhyType.fromValue(txPhy),
//                    PhyType.fromValue(rxPhy)
//                )
//            }
//
//            callbackHandler.post(Runnable {
//                peripheralCallback.onPhyUpdate(
//                    this@BluetoothPeripheral,
//                    PhyType.fromValue(txPhy),
//                    PhyType.fromValue(rxPhy),
//                    gattStatus
//                )
//            })
//            completedCommand()
//        }
//
//        override fun onPhyUpdate(gatt: BluetoothGatt, txPhy: Int, rxPhy: Int, status: Int) {
//            val gattStatus: GattStatus = GattStatus.fromValue(status)
//            if (gattStatus !== GattStatus.SUCCESS) {
//                Logger.e(TAG, "update Phy failed, status '%s'", gattStatus)
//            } else {
//                Logger.i(
//                    TAG,
//                    "updated Phy: tx = %s, rx = %s",
//                    PhyType.fromValue(txPhy),
//                    PhyType.fromValue(rxPhy)
//                )
//            }
//
//            callbackHandler.post(Runnable {
//                peripheralCallback.onPhyUpdate(
//                    this@BluetoothPeripheral,
//                    PhyType.fromValue(txPhy),
//                    PhyType.fromValue(rxPhy),
//                    gattStatus
//                )
//            })
//
//            // Only complete the command if we initiated the operation. It can also be initiated by the remote peripheral...
//            if (currentCommand == SET_PHY_TYPE_COMMAND) {
//                currentCommand = IDLE
//                completedCommand()
//            }
//        }
//
//        /**
//         * This callback is only called from Android 8 (Oreo) or higher. Not all phones seem to call this though...
//         */
//        fun onConnectionUpdated(
//            gatt: BluetoothGatt,
//            interval: Int,
//            latency: Int,
//            timeout: Int,
//            status: Int
//        ) {
//            val gattStatus: GattStatus = GattStatus.fromValue(status)
//            if (gattStatus === GattStatus.SUCCESS) {
//                val msg: String = java.lang.String.format(
//                    Locale.ENGLISH,
//                    "connection parameters: interval=%.1fms latency=%d timeout=%ds",
//                    interval * 1.25f,
//                    latency,
//                    timeout / 100
//                )
//                Logger.d(TAG, msg)
//            } else {
//                Logger.e(TAG, "connection parameters update failed with status '%s'", gattStatus)
//            }
//
//            callbackHandler.post(Runnable {
//                peripheralCallback.onConnectionUpdated(
//                    this@BluetoothPeripheral,
//                    interval,
//                    latency,
//                    timeout,
//                    gattStatus
//                )
//            })
//        }
//
//        override fun onServiceChanged(gatt: BluetoothGatt) {
//            Logger.d(TAG, "onServiceChangedCalled")
//
//            // Does it realy make sense to discover services? Or should we just disconnect and reconnect?
//            commandQueue.clear()
//            commandQueueBusy = false
//            delayedDiscoverServices(100)
//        }
//    }
//
//    private fun successfullyConnected() {
//        val bondstate: BondState = bondState
//        val timePassed = SystemClock.elapsedRealtime() - connectTimestamp
//        Logger.i(
//            TAG, "connected to '%s' (%s) in %.1fs",
//            name, bondstate, timePassed / 1000.0f
//        )
//
//        if (bondstate === BondState.NONE || bondstate === BondState.BONDED) {
//            delayedDiscoverServices(getServiceDiscoveryDelay(bondstate))
//        } else if (bondstate === BondState.BONDING) {
//            // Apparently the bonding process has already started, so let it complete. We'll do discoverServices once bonding finished
//            Logger.i(TAG, "waiting for bonding to complete")
//        }
//    }
//
//    private fun delayedDiscoverServices(delay: Long) {
//        discoverServicesRunnable = object : Runnable {
//            override fun run() {
//                Logger.d(
//                    TAG, "discovering services of '%s' with delay of %d ms",
//                    this.name, delay
//                )
//                if (bluetoothGatt != null && bluetoothGatt!!.discoverServices()) {
//                    discoveryStarted = true
//                } else {
//                    Logger.e(TAG, "discoverServices failed to start")
//                }
//                discoverServicesRunnable = null
//            }
//        }
//        mainHandler.postDelayed(discoverServicesRunnable, delay)
//    }
//
//    private fun getServiceDiscoveryDelay(bondstate: BondState): Long {
//        var delayWhenBonded: Long = 0
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
//            // It seems delays when bonded are only needed in versions Nougat or lower
//            // This issue was observed on a Nexus 5 (M) and Sony Xperia L1 (N) when connecting to a A&D UA-651BLE
//            // The delay is needed when devices have the Service Changed Characteristic.
//            // If they don't have it the delay isn't needed but we do it anyway to keep code simple
//            delayWhenBonded = 1000L
//        }
//        return if (bondstate === BondState.BONDED) delayWhenBonded else 0
//    }
//
//    private fun successfullyDisconnected(previousState: Int) {
//        if (previousState == BluetoothProfile.STATE_CONNECTED || previousState == BluetoothProfile.STATE_DISCONNECTING) {
//            Logger.i(TAG, "disconnected '%s' on request", name)
//        } else if (previousState == BluetoothProfile.STATE_CONNECTING) {
//            Logger.i(TAG, "cancelling connect attempt")
//        }
//
//        if (bondLost) {
//            Logger.d(TAG, "disconnected because of bond lost")
//
//            // Give the stack some time to register the bond loss internally. This is needed on most phones...
//            callbackHandler.postDelayed(object : Runnable {
//                override fun run() {
//                    if (this.services.isEmpty()) {
//                        // Service discovery was not completed yet so consider it a connectionFailure
//                        completeDisconnect(false, HciStatus.AUTHENTICATION_FAILURE)
//                        listener.connectFailed(
//                            this@BluetoothPeripheral,
//                            HciStatus.AUTHENTICATION_FAILURE
//                        )
//                    } else {
//                        // Bond was lost after a succesful connection was established
//                        completeDisconnect(true, HciStatus.AUTHENTICATION_FAILURE)
//                    }
//                }
//            }, DELAY_AFTER_BOND_LOST)
//        } else {
//            completeDisconnect(true, HciStatus.SUCCESS)
//        }
//    }
//
//    private fun connectionStateChangeUnsuccessful(
//        status: HciStatus,
//        previousState: Int,
//        newState: Int
//    ) {
//        cancelPendingServiceDiscovery()
//        val servicesDiscovered = !services.isEmpty()
//
//        // See if the initial connection failed
//        if (previousState == BluetoothProfile.STATE_CONNECTING) {
//            val timePassed = SystemClock.elapsedRealtime() - connectTimestamp
//            val isTimeout = timePassed > timoutThreshold
//            val adjustedStatus: HciStatus =
//                if ((status === HciStatus.ERROR && isTimeout)) HciStatus.CONNECTION_FAILED_ESTABLISHMENT else status
//            Logger.i(TAG, "connection failed with status '%s'", adjustedStatus)
//            completeDisconnect(false, adjustedStatus)
//            listener.connectFailed(this@BluetoothPeripheral, adjustedStatus)
//        } else if (previousState == BluetoothProfile.STATE_CONNECTED && newState == BluetoothProfile.STATE_DISCONNECTED && !servicesDiscovered) {
//            // We got a disconnection before the services were even discovered
//            Logger.i(
//                TAG,
//                "peripheral '%s' disconnected with status '%s' (%d) before completing service discovery",
//                name,
//                status,
//                status.value
//            )
//            completeDisconnect(false, status)
//            listener.connectFailed(this@BluetoothPeripheral, status)
//        } else {
//            // See if we got connection drop
//            if (newState == BluetoothProfile.STATE_DISCONNECTED) {
//                Logger.i(
//                    TAG, "peripheral '%s' disconnected with status '%s' (%d)",
//                    name, status, status.value
//                )
//            } else {
//                Logger.i(
//                    TAG, "unexpected connection state change for '%s' status '%s' (%d)",
//                    name, status, status.value
//                )
//            }
//            completeDisconnect(true, status)
//        }
//    }
//
//    private fun cancelPendingServiceDiscovery() {
//        if (discoverServicesRunnable != null) {
//            mainHandler.removeCallbacks(discoverServicesRunnable)
//            discoverServicesRunnable = null
//        }
//    }
//
//    private val bondStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            val action = intent.action ?: return
//            val receivedDevice =
//                intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
//                    ?: return
//
//            // Ignore updates for other devices
//            if (!receivedDevice.address.equals(this.address, ignoreCase = true)) return
//
//            if (action == BluetoothDevice.ACTION_BOND_STATE_CHANGED) {
//                val bondState =
//                    intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR)
//                val previousBondState = intent.getIntExtra(
//                    BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE,
//                    BluetoothDevice.ERROR
//                )
//                handleBondStateChange(bondState, previousBondState)
//            }
//        }
//    }
//
//    private fun handleBondStateChange(bondState: Int, previousBondState: Int) {
//        when (bondState) {
//            BOND_BONDING -> {
//                Logger.d(
//                    TAG, "starting bonding with '%s' (%s)",
//                    name,
//                    address
//                )
//                callbackHandler.post(Runnable { peripheralCallback.onBondingStarted(this@BluetoothPeripheral) })
//            }
//
//            BOND_BONDED -> {
//                Logger.d(
//                    TAG, "bonded with '%s' (%s)",
//                    name,
//                    address
//                )
//                callbackHandler.post(Runnable { peripheralCallback.onBondingSucceeded(this@BluetoothPeripheral) })
//
//                // Check if we are missing a gatt object. This is the case if createBond was called on a disconnected peripheral
//                if (bluetoothGatt == null) {
//                    // Bonding succeeded so now we can connect
//                    connect()
//                    return
//                }
//
//                // If bonding was started at connection time, we may still have to discover the services
//                // Also make sure we are not starting a discovery while another one is already in progress
//                if (services.isEmpty() && !discoveryStarted) {
//                    delayedDiscoverServices(0)
//                }
//
//                // If bonding was triggered by a read/write, we must retry it
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                    if (commandQueueBusy && !manuallyBonding) {
//                        mainHandler.postDelayed(Runnable {
//                            Logger.d(TAG, "retrying command after bonding")
//                            retryCommand()
//                        }, 50)
//                    }
//                }
//
//                // If we are doing a manual bond, complete the command
//                if (manuallyBonding) {
//                    manuallyBonding = false
//                    completedCommand()
//                }
//
//                // If the peripheral initated the bonding, continue the queue
//                if (peripheralInitiatedBonding) {
//                    peripheralInitiatedBonding = false
//                    nextCommand()
//                }
//            }
//
//            BOND_NONE -> {
//                if (previousBondState == BOND_BONDING) {
//                    Logger.e(
//                        TAG, "bonding failed for '%s', disconnecting device",
//                        name
//                    )
//                    callbackHandler.post(Runnable { peripheralCallback.onBondingFailed(this@BluetoothPeripheral) })
//                } else {
//                    Logger.e(TAG, "bond lost for '%s'", name)
//                    bondLost = true
//
//                    // Cancel the discoverServiceRunnable if it is still pending
//                    cancelPendingServiceDiscovery()
//
//                    callbackHandler.post(Runnable { peripheralCallback.onBondLost(this@BluetoothPeripheral) })
//                }
//
//                // There are 2 scenarios here:
//                // 1. The user removed the peripheral from the list of paired devices in the settings menu
//                // 2. The peripheral bonded with another phone after the last connection
//                //
//                // In both scenarios we want to end up in a disconnected state.
//                // When removing a bond via the settings menu, Android will disconnect the peripheral itself.
//                // However, the disconnected event (CONNECTION_TERMINATED_BY_LOCAL_HOST) will come either before or after the bond state update and on a different thread
//                // Note that on the Samsung J5 (J530F) the disconnect happens but no bond change is received!
//                // And in case of scenario 2 we may need to issue a disconnect ourselves.
//                // Therefor to solve this race condition, add a bit of delay to see if a disconnect is needed for scenario 2
//                mainHandler.postDelayed(Runnable {
//                    if (getState() === ConnectionState.CONNECTED) {
//                        // If we are still connected, then disconnect because we usually can't interact with the peripheral anymore
//                        // Some peripherals already do a diconnect by themselves (REMOTE_USER_TERMINATED_CONNECTION) so we may already be disconnected
//                        disconnect()
//                    }
//                }, 100)
//            }
//        }
//    }
//
//    private val pairingRequestBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            val receivedDevice =
//                intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
//                    ?: return
//
//            // Skip other devices
//            if (!receivedDevice.address.equals(this.address, ignoreCase = true)) return
//
//            val variant =
//                intent.getIntExtra(BluetoothDevice.EXTRA_PAIRING_VARIANT, BluetoothDevice.ERROR)
//            Logger.d(
//                TAG,
//                "pairing request received: " + pairingVariantToString(variant) + " (" + variant + ")"
//            )
//
//            if (variant == PAIRING_VARIANT_PIN) {
//                val pin = listener.getPincode(this@BluetoothPeripheral)
//                if (pin != null) {
//                    Logger.d(TAG, "setting PIN code for this peripheral using '%s'", pin)
//                    receivedDevice.setPin(pin.toByteArray())
//                    abortBroadcast()
//                }
//            }
//        }
//    }
//
//    fun setPeripheralCallback(peripheralCallback: BluetoothPeripheralCallback) {
//        this.peripheralCallback =
//            Objects.requireNonNull(peripheralCallback, NO_VALID_PERIPHERAL_CALLBACK_PROVIDED)
//    }
//
//    fun setDevice(bluetoothDevice: BluetoothDevice) {
//        this.device = Objects.requireNonNull(bluetoothDevice, NO_VALID_DEVICE_PROVIDED)
//    }
//
//    /**
//     * Connect directly with the bluetooth device. This call will timeout in max 30 seconds (5 seconds on Samsung phones)
//     */
//    fun connect() {
//        // Make sure we are disconnected before we start making a connection
//        if (state == BluetoothProfile.STATE_DISCONNECTED) {
//            mainHandler.postDelayed(object : Runnable {
//                override fun run() {
//                    // Connect to device with autoConnect = false
//                    Logger.i(
//                        TAG, "connect to '%s' (%s) using transport %s",
//                        this.name,
//                        this.address, transport.name()
//                    )
//                    registerBondingBroadcastReceivers()
//                    discoveryStarted = false
//                    connectTimestamp = SystemClock.elapsedRealtime()
//                    bluetoothGatt = connectGattHelper(device, false, bluetoothGattCallback)
//                    if (bluetoothGatt != null) {
//                        bluetoothGattCallback.onConnectionStateChange(
//                            bluetoothGatt,
//                            HciStatus.SUCCESS.value,
//                            BluetoothProfile.STATE_CONNECTING
//                        )
//                        startConnectionTimer(this@BluetoothPeripheral)
//                    } else {
//                        Logger.e(
//                            TAG, "failed to connect to peripheral '%s'",
//                            this.address
//                        )
//                    }
//                }
//            }, DIRECT_CONNECTION_DELAY_IN_MS)
//        } else {
//            Logger.e(
//                TAG, "peripheral '%s' not yet disconnected, will not connect",
//                name
//            )
//        }
//    }
//
//    /**
//     * Try to connect to a device whenever it is found by the OS. This call never times out.
//     * Connecting to a device will take longer than when using connect()
//     */
//    fun autoConnect() {
//        // Note that this will only work for devices that are known! After turning BT on/off Android doesn't know the device anymore!
//        // https://stackoverflow.com/questions/43476369/android-save-ble-device-to-reconnect-after-app-close
//        if (state == BluetoothProfile.STATE_DISCONNECTED) {
//            mainHandler.post(object : Runnable {
//                override fun run() {
//                    // Connect to device with autoConnect = true
//                    Logger.i(
//                        TAG, "autoConnect to '%s' (%s) using transport %s",
//                        this.name,
//                        this.address, transport.name()
//                    )
//                    registerBondingBroadcastReceivers()
//                    discoveryStarted = false
//                    connectTimestamp = SystemClock.elapsedRealtime()
//                    bluetoothGatt = connectGattHelper(device, true, bluetoothGattCallback)
//                    if (bluetoothGatt != null) {
//                        bluetoothGattCallback.onConnectionStateChange(
//                            bluetoothGatt,
//                            HciStatus.SUCCESS.value,
//                            BluetoothProfile.STATE_CONNECTING
//                        )
//                    } else {
//                        Logger.e(
//                            TAG, "failed to autoconnect to peripheral '%s'",
//                            this.address
//                        )
//                    }
//                }
//            })
//        } else {
//            Logger.e(
//                TAG, "peripheral '%s' not yet disconnected, will not connect",
//                name
//            )
//        }
//    }
//
//    private fun registerBondingBroadcastReceivers() {
//        context.registerReceiver(
//            bondStateReceiver,
//            IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
//        )
//        context.registerReceiver(
//            pairingRequestBroadcastReceiver,
//            IntentFilter(BluetoothDevice.ACTION_PAIRING_REQUEST)
//        )
//    }
//
//    /**
//     * Create a bond with the peripheral.
//     *
//     *
//     * If a (auto)connect has been issued, the bonding command will be enqueued and you will
//     * receive updates via the [BluetoothPeripheralCallback]. Otherwise the bonding will
//     * be done immediately and no updates via the callback will happen.
//     *
//     * @return true if bonding was started/enqueued, false if not
//     */
//    fun createBond(): Boolean {
//        // Check if we have a Gatt object
//        if (bluetoothGatt == null) {
//            // No gatt object so no connection issued, do create bond immediately
//            Logger.d(
//                TAG, "connecting and creating bond with '%s'",
//                name
//            )
//            registerBondingBroadcastReceivers()
//            return device.createBond()
//        }
//
//        // Enqueue the bond command because a connection has been issued or we are already connected
//        return enqueue(object : Runnable {
//            override fun run() {
//                manuallyBonding = true
//                if (!device.createBond()) {
//                    Logger.e(
//                        TAG, "bonding failed for %s",
//                        this.address
//                    )
//                    completedCommand()
//                } else {
//                    Logger.d(TAG, "manually bonding %s", this.address)
//                    nrTries++
//                }
//            }
//        })
//    }
//
//    /**
//     * Cancel an active or pending connection.
//     *
//     *
//     * This operation is asynchronous and you will receive a callback on onDisconnectedPeripheral.
//     */
//    fun cancelConnection() {
//        // Check if we have a Gatt object
//        if (bluetoothGatt == null) {
//            Logger.w(TAG, "cannot cancel connection because no connection attempt is made yet")
//            return
//        }
//
//        // Check if we are not already disconnected or disconnecting
//        if (state == BluetoothProfile.STATE_DISCONNECTED || state == BluetoothProfile.STATE_DISCONNECTING) {
//            return
//        }
//
//        // Cancel the connection timer
//        cancelConnectionTimer()
//
//        // Check if we are in the process of connecting
//        if (state == BluetoothProfile.STATE_CONNECTING) {
//            // Cancel the connection by calling disconnect
//            disconnect()
//
//            // Since we will not get a callback on onConnectionStateChange for this, we issue the disconnect ourselves
//            mainHandler.postDelayed(Runnable {
//                if (bluetoothGatt != null) {
//                    bluetoothGattCallback.onConnectionStateChange(
//                        bluetoothGatt,
//                        HciStatus.SUCCESS.value,
//                        BluetoothProfile.STATE_DISCONNECTED
//                    )
//                }
//            }, 50)
//        } else {
//            // Cancel active connection and onConnectionStateChange will be called by Android
//            disconnect()
//        }
//    }
//
//    /**
//     * Disconnect the bluetooth peripheral.
//     *
//     *
//     * When the disconnection has been completed [BluetoothCentralManagerCallback.onDisconnectedPeripheral] will be called.
//     */
//    private fun disconnect() {
//        if (state == BluetoothProfile.STATE_CONNECTED || state == BluetoothProfile.STATE_CONNECTING) {
//            if (bluetoothGatt != null) {
//                bluetoothGattCallback.onConnectionStateChange(
//                    bluetoothGatt,
//                    HciStatus.SUCCESS.value,
//                    BluetoothProfile.STATE_DISCONNECTING
//                )
//            }
//            mainHandler.post(object : Runnable {
//                override fun run() {
//                    if (state == BluetoothProfile.STATE_DISCONNECTING && bluetoothGatt != null) {
//                        bluetoothGatt!!.disconnect()
//                        Logger.i(
//                            TAG, "force disconnect '%s' (%s)",
//                            this.name,
//                            this.address
//                        )
//                    }
//                }
//            })
//        } else {
//            listener.disconnected(this@BluetoothPeripheral, HciStatus.SUCCESS)
//        }
//    }
//
//    fun disconnectWhenBluetoothOff() {
//        completeDisconnect(true, HciStatus.SUCCESS)
//    }
//
//    /**
//     * Complete the disconnect after getting connectionstate == disconnected
//     */
//    private fun completeDisconnect(notify: Boolean, status: HciStatus) {
//        if (bluetoothGatt != null) {
//            bluetoothGatt!!.close()
//            bluetoothGatt = null
//        }
//        commandQueue.clear()
//        commandQueueBusy = false
//        notifyingCharacteristics.clear()
//        currentMtu = DEFAULT_MTU
//        currentCommand = IDLE
//        manuallyBonding = false
//        peripheralInitiatedBonding = false
//        discoveryStarted = false
//        try {
//            context.unregisterReceiver(bondStateReceiver)
//            context.unregisterReceiver(pairingRequestBroadcastReceiver)
//        } catch (e: IllegalArgumentException) {
//            // In case bluetooth is off, unregisering broadcast receivers may fail
//        }
//        bondLost = false
//        if (notify) {
//            listener.disconnected(this@BluetoothPeripheral, status)
//        }
//    }
//
//    val address: String
//        /**
//         * Get the mac address of the bluetooth peripheral.
//         *
//         * @return Address of the bluetooth peripheral
//         */
//        get() = device.address
//
//    val type: PeripheralType
//        /**
//         * Get the type of the peripheral.
//         *
//         * @return the PeripheralType
//         */
//        get() = peripheralType
//
//    val name: String
//        /**
//         * Get the name of the bluetooth peripheral.
//         *
//         * @return name of the bluetooth peripheral
//         */
//        get() {
//            val name = device.name
//            if (name != null) {
//                // Cache the name so that we even know it when bluetooth is switched off
//                cachedName = name
//                return name
//            }
//            return cachedName
//        }
//
//    val bondState: BondState
//        /**
//         * Get the bond state of the bluetooth peripheral.
//         *
//         * @return the bond state
//         */
//        get() = BondState.fromValue(device.bondState)
//
//    val services: List<BluetoothGattService>
//        /**
//         * Get the services supported by the connected bluetooth peripheral.
//         * Only services that are also supported by [BluetoothCentralManager] are included.
//         *
//         * @return Supported services.
//         */
//        get() {
//            if (bluetoothGatt != null) {
//                return bluetoothGatt!!.services
//            }
//            return emptyList()
//        }
//
//    /**
//     * Get the BluetoothGattService object for a service UUID.
//     *
//     * @param serviceUUID the UUID of the service
//     * @return the BluetoothGattService object for the service UUID or null if the peripheral does not have a service with the specified UUID
//     */
//    @Nullable
//    fun getService(serviceUUID: UUID): BluetoothGattService? {
//        Objects.requireNonNull(serviceUUID, NO_VALID_SERVICE_UUID_PROVIDED)
//
//        return if (bluetoothGatt != null) {
//            bluetoothGatt!!.getService(serviceUUID)
//        } else {
//            null
//        }
//    }
//
//    /**
//     * Get the BluetoothGattCharacteristic object for a characteristic UUID.
//     *
//     * @param serviceUUID        the service UUID the characteristic is part of
//     * @param characteristicUUID the UUID of the chararacteristic
//     * @return the BluetoothGattCharacteristic object for the characteristic UUID or null if the peripheral does not have a characteristic with the specified UUID
//     */
//    @Nullable
//    fun getCharacteristic(
//        serviceUUID: UUID,
//        characteristicUUID: UUID
//    ): BluetoothGattCharacteristic? {
//        Objects.requireNonNull(serviceUUID, NO_VALID_SERVICE_UUID_PROVIDED)
//        Objects.requireNonNull(characteristicUUID, NO_VALID_CHARACTERISTIC_UUID_PROVIDED)
//
//        val service = getService(serviceUUID)
//        return service?.getCharacteristic(characteristicUUID)
//    }
//
//    /**
//     * Returns the connection state of the peripheral.
//     *
//     * @return the connection state.
//     */
//    fun getState(): ConnectionState {
//        return ConnectionState.fromValue(state)
//    }
//
//    /**
//     * Get maximum length of byte array that can be written depending on WriteType
//     *
//     *
//     *
//     * This value is derived from the current negotiated MTU or the maximum characteristic length (512)
//     */
//    fun getMaximumWriteValueLength(writeType: WriteType): Int {
//        Objects.requireNonNull(writeType, "writetype is null")
//
//        return when (writeType) {
//            WITH_RESPONSE -> 512
//            SIGNED -> currentMtu - 15
//            else -> currentMtu - 3
//        }
//    }
//
//    /**
//     * Returns the transport used during connection phase.
//     *
//     * @return the Transport.
//     */
//    fun getTransport(): Transport {
//        return transport
//    }
//
//    /**
//     * Boolean to indicate if the specified characteristic is currently notifying or indicating.
//     *
//     * @param characteristic the characteristic to check
//     * @return true if the characteristic is notifying or indicating, false if it is not
//     */
//    fun isNotifying(characteristic: BluetoothGattCharacteristic): Boolean {
//        Objects.requireNonNull(characteristic, NO_VALID_CHARACTERISTIC_PROVIDED)
//        return notifyingCharacteristics.contains(characteristic)
//    }
//
//    /**
//     * Get all notifying/indicating characteristics
//     *
//     * @return Set of characteristics or empty set
//     */
//    fun getNotifyingCharacteristics(): Set<BluetoothGattCharacteristic> {
//        return Collections.unmodifiableSet(notifyingCharacteristics)
//    }
//
//    private val isConnected: Boolean
//        get() = bluetoothGatt != null && state == BluetoothProfile.STATE_CONNECTED
//
//    private fun notConnected(): Boolean {
//        return !isConnected
//    }
//
//    val isUncached: Boolean
//        /**
//         * Check if the peripheral is uncached by the Android BLE stack
//         *
//         * @return true if unchached, otherwise false
//         */
//        get() = type === PeripheralType.UNKNOWN
//
//    /**
//     * Read the value of a characteristic.
//     *
//     *
//     * Convenience function to read a characteristic without first having to find it.
//     *
//     * @param serviceUUID        the service UUID the characteristic belongs to
//     * @param characteristicUUID the characteristic's UUID
//     * @return true if the operation was enqueued, false if the characteristic was not found
//     * @throws IllegalArgumentException if the characteristic does not support reading
//     */
//    fun readCharacteristic(serviceUUID: UUID, characteristicUUID: UUID): Boolean {
//        Objects.requireNonNull(serviceUUID, NO_VALID_SERVICE_UUID_PROVIDED)
//        Objects.requireNonNull(characteristicUUID, NO_VALID_CHARACTERISTIC_UUID_PROVIDED)
//
//        val characteristic = getCharacteristic(serviceUUID, characteristicUUID)
//        if (characteristic != null) {
//            return readCharacteristic(characteristic)
//        }
//        return false
//    }
//
//    /**
//     * Read the value of a characteristic.
//     *
//     *
//     * [BluetoothPeripheralCallback.onCharacteristicUpdate]   will be triggered as a result of this call.
//     *
//     * @param characteristic Specifies the characteristic to read.
//     * @return true if the operation was enqueued, otherwise false
//     * @throws IllegalArgumentException if the characteristic does not support reading
//     */
//    fun readCharacteristic(characteristic: BluetoothGattCharacteristic): Boolean {
//        Objects.requireNonNull(characteristic, NO_VALID_CHARACTERISTIC_PROVIDED)
//
//        if (doesNotSupportReading(characteristic)) {
//            val message = String.format(
//                "characteristic <%s> does not have read property",
//                characteristic.uuid
//            )
//            throw IllegalArgumentException(message)
//        }
//
//        return enqueue {
//            if (bluetoothGatt!!.readCharacteristic(characteristic)) {
//                Logger.d(
//                    TAG,
//                    "reading characteristic <%s>",
//                    characteristic.uuid
//                )
//                nrTries++
//            } else {
//                Logger.e(
//                    TAG,
//                    "readCharacteristic failed for characteristic: %s",
//                    characteristic.uuid
//                )
//                completedCommand()
//            }
//        }
//    }
//
//    private fun doesNotSupportReading(characteristic: BluetoothGattCharacteristic): Boolean {
//        return (characteristic.properties and PROPERTY_READ) == 0
//    }
//
//    /**
//     * Write a value to a characteristic using the specified write type.
//     *
//     *
//     * Convenience function to write a characteristic without first having to find it.
//     * All parameters must have a valid value in order for the operation to be enqueued.
//     *
//     * @param serviceUUID        the service UUID the characteristic belongs to
//     * @param characteristicUUID the characteristic's UUID
//     * @param value              the byte array to write
//     * @param writeType          the write type to use when writing.
//     * @return true if the operation was enqueued, otherwise false
//     * @throws IllegalArgumentException if the characteristic does not support writing with the specified writeType or the byte array is empty or too long
//     */
//    fun writeCharacteristic(
//        serviceUUID: UUID,
//        characteristicUUID: UUID,
//        value: ByteArray,
//        writeType: WriteType
//    ): Boolean {
//        Objects.requireNonNull(serviceUUID, NO_VALID_SERVICE_UUID_PROVIDED)
//        Objects.requireNonNull(characteristicUUID, NO_VALID_CHARACTERISTIC_UUID_PROVIDED)
//        Objects.requireNonNull(value, NO_VALID_VALUE_PROVIDED)
//        Objects.requireNonNull(writeType, NO_VALID_WRITE_TYPE_PROVIDED)
//
//        val characteristic = getCharacteristic(serviceUUID, characteristicUUID)
//        if (characteristic != null) {
//            return writeCharacteristic(characteristic, value, writeType)
//        }
//        return false
//    }
//
//    /**
//     * Write a value to a characteristic using the specified write type.
//     *
//     *
//     * All parameters must have a valid value in order for the operation to be enqueued.
//     * The length of the byte array to write must be between 1 and getMaximumWriteValueLength(writeType).
//     *
//     *
//     * [BluetoothPeripheralCallback.onCharacteristicWrite] will be triggered as a result of this call.
//     *
//     * @param characteristic the characteristic to write to
//     * @param value          the byte array to write
//     * @param writeType      the write type to use when writing.
//     * @return true if a write operation was succesfully enqueued, otherwise false
//     * @throws IllegalArgumentException if the characteristic does not support writing with the specified writeType or the byte array is empty or too long
//     */
//    fun writeCharacteristic(
//        characteristic: BluetoothGattCharacteristic,
//        value: ByteArray,
//        writeType: WriteType
//    ): Boolean {
//        Objects.requireNonNull(characteristic, NO_VALID_CHARACTERISTIC_PROVIDED)
//        Objects.requireNonNull(value, NO_VALID_VALUE_PROVIDED)
//        Objects.requireNonNull(writeType, NO_VALID_WRITE_TYPE_PROVIDED)
//
//        require(value.size != 0) { VALUE_BYTE_ARRAY_IS_EMPTY }
//
//        require(value.size <= getMaximumWriteValueLength(writeType)) { VALUE_BYTE_ARRAY_IS_TOO_LONG }
//
//        if (doesNotSupportWriteType(characteristic, writeType)) {
//            val message = java.lang.String.format(
//                "characteristic <%s> does not support writeType '%s'",
//                characteristic.uuid,
//                writeType
//            )
//            throw IllegalArgumentException(message)
//        }
//
//        // Copy the value to avoid race conditions
//        val bytesToWrite = copyOf(value)
//
//        return enqueue {
//            if (willCauseLongWrite(bytesToWrite, writeType)) {
//                // Android will turn this into a Long Write because it is larger than the MTU - 3.
//                // When doing a Long Write the byte array will be automatically split in chunks of size MTU - 3.
//                // However, the peripheral's firmware must also support it, so it is not guaranteed to work.
//                // Long writes are also very inefficient because of the confirmation of each write operation.
//                // So it is better to increase MTU if possible. Hence a warning if this write becomes a long write...
//                // See https://stackoverflow.com/questions/48216517/rxandroidble-write-only-sends-the-first-20b
//                Logger.w(
//                    TAG,
//                    "value byte array is longer than allowed by MTU, write will fail if peripheral does not support long writes"
//                )
//            }
//            if (internalWriteCharacteristic(characteristic, bytesToWrite, writeType)) {
//                Logger.d(
//                    TAG,
//                    "writing <%s> to characteristic <%s>",
//                    asHexString(bytesToWrite),
//                    characteristic.uuid
//                )
//                nrTries++
//            } else {
//                Logger.e(
//                    TAG,
//                    "writeCharacteristic failed for characteristic: %s",
//                    characteristic.uuid
//                )
//                completedCommand()
//            }
//        }
//    }
//
//    private fun willCauseLongWrite(value: ByteArray, writeType: WriteType): Boolean {
//        return value.size > currentMtu - 3 && writeType === WriteType.WITH_RESPONSE
//    }
//
//    private fun doesNotSupportWriteType(
//        characteristic: BluetoothGattCharacteristic,
//        writeType: WriteType
//    ): Boolean {
//        return (characteristic.properties and writeType.property) === 0
//    }
//
//    private fun internalWriteCharacteristic(
//        characteristic: BluetoothGattCharacteristic,
//        value: ByteArray,
//        writeType: WriteType
//    ): Boolean {
//        if (bluetoothGatt == null) return false
//
//        currentWriteBytes = value
//
//        if (Build.VERSION.SDK_INT >= 33) {
//            val result = bluetoothGatt!!.writeCharacteristic(
//                characteristic,
//                currentWriteBytes,
//                writeType.writeType
//            )
//            return result == BluetoothStatusCodes.SUCCESS
//        } else {
//            characteristic.writeType = writeType.writeType
//            characteristic.setValue(value)
//            return bluetoothGatt!!.writeCharacteristic(characteristic)
//        }
//    }
//
//    /**
//     * Read the value of a descriptor.
//     *
//     * @param descriptor the descriptor to read
//     * @return true if a write operation was succesfully enqueued, otherwise false
//     */
//    fun readDescriptor(descriptor: BluetoothGattDescriptor): Boolean {
//        Objects.requireNonNull(descriptor, NO_VALID_DESCRIPTOR_PROVIDED)
//
//        return enqueue {
//            if (bluetoothGatt!!.readDescriptor(descriptor)) {
//                Logger.d(
//                    TAG,
//                    "reading descriptor <%s>",
//                    descriptor.uuid
//                )
//                nrTries++
//            } else {
//                Logger.e(
//                    TAG,
//                    "readDescriptor failed for characteristic: %s",
//                    descriptor.uuid
//                )
//                completedCommand()
//            }
//        }
//    }
//
//    /**
//     * Write a value to a descriptor.
//     *
//     *
//     * For turning on/off notifications use [BluetoothPeripheral.setNotify] instead.
//     *
//     * @param descriptor the descriptor to write to
//     * @param value      the value to write
//     * @return true if a write operation was succesfully enqueued, otherwise false
//     */
//    fun writeDescriptor(descriptor: BluetoothGattDescriptor, value: ByteArray): Boolean {
//        Objects.requireNonNull(descriptor, NO_VALID_DESCRIPTOR_PROVIDED)
//        Objects.requireNonNull(value, NO_VALID_VALUE_PROVIDED)
//
//        require(value.size != 0) { VALUE_BYTE_ARRAY_IS_EMPTY }
//
//        require(value.size <= getMaximumWriteValueLength(WriteType.WITH_RESPONSE)) { VALUE_BYTE_ARRAY_IS_TOO_LONG }
//
//        // Copy the value to avoid race conditions
//        val bytesToWrite = copyOf(value)
//
//        return enqueue {
//            if (internalWriteDescriptor(descriptor, bytesToWrite)) {
//                Logger.d(
//                    TAG,
//                    "writing <%s> to descriptor <%s>",
//                    asHexString(bytesToWrite),
//                    descriptor.uuid
//                )
//                nrTries++
//            } else {
//                Logger.e(
//                    TAG,
//                    "writeDescriptor failed for descriptor: %s",
//                    descriptor.uuid
//                )
//                completedCommand()
//            }
//        }
//    }
//
//    private fun internalWriteDescriptor(
//        descriptor: BluetoothGattDescriptor,
//        value: ByteArray
//    ): Boolean {
//        if (bluetoothGatt == null) return false
//
//        currentWriteBytes = value
//
//        if (Build.VERSION.SDK_INT >= 33) {
//            val result = bluetoothGatt!!.writeDescriptor(descriptor, value)
//            return result == BluetoothStatusCodes.SUCCESS
//        } else {
//            adjustWriteTypeIfNeeded(descriptor.characteristic)
//            descriptor.setValue(value)
//            return bluetoothGatt!!.writeDescriptor(descriptor)
//        }
//    }
//
//    /**
//     * Set the notification state of a characteristic to 'on' or 'off'. The characteristic must support notifications or indications.
//     *
//     * @param serviceUUID        the service UUID the characteristic belongs to
//     * @param characteristicUUID the characteristic's UUID
//     * @param enable             true for setting notification on, false for turning it off
//     * @return true if the operation was enqueued, otherwise false
//     * @throws IllegalArgumentException if the CCC descriptor was not found or the characteristic does not support notifications or indications
//     */
//    fun setNotify(serviceUUID: UUID, characteristicUUID: UUID, enable: Boolean): Boolean {
//        Objects.requireNonNull(serviceUUID, NO_VALID_SERVICE_UUID_PROVIDED)
//        Objects.requireNonNull(characteristicUUID, NO_VALID_CHARACTERISTIC_UUID_PROVIDED)
//
//        val characteristic = getCharacteristic(serviceUUID, characteristicUUID)
//        if (characteristic != null) {
//            return setNotify(characteristic, enable)
//        }
//        return false
//    }
//
//    /**
//     * Set the notification state of a characteristic to 'on' or 'off'. The characteristic must support notifications or indications.
//     *
//     *
//     * [BluetoothPeripheralCallback.onNotificationStateUpdate] will be triggered as a result of this call.
//     *
//     * @param characteristic the characteristic to turn notification on/off for
//     * @param enable         true for setting notification on, false for turning it off
//     * @return true if the operation was enqueued, otherwise false
//     * @throws IllegalArgumentException if the CCC descriptor was not found or the characteristic does not support notifications or indications
//     */
//    fun setNotify(characteristic: BluetoothGattCharacteristic, enable: Boolean): Boolean {
//        Objects.requireNonNull(characteristic, NO_VALID_CHARACTERISTIC_PROVIDED)
//
//        // Get the Client Characteristic Configuration Descriptor for the characteristic
//        val descriptor = characteristic.getDescriptor(CCC_DESCRIPTOR_UUID)
//        if (descriptor == null) {
//            val message = String.format(
//                "could not get CCC descriptor for characteristic %s",
//                characteristic.uuid
//            )
//            throw IllegalArgumentException(message)
//        }
//
//        // Check if characteristic has NOTIFY or INDICATE properties and set the correct byte value to be written
//        val value: ByteArray
//        val properties = characteristic.properties
//        value = if ((properties and PROPERTY_NOTIFY) > 0) {
//            BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
//        } else if ((properties and PROPERTY_INDICATE) > 0) {
//            BluetoothGattDescriptor.ENABLE_INDICATION_VALUE
//        } else {
//            val message = String.format(
//                "characteristic %s does not have notify or indicate property",
//                characteristic.uuid
//            )
//            throw IllegalArgumentException(message)
//        }
//        val finalValue = if (enable) value else BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
//
//        return enqueue(Runnable { // First try to set notification for Gatt object
//            if (!bluetoothGatt!!.setCharacteristicNotification(characteristic, enable)) {
//                Logger.e(
//                    TAG,
//                    "setCharacteristicNotification failed for characteristic: %s",
//                    characteristic.uuid
//                )
//                completedCommand()
//                return@Runnable
//            }
//
//            // Then write to CCC descriptor
//            currentWriteBytes = finalValue
//            if (internalWriteDescriptor(descriptor, finalValue)) {
//                nrTries++
//            } else {
//                Logger.e(TAG, "writeDescriptor failed for descriptor: %s", descriptor.uuid)
//                completedCommand()
//            }
//        })
//    }
//
//    private fun adjustWriteTypeIfNeeded(characteristic: BluetoothGattCharacteristic) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//            // Up to Android 6 there is a bug where Android takes the writeType of the parent characteristic instead of always WRITE_TYPE_DEFAULT
//            // See: https://android.googlesource.com/platform/frameworks/base/+/942aebc95924ab1e7ea1e92aaf4e7fc45f695a6c%5E%21/#F0
//            characteristic.writeType = BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
//        }
//    }
//
//    /**
//     * Read the RSSI for a connected remote peripheral.
//     *
//     *
//     * [BluetoothPeripheralCallback.onReadRemoteRssi] will be triggered as a result of this call.
//     *
//     * @return true if the operation was enqueued, false otherwise
//     */
//    fun readRemoteRssi(): Boolean {
//        return enqueue {
//            if (!bluetoothGatt!!.readRemoteRssi()) {
//                Logger.e(TAG, "readRemoteRssi failed")
//                completedCommand()
//            }
//        }
//    }
//
//    /**
//     * Request an MTU size used for a given connection.
//     *
//     *
//     * When performing a write request operation (write without response),
//     * the data sent is truncated to the MTU size. This function may be used
//     * to request a larger MTU size to be able to send more data at once.
//     *
//     *
//     * Note that requesting an MTU should only take place once per connection, according to the Bluetooth standard.
//     *
//     * [BluetoothPeripheralCallback.onMtuChanged] will be triggered as a result of this call.
//     *
//     * @param mtu the desired MTU size
//     * @return true if the operation was enqueued, false otherwise
//     */
//    fun requestMtu(mtu: Int): Boolean {
//        require(!(mtu < DEFAULT_MTU || mtu > MAX_MTU)) { "mtu must be between 23 and 517" }
//
//        return enqueue {
//            if (bluetoothGatt!!.requestMtu(mtu)) {
//                currentCommand = REQUEST_MTU_COMMAND
//                Logger.i(TAG, "requesting MTU of %d", mtu)
//            } else {
//                Logger.e(TAG, "requestMtu failed")
//                completedCommand()
//            }
//        }
//    }
//
//    /**
//     * Request a different connection priority.
//     *
//     * @param priority the requested connection priority
//     * @return true if request was enqueued, false if not
//     */
//    fun requestConnectionPriority(priority: ConnectionPriority): Boolean {
//        Objects.requireNonNull(priority, NO_VALID_PRIORITY_PROVIDED)
//
//        return enqueue {
//            if (bluetoothGatt!!.requestConnectionPriority(priority.value)) {
//                Logger.d(
//                    TAG,
//                    "requesting connection priority %s",
//                    priority
//                )
//            } else {
//                Logger.e(TAG, "could not request connection priority")
//            }
//            // Complete command as there is no reliable callback for this, but allow some time
//            callbackHandler.postDelayed(
//                Runnable { completedCommand() },
//                AVG_REQUEST_CONNECTION_PRIORITY_DURATION
//            )
//        }
//    }
//
//    /**
//     * Set the preferred connection PHY for this app. Please note that this is just a
//     * recommendation, whether the PHY change will happen depends on other applications preferences,
//     * local and remote controller capabilities. Controller can override these settings.
//     *
//     *
//     * [BluetoothPeripheralCallback.onPhyUpdate] will be triggered as a result of this call, even
//     * if no PHY change happens. It is also triggered when remote device updates the PHY.
//     *
//     * @param txPhy      the desired TX PHY
//     * @param rxPhy      the desired RX PHY
//     * @param phyOptions the desired optional sub-type for PHY_LE_CODED
//     * @return true if request was enqueued, false if not
//     */
//    fun setPreferredPhy(txPhy: PhyType, rxPhy: PhyType, phyOptions: PhyOptions): Boolean {
//        Objects.requireNonNull(txPhy)
//        Objects.requireNonNull(rxPhy)
//        Objects.requireNonNull(phyOptions)
//
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//            Logger.e(TAG, "setPreferredPhy requires Android 8.0 or newer")
//            return false
//        }
//
//        return enqueue {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                currentCommand = SET_PHY_TYPE_COMMAND
//                Logger.i(
//                    TAG,
//                    "setting preferred Phy: tx = %s, rx = %s, options = %s",
//                    txPhy,
//                    rxPhy,
//                    phyOptions
//                )
//                bluetoothGatt!!.setPreferredPhy(txPhy.mask, rxPhy.mask, phyOptions.value)
//
//                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
//                    // There is a bug in Android 13 where onPhyUpdate is not always called
//                    // Therefore complete this command after a delay in order not to block the queueu
//                    currentCommand = IDLE
//                    callbackHandler.postDelayed(Runnable { completedCommand() }, 200)
//                }
//            }
//        }
//    }
//
//    /**
//     * Read the current transmitter PHY and receiver PHY of the connection. The values are returned
//     * in [BluetoothPeripheralCallback.onPhyUpdate]
//     */
//    fun readPhy(): Boolean {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//            Logger.e(TAG, "setPreferredPhy requires Android 8.0 or newer")
//            return false
//        }
//
//        return enqueue {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                bluetoothGatt!!.readPhy()
//                Logger.d(TAG, "reading Phy")
//            }
//        }
//    }
//
//    /**
//     * Asynchronous method to clear the services cache. Make sure to add a delay when using this!
//     *
//     * @return true if the method was executed, false if not executed
//     */
//    fun clearServicesCache(): Boolean {
//        if (bluetoothGatt == null) return false
//
//        var result = false
//        try {
//            val refreshMethod: Method? = bluetoothGatt!!.javaClass.getMethod("refresh")
//            if (refreshMethod != null) {
//                result = refreshMethod.invoke(bluetoothGatt)
//            }
//        } catch (e: Exception) {
//            Logger.e(TAG, "could not invoke refresh method")
//        }
//        return result
//    }
//
//    /**
//     * Enqueue a runnable to the command queue
//     *
//     * @param command a Runnable containg a command
//     * @return true if the command was successfully enqueued, otherwise false
//     */
//    private fun enqueue(command: Runnable): Boolean {
//        if (notConnected()) {
//            Logger.e(TAG, PERIPHERAL_NOT_CONNECTED)
//            return false
//        }
//
//        val result = commandQueue.add(command)
//        if (result) {
//            nextCommand()
//        } else {
//            Logger.e(TAG, "could not enqueue command")
//        }
//        return result
//    }
//
//    /**
//     * The current command has been completed, move to the next command in the queue (if any)
//     */
//    private fun completedCommand() {
//        isRetrying = false
//        commandQueue.poll()
//        commandQueueBusy = false
//        nextCommand()
//    }
//
//    /**
//     * Retry the current command. Typically used when a read/write fails and triggers a bonding procedure
//     */
//    private fun retryCommand() {
//        commandQueueBusy = false
//        val currentCommand = commandQueue.peek()
//        if (currentCommand != null) {
//            if (nrTries >= MAX_TRIES) {
//                // Max retries reached, give up on this one and proceed
//                Logger.d(TAG, "max number of tries reached, not retrying operation anymore")
//                commandQueue.poll()
//            } else {
//                isRetrying = true
//            }
//        }
//        nextCommand()
//    }
//
//    /**
//     * Execute the next command in the command queue.
//     * If a command is being executed the next command will not be executed
//     * A queue is used because the calls have to be executed sequentially.
//     * If the command fails, the next command in the queue is executed.
//     */
//    private fun nextCommand() {
//        synchronized(this) {
//            // If there is still a command being executed, then bail out
//            if (commandQueueBusy) return
//
//            // Check if there is something to do at all
//            val bluetoothCommand = commandQueue.peek() ?: return
//
//            // Check if we still have a valid gatt object
//            if (bluetoothGatt == null) {
//                Logger.e(
//                    TAG,
//                    "gatt is 'null' for peripheral '%s', clearing command queue",
//                    address
//                )
//                commandQueue.clear()
//                commandQueueBusy = false
//                return
//            }
//
//            // Check if the peripheral has initiated bonding as this may be a reason for failures
//            if (bondState === BondState.BONDING) {
//                Logger.w(
//                    TAG,
//                    "bonding is in progress, waiting for bonding to complete"
//                )
//                peripheralInitiatedBonding = true
//                return
//            }
//
//            // Execute the next command in the queue
//            commandQueueBusy = true
//            if (!isRetrying) {
//                nrTries = 0
//            }
//            mainHandler.post(object : Runnable {
//                override fun run() {
//                    try {
//                        if (this.isConnected) {
//                            bluetoothCommand.run()
//                        }
//                    } catch (ex: Exception) {
//                        Logger.e(
//                            TAG, "command exception for device '%s'",
//                            this.name
//                        )
//                        Logger.e(TAG, ex.toString())
//                        completedCommand()
//                    }
//                }
//            })
//        }
//    }
//
//    /**
//     * Constructs a new device wrapper around `device`.
//     *
//     * @param context   Android application environment.
//     * @param device    Wrapped Android bluetooth device.
//     * @param listener  Callback to [BluetoothCentralManager].
//     * @param transport Transport to be used during connection phase.
//     */
//    init {
//        this.device = Objects.requireNonNull(device, NO_VALID_DEVICE_PROVIDED)
//        this.peripheralType = PeripheralType.fromValue(device.type)
//        this.listener = Objects.requireNonNull(listener, "no valid listener provided")
//        this.peripheralCallback =
//            Objects.requireNonNull(peripheralCallback, NO_VALID_PERIPHERAL_CALLBACK_PROVIDED)
//        this.callbackHandler =
//            Objects.requireNonNull(callbackHandler, "no valid callback handler provided")
//        this.transport = Objects.requireNonNull(transport, "no valid transport provided")
//    }
//
//    private fun pairingVariantToString(variant: Int): String {
//        return when (variant) {
//            PAIRING_VARIANT_PIN -> "PAIRING_VARIANT_PIN"
//            PAIRING_VARIANT_PASSKEY -> "PAIRING_VARIANT_PASSKEY"
//            PAIRING_VARIANT_PASSKEY_CONFIRMATION -> "PAIRING_VARIANT_PASSKEY_CONFIRMATION"
//            PAIRING_VARIANT_CONSENT -> "PAIRING_VARIANT_CONSENT"
//            PAIRING_VARIANT_DISPLAY_PASSKEY -> "PAIRING_VARIANT_DISPLAY_PASSKEY"
//            PAIRING_VARIANT_DISPLAY_PIN -> "PAIRING_VARIANT_DISPLAY_PIN"
//            PAIRING_VARIANT_OOB_CONSENT -> "PAIRING_VARIANT_OOB_CONSENT"
//            else -> "UNKNOWN"
//        }
//    }
//
//    internal interface InternalCallback {
//        /**
//         * Trying to connect to [BluetoothPeripheral]
//         *
//         * @param peripheral [BluetoothPeripheral] the peripheral.
//         */
//        fun connecting(peripheral: BluetoothPeripheral)
//
//        /**
//         * [BluetoothPeripheral] has successfully connected.
//         *
//         * @param peripheral [BluetoothPeripheral] that connected.
//         */
//        fun connected(peripheral: BluetoothPeripheral)
//
//        /**
//         * Connecting with [BluetoothPeripheral] has failed.
//         *
//         * @param peripheral [BluetoothPeripheral] of which connect failed.
//         */
//        fun connectFailed(peripheral: BluetoothPeripheral, status: HciStatus)
//
//        /**
//         * Trying to disconnect to [BluetoothPeripheral]
//         *
//         * @param peripheral [BluetoothPeripheral] the peripheral.
//         */
//        fun disconnecting(peripheral: BluetoothPeripheral)
//
//        /**
//         * [BluetoothPeripheral] has disconnected.
//         *
//         * @param peripheral [BluetoothPeripheral] that disconnected.
//         */
//        fun disconnected(peripheral: BluetoothPeripheral, status: HciStatus)
//
//        fun getPincode(peripheral: BluetoothPeripheral): String?
//    }
//
//    /////////////////
//    private fun connectGattHelper(
//        remoteDevice: BluetoothDevice?,
//        autoConnect: Boolean,
//        bluetoothGattCallback: BluetoothGattCallback
//    ): BluetoothGatt? {
//        if (remoteDevice == null) {
//            return null
//        }
//
//        /*
//          This bug workaround was taken from the Polidea RxAndroidBle
//          Issue that caused a race condition mentioned below was fixed in 7.0.0_r1
//          https://android.googlesource.com/platform/frameworks/base/+/android-7.0.0_r1/core/java/android/bluetooth/BluetoothGatt.java#649
//          compared to
//          https://android.googlesource.com/platform/frameworks/base/+/android-6.0.1_r72/core/java/android/bluetooth/BluetoothGatt.java#739
//          issue: https://android.googlesource.com/platform/frameworks/base/+/d35167adcaa40cb54df8e392379dfdfe98bcdba2%5E%21/#F0
//          */
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N || !autoConnect) {
//            return connectGattCompat(bluetoothGattCallback, remoteDevice, autoConnect)
//        }
//
//        try {
//            val iBluetoothGatt = getIBluetoothGatt(iBluetoothManager)
//
//            if (iBluetoothGatt == null) {
//                Logger.e(TAG, "could not get iBluetoothGatt object")
//                return connectGattCompat(bluetoothGattCallback, remoteDevice, true)
//            }
//
//            val bluetoothGatt = createBluetoothGatt(iBluetoothGatt, remoteDevice)
//
//            if (bluetoothGatt == null) {
//                Logger.e(TAG, "could not create BluetoothGatt object")
//                return connectGattCompat(bluetoothGattCallback, remoteDevice, true)
//            }
//
//            val connectedSuccessfully =
//                connectUsingReflection(remoteDevice, bluetoothGatt, bluetoothGattCallback, true)
//
//            if (!connectedSuccessfully) {
//                Logger.i(TAG, "connection using reflection failed, closing gatt")
//                bluetoothGatt.close()
//            }
//
//            return bluetoothGatt
//        } catch (exception: NoSuchMethodException) {
//            Logger.e(TAG, "error during reflection")
//            return connectGattCompat(bluetoothGattCallback, remoteDevice, true)
//        } catch (exception: IllegalAccessException) {
//            Logger.e(TAG, "error during reflection")
//            return connectGattCompat(bluetoothGattCallback, remoteDevice, true)
//        } catch (exception: IllegalArgumentException) {
//            Logger.e(TAG, "error during reflection")
//            return connectGattCompat(bluetoothGattCallback, remoteDevice, true)
//        } catch (exception: InvocationTargetException) {
//            Logger.e(TAG, "error during reflection")
//            return connectGattCompat(bluetoothGattCallback, remoteDevice, true)
//        } catch (exception: InstantiationException) {
//            Logger.e(TAG, "error during reflection")
//            return connectGattCompat(bluetoothGattCallback, remoteDevice, true)
//        } catch (exception: NoSuchFieldException) {
//            Logger.e(TAG, "error during reflection")
//            return connectGattCompat(bluetoothGattCallback, remoteDevice, true)
//        }
//    }
//
//    private fun connectGattCompat(
//        bluetoothGattCallback: BluetoothGattCallback,
//        device: BluetoothDevice,
//        autoConnect: Boolean
//    ): BluetoothGatt {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            return device.connectGatt(context, autoConnect, bluetoothGattCallback, transport.value)
//        } else {
//            // Try to call connectGatt with transport parameter using reflection
//            try {
//                val connectGattMethod: Method = device.javaClass.getMethod(
//                    "connectGatt",
//                    Context::class.java,
//                    Boolean::class.javaPrimitiveType,
//                    BluetoothGattCallback::class.java,
//                    Int::class.javaPrimitiveType
//                )
//                try {
//                    return connectGattMethod.invoke(
//                        device,
//                        context,
//                        autoConnect,
//                        bluetoothGattCallback,
//                        transport.value
//                    )
//                } catch (e: IllegalAccessException) {
//                    e.printStackTrace()
//                } catch (e: InvocationTargetException) {
//                    e.printStackTrace()
//                }
//            } catch (e: NoSuchMethodException) {
//                e.printStackTrace()
//            }
//        }
//        // Fallback on connectGatt without transport parameter
//        return device.connectGatt(context, autoConnect, bluetoothGattCallback)
//    }
//
//    @Throws(
//        NoSuchMethodException::class,
//        InvocationTargetException::class,
//        IllegalAccessException::class,
//        NoSuchFieldException::class
//    )
//    private fun connectUsingReflection(
//        device: BluetoothDevice,
//        bluetoothGatt: BluetoothGatt,
//        bluetoothGattCallback: BluetoothGattCallback,
//        autoConnect: Boolean
//    ): Boolean {
//        setAutoConnectValue(bluetoothGatt, autoConnect)
//        val connectMethod: Method = bluetoothGatt.javaClass.getDeclaredMethod(
//            "connect",
//            Boolean::class.java,
//            BluetoothGattCallback::class.java
//        )
//        connectMethod.setAccessible(true)
//        return connectMethod.invoke(bluetoothGatt, true, bluetoothGattCallback)
//    }
//
//    @Throws(
//        IllegalAccessException::class,
//        InvocationTargetException::class,
//        InstantiationException::class
//    )
//    private fun createBluetoothGatt(
//        iBluetoothGatt: Any,
//        remoteDevice: BluetoothDevice
//    ): BluetoothGatt {
//        val bluetoothGattConstructor: Constructor =
//            BluetoothGatt::class.java.declaredConstructors[0]
//        bluetoothGattConstructor.setAccessible(true)
//        return if (bluetoothGattConstructor.getParameterTypes().length === 4) {
//            bluetoothGattConstructor.newInstance(
//                context,
//                iBluetoothGatt,
//                remoteDevice,
//                transport.value
//            )
//        } else {
//            bluetoothGattConstructor.newInstance(context, iBluetoothGatt, remoteDevice)
//        }
//    }
//
//    @Throws(
//        NoSuchMethodException::class,
//        InvocationTargetException::class,
//        IllegalAccessException::class
//    )
//    private fun getIBluetoothGatt(iBluetoothManager: Any?): Any? {
//        if (iBluetoothManager == null) {
//            return null
//        }
//
//        val getBluetoothGattMethod: Method =
//            getMethodFromClass(iBluetoothManager.javaClass, "getBluetoothGatt")
//        return getBluetoothGattMethod.invoke(iBluetoothManager)
//    }
//
//    @get:Throws(
//        NoSuchMethodException::class,
//        InvocationTargetException::class,
//        IllegalAccessException::class
//    )
//    private val iBluetoothManager: Any?
//        get() {
//            val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter() ?: return null
//
//            val getBluetoothManagerMethod: Method =
//                getMethodFromClass(bluetoothAdapter.javaClass, "getBluetoothManager")
//            return getBluetoothManagerMethod.invoke(bluetoothAdapter)
//        }
//
//    @Throws(NoSuchMethodException::class)
//    private fun getMethodFromClass(cls: Class<*>, methodName: String): Method {
//        val method: Method = cls.getDeclaredMethod(methodName)
//        method.setAccessible(true)
//        return method
//    }
//
//    @Throws(NoSuchFieldException::class, IllegalAccessException::class)
//    private fun setAutoConnectValue(bluetoothGatt: BluetoothGatt, autoConnect: Boolean) {
//        val autoConnectField: Field = bluetoothGatt.javaClass.getDeclaredField("mAutoConnect")
//        autoConnectField.setAccessible(true)
//        autoConnectField.setBoolean(bluetoothGatt, autoConnect)
//    }
//
//    private fun startConnectionTimer(peripheral: BluetoothPeripheral) {
//        cancelConnectionTimer()
//        timeoutRunnable = Runnable {
//            Logger.e(
//                TAG, "connection timout, disconnecting '%s'",
//                peripheral.name
//            )
//            disconnect()
//
//            mainHandler.postDelayed(Runnable {
//                if (bluetoothGatt != null) {
//                    bluetoothGattCallback.onConnectionStateChange(
//                        bluetoothGatt,
//                        HciStatus.CONNECTION_FAILED_ESTABLISHMENT.value,
//                        BluetoothProfile.STATE_DISCONNECTED
//                    )
//                }
//            }, 50)
//            timeoutRunnable = null
//        }
//
//        mainHandler.postDelayed(timeoutRunnable, CONNECTION_TIMEOUT_IN_MS)
//    }
//
//    private fun cancelConnectionTimer() {
//        if (timeoutRunnable != null) {
//            mainHandler.removeCallbacks(timeoutRunnable)
//            timeoutRunnable = null
//        }
//    }
//
//    private val timoutThreshold: Int
//        get() {
//            val manufacturer = Build.MANUFACTURER
//            return if (manufacturer.equals("samsung", ignoreCase = true)) {
//                TIMEOUT_THRESHOLD_SAMSUNG
//            } else {
//                TIMEOUT_THRESHOLD_DEFAULT
//            }
//        }
//
//    /**
//     * Make a safe copy of a nullable byte array
//     *
//     * @param source byte array to copy
//     * @return non-null copy of the source byte array or an empty array if source was null
//     */
//    fun copyOf(@Nullable source: ByteArray?): ByteArray {
//        return if ((source == null)) ByteArray(0) else source.copyOf(source.size)
//    }
//
//    /**
//     * Make a byte array nonnull by either returning the original byte array if non-null or an empty bytearray
//     *
//     * @param source byte array to make nonnull
//     * @return the source byte array or an empty array if source was null
//     */
//    fun nonnullOf(@Nullable source: ByteArray?): ByteArray {
//        return if ((source == null)) ByteArray(0) else source
//    }
//
//    companion object {
//        private val TAG: String = BluetoothPeripheral::class.java.simpleName
//        private val CCC_DESCRIPTOR_UUID: UUID =
//            UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
//
//        /**
//         * Max MTU that Android can handle
//         */
//        const val MAX_MTU: Int = 517
//
//        // Minimal and default MTU
//        private const val DEFAULT_MTU = 23
//
//        // Maximum number of retries of commands
//        private const val MAX_TRIES = 2
//
//        // Delay to use when doing a connect
//        private const val DIRECT_CONNECTION_DELAY_IN_MS = 100
//
//        // Timeout to use if no callback on onConnectionStateChange happens
//        private const val CONNECTION_TIMEOUT_IN_MS = 35000
//
//        // Samsung phones time out after 5 seconds while most other phone time out after 30 seconds
//        private const val TIMEOUT_THRESHOLD_SAMSUNG = 4500
//
//        // Most other phone time out after 30 seconds
//        private const val TIMEOUT_THRESHOLD_DEFAULT = 25000
//
//        // When a bond is lost, the bluetooth stack needs some time to update its internal state
//        private const val DELAY_AFTER_BOND_LOST = 1000L
//
//        // The average time it takes to complete requestConnectionPriority
//        private const val AVG_REQUEST_CONNECTION_PRIORITY_DURATION: Long = 500
//
//        private const val NO_VALID_SERVICE_UUID_PROVIDED = "no valid service UUID provided"
//        private const val NO_VALID_CHARACTERISTIC_UUID_PROVIDED =
//            "no valid characteristic UUID provided"
//        private const val NO_VALID_CHARACTERISTIC_PROVIDED = "no valid characteristic provided"
//        private const val NO_VALID_WRITE_TYPE_PROVIDED = "no valid writeType provided"
//        private const val NO_VALID_VALUE_PROVIDED = "no valid value provided"
//        private const val NO_VALID_DESCRIPTOR_PROVIDED = "no valid descriptor provided"
//        private const val NO_VALID_PERIPHERAL_CALLBACK_PROVIDED =
//            "no valid peripheral callback provided"
//        private const val NO_VALID_DEVICE_PROVIDED = "no valid device provided"
//        private const val NO_VALID_PRIORITY_PROVIDED = "no valid priority provided"
//        private const val PERIPHERAL_NOT_CONNECTED = "peripheral not connected"
//        private const val VALUE_BYTE_ARRAY_IS_EMPTY = "value byte array is empty"
//        private const val VALUE_BYTE_ARRAY_IS_TOO_LONG = "value byte array is too long"
//
//        // String constants for commands where the callbacks can also happen because the remote peripheral initiated the command
//        private const val IDLE = 0
//        private const val REQUEST_MTU_COMMAND = 1
//        private const val SET_PHY_TYPE_COMMAND = 2
//
//        private const val PAIRING_VARIANT_PIN = 0
//        private const val PAIRING_VARIANT_PASSKEY = 1
//        private const val PAIRING_VARIANT_PASSKEY_CONFIRMATION = 2
//        private const val PAIRING_VARIANT_CONSENT = 3
//        private const val PAIRING_VARIANT_DISPLAY_PASSKEY = 4
//        private const val PAIRING_VARIANT_DISPLAY_PIN = 5
//        private const val PAIRING_VARIANT_OOB_CONSENT = 6
//    }
//}
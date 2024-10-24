package com.example.count_out.entity

import com.example.count_out.R

enum class Rainfall{ SMALL, MEDIUM, LARGE }
enum class UPDOWN { UP, DOWN, START, END }
enum class TypeKeyboard{ DIGIT, TEXT, PASS, NONE }
enum class Zone(val id: Int, var maxPulse: Int){
    EXTRA_SLOW( id = 1, maxPulse = 100),
    SLOW(id = 2, maxPulse = 120),
    MEDIUM(id = 3, maxPulse = 135),
    HIGH(id = 4, maxPulse = 160),
    MAX(id = 5, maxPulse = 180)
}
enum class ConnectState(val strId: Int) {
    NOT_CONNECTED(R.string.not_connected) ,
    CONNECTING(R.string.connecting),
    CONNECTED(R.string.connected) }
enum class StateBleScanner { RUNNING, END, }
enum class StateBleConnecting { NONE, GET_REMOTE_DEVICE, CONNECT_GAT}
enum class RunningState { Binding, Started, Paused, Stopped }
enum class RoundType(val strId: Int, var amount: Int, var duration: Int){
    UP (R.string.work_up,0,0),
    OUT (R.string.work_out,0,0),
    DOWN (R.string.work_down,0,0);
}
enum class GoalSet(val id: Int){ DISTANCE(1), DURATION(2), COUNT(3), COUNT_GROUP(4), }
enum class ErrorBleService{ NONE, GET_REMOTE_DEVICE, CONNECT_DEVICE, NOT_CONNECT_GATT, DISCOVER_SERVICE }
enum class UnitTime(val arg: Int) { Days(1), Week(2), Month(3), Year(4)}
enum class CommandService{
    START_WORK,
    STOP_WORK,
    PAUSE_WORK,
    CONNECT_DEVICE,
    DISCONNECT_DEVICE,
    START_SCANNING,
    STOP_SCANNING,
    CLEAR_CACHE_BLE,
    START_LOCATION,
    STOP_LOCATION,
    SAVE_TRAINING,
    NOT_SAVE_TRAINING,
}
/**This class describes the HCI error codes as defined in the Bluetooth Standard, Volume 1, Part F,
 * 1.3 HCI Error Code, pages 364-377.
 * See https://www.bluetooth.org/docman/handlers/downloaddoc.ashx?doc_id=478726,*/
enum class HciStatus(val param: Int) {
    UNSUPPORTED_LMP_OR_LL_PARAMETER_VALUE(0x20),
    /** a Controller will not allow a role change at this time.*/
    ROLE_CHANGE_NOT_ALLOWED(0x21),
    /** An LMP transaction failed to respond within the LMP response timeout or an LL transaction failed to respond within the LL response timeout.*/
    LMP_OR_LL_RESPONSE_TIMEOUT(0x22),
    /** An LMP transaction or LL procedure has collided with the same transaction or procedure that is already in progress.*/
    LMP_OR_LL_ERROR_TRANS_COLLISION(0x23),
    /** A Controller sent an LMP PDU with an OpCode that was not allowed.*/
    LMP_PDU_NOT_ALLOWED(0x24),
    /** The requested encryption mode is not acceptable at this time.*/
    ENCRYPTION_MODE_NOT_ACCEPTABLE(0x25),
    /** A link key cannot be changed because a fixed unit key is being used.*/
    LINK_KEY_CANNOT_BE_EXCHANGED(0x26),
    /** The requested Quality of Service is not supported.*/
    REQUESTED_QOS_NOT_SUPPORTED(0x27),
    /** An LMP PDU or LL PDU that includes an instant cannot be performed because the instant when this would have occurred has passed.*/
    INSTANT_PASSED(0x28),
    /** It was not possible to pair as a unit key was requested and it is not supported.*/
    PAIRING_WITH_UNIT_KEY_NOT_SUPPORTED(0x29),
    /** An LMP transaction or LL Procedure was started that collides with an ongoing transaction.*/
    DIFFERENT_TRANSACTION_COLLISION(0x2A),
    /** Undefined error code*/
    UNDEFINED_0x2B(0x2B),
    /** The specified quality of service parameters could not be accepted at this time, but other parameters may be acceptable.*/
    QOS_UNACCEPTABLE_PARAMETER(0x2C),
    /** The specified quality of service parameters cannot be accepted and QoS negotiation should be terminated*/
    QOS_REJECTED(0x2D),
    /** The Controller cannot perform channel assessment because it is not supported.*/
    CHANNEL_CLASSIFICATION_NOT_SUPPORTED(0x2E),
    /** The HCI command or LMP PDU sent is only possible on an encrypted link.*/
    INSUFFICIENT_SECURITY(0x2F),
    /** A parameter value requested is outside the mandatory range of parameters for the given HCI command or LMP PDU and the recipient does not accept that value.*/
    PARAMETER_OUT_OF_RANGE(0x30),
    /** Undefined error*/
    UNDEFINED_0x31(0x31),
    /** A Role Switch is pending. This can be used when an HCI command or LMP PDU cannot be accepted because of a pending role switch. This can also be used to notify a peer device about a pending role switch.*/
    ROLE_SWITCH_PENDING(0x32),
    /** Undefined error*/
    UNDEFINED_0x33(0x33),
    /** The current Synchronous negotiation was terminated with the negotiation state set to Reserved Slot Violation.*/
    RESERVED_SLOT_VIOLATION(0x34),
    /** A role switch was attempted but it failed and the original piconet structure is restored.*/
    ROLE_SWITCH_FAILED(0x35),
    /** The extended inquiry response, with the requested requirements for FEC, is too large to fit in any of the packet types supported by the Controller.*/
    INQUIRY_RESPONSE_TOO_LARGE(0x36),
    /** The IO capabilities request or response was rejected because the sending Host does not support Secure Simple Pairing even though the receiving Link Manager does.*/
    SECURE_SIMPLE_PAIRING_NOT_SUPPORTED(0x37),
    /** The Host is busy with another pairing operation and unable to support the requested pairing. The receiving device should retry pairing again later.*/
    HOST_BUSY_PAIRING(0x38),
    /** The Controller could not calculate an appropriate value for the Channel selection operation.*/
    CONNECTION_REJECTED_NO_SUITABLE_CHANNEL(0x39),
    /** The Controller was busy and unable to process the request.*/
    CONTROLLER_BUSY(0x3A),
    /** The remote device either terminated the connection or rejected a request because of one or more unacceptable connection parameters.*/
    UNACCEPTABLE_CONNECTION_PARAMETERS(0x3B),
    /** Advertising for a fixed duration completed or, for directed advertising, that advertising completed without a connection being created.*/
    ADVERTISING_TIMEOUT(0x3C),
    /** The connection was terminated because the Message Integrity Check (MIC) failed on a received packet.*/
    CONNECTION_TERMINATED_MIC_FAILURE(0x3D),
    /** The LL initiated a connection but the connection has failed to be established.*/
    CONNECTION_FAILED_ESTABLISHMENT(0x3E),
    /** The MAC of the 802.11 AMP was requested to connect to a peer, but the connection failed.*/
    MAC_CONNECTION_FAILED(0x3F),
    /** The master, at this time, is unable to make a coarse adjustment to the piconet clock, using the supplied parameters. Instead the master will attempt to move the clock using clock dragging.*/
    COARSE_CLOCK_ADJUSTMENT_REJECTED(0x40),
    /** The LMP PDU is rejected because the Type 0 submap is not currently defined.*/
    TYPE0_SUBMAP_NOT_DEFINED(0x41),
    /** A command was sent from the Host that should identify an Advertising or Sync handle, but the Advertising or Sync handle does not exist.*/
    UNKNOWN_ADVERTISING_IDENTIFIER(0x42),
    /** The number of operations requested has been reached and has indicated the completion of the activity (e.g., advertising or scanning).*/
    LIMIT_REACHED(0x43),
    /** A request to the Controller issued by the Host and still pending was successfully canceled.*/
    OPERATION_CANCELLED_BY_HOST(0x44),
    /** An attempt was made to send or receive a packet that exceeds the maximum allowed packet length.*/
    PACKET_TOO_LONG(0x45),
    // Additional Android specific errors
    ERROR(0x85),
    /** Failure to register client when trying to connect. Probably because the max (30) of clients has been reached.
     * The most likely fix is to make sure you always call close() after a disconnect happens.*/
    FAILURE_REGISTERING_CLIENT(0x101),
    /** An undefined error occurred
     * See https://android.googlesource.com/platform/system/bt/+/master/stack/include/hci_error_code.h*/
    UNDEFINED(0xFF),
    /** Used when status code is not defined in the class*/
    UNKNOWN_STATUS_CODE(0xFFFF)
}
fun hciStatusFromValue(value: Int) = HciStatus.entries.find { item-> item.param == value } ?: HciStatus.UNKNOWN_STATUS_CODE
package net.treelzebub.chatooth.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import net.treelzebub.chatooth.BuildConfig
import net.treelzebub.chatooth.convenience.unbox
import java.util.*

/**
 * Created by Tre Murillo on 8/27/16
 */
object Bluetooth {

    const val CHATOOTH_NAME     = BuildConfig.APPLICATION_ID
          val CHATOOTH_UUID     = UUID.fromString("ITS-A-TOTALLY-UNIQUE-IDENTIFIER")
    const val REQUEST_ENABLE_BT = 0x137

    private val adapter: BluetoothAdapter? get() = BluetoothAdapter.getDefaultAdapter()

    val isSupported   = adapter != null
    val isEnabled     = adapter?.isEnabled.unbox()
    val pairedDevices = adapter?.bondedDevices ?: setOf()

    // startDiscovery is async and returns a bool indicating successful initiation of discovery.
    // initial discovery lasts 12 seconds.
    fun discover()      = adapter!!.startDiscovery()
    fun stopDiscovery() = adapter!!.cancelDiscovery()

    val serverSocket: BluetoothServerSocket get() {
        return adapter!!.listenUsingRfcommWithServiceRecord(CHATOOTH_NAME, CHATOOTH_UUID)
    }
}
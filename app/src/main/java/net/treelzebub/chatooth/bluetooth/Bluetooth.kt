package net.treelzebub.chatooth.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import net.treelzebub.chatooth.convenience.unbox

/**
 * Created by Tre Murillo on 8/27/16
 */
object Bluetooth {

    const val REQUEST_ENABLE_BT = 0x37

    private val adapter: BluetoothAdapter? get() = BluetoothAdapter.getDefaultAdapter()

    val isSupported   = adapter != null
    val isEnabled     = adapter?.isEnabled.unbox()
    val pairedDevices = adapter?.bondedDevices ?: setOf()

    fun requestEnable(a: AppCompatActivity) {
        val i = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        a.startActivityForResult(i, REQUEST_ENABLE_BT)
    }

    // startDiscovery is async and returns a bool indicating successful initiation of discovery.
    // initial discovery lasts 12 seconds.
    fun discover() = adapter!!.startDiscovery()
}
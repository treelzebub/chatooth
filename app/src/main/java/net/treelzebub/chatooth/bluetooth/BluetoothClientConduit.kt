package net.treelzebub.chatooth.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import com.levelmoney.conduit.Conduit
import com.levelmoney.observefragment.activity.ObserveAppCompatActivity
import java.io.IOException

/**
 * Created by Tre Murillo on 8/27/16
 */
class BluetoothClientConduit(a: ObserveAppCompatActivity) : Conduit<BluetoothClientConduit, BluetoothSocket?>(a) {

    override fun onLoad(args: Bundle?): BluetoothSocket? {
        Bluetooth.stopDiscovery()
        val remote = args!!.getParcelable<BluetoothDevice>("device")
        var socket: BluetoothSocket? = null
        try {
            socket = remote.createRfcommSocketToServiceRecord(Bluetooth.CHATOOTH_UUID)
            socket.connect()
        } catch (e: IOException) {
            socket?.close()
            return null
        }
        return socket
    }

    fun load(device: BluetoothDevice) {
        load(Bundle().apply { putParcelable("device", device) })
    }
}

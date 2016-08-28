package net.treelzebub.chatooth.bluetooth

import android.bluetooth.BluetoothSocket
import android.os.Bundle
import com.levelmoney.conduit.Conduit
import com.levelmoney.observefragment.activity.ObserveAppCompatActivity
import java.io.IOException

/**
 * Created by Tre Murillo on 8/27/16
 */
class BluetoothServerConduit(a: ObserveAppCompatActivity) : Conduit<BluetoothServerConduit, BluetoothSocket?>(a) {

    override fun onLoad(args: Bundle?): BluetoothSocket? {
        val serverSocket = Bluetooth.serverSocket
        var openedSocket: BluetoothSocket?
        while (true) {
            try {
                openedSocket = serverSocket.accept()
            } catch (e: IOException) {
                return null
            }

            if (openedSocket != null) {
                // A connection was accepted
                serverSocket.close()
                return openedSocket
            }
        }
    }
}

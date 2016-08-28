package net.treelzebub.chatooth.activity.discovery

import android.bluetooth.BluetoothDevice
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import net.treelzebub.chatooth.R
import net.treelzebub.chatooth.convenience.inflate
import net.treelzebub.chatooth.convenience.setText

/**
 * Created by Tre Murillo on 8/27/16
 */
class DiscoveryAdapter : RecyclerView.Adapter<DiscoveryHolder>() {

    private val itemLayout = R.layout.item_discovery_device

    var devices: List<BluetoothDevice> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DiscoveryHolder(parent.inflate(itemLayout))

    override fun onBindViewHolder(holder: DiscoveryHolder, position: Int) = holder.populate(position, devices[position])

    override fun getItemCount() = devices.size

    fun add(device: BluetoothDevice) {
        if (device in devices) return
        devices = devices.toMutableList().apply { add(device) }
        notifyItemChanged(devices.size)
    }

    fun remove(device: BluetoothDevice) {
        val idx = devices.indexOf(device)
        devices = devices.toMutableList().apply { remove(device) }
        notifyItemChanged(idx)
    }

    fun modify(device: BluetoothDevice) {
        val existing = devices.firstOrNull { it.address === device.address }
        if (existing != null) {
            val idx = devices.indexOf(existing)
            devices = devices.toMutableList().apply { this[idx] = device }
        } else {
            add(device)
        }
    }
}

class DiscoveryHolder(v: View) : RecyclerView.ViewHolder(v) {

    fun populate(itemIdx: Int, device: BluetoothDevice) {
        setText(R.id.device_number) { "" + itemIdx + 1 }
        setText(R.id.device_name)   { device.name }
        setText(R.id.status)        { device.bondState.status() }
    }

    private fun Int?.status(): String {
        return when (this) {
            BluetoothDevice.BOND_BONDED  -> "Connected"
            BluetoothDevice.BOND_BONDING -> "Connecting"
            else                         -> "Not Connected"
        }
    }
}

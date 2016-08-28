package net.treelzebub.chatooth.activity.discovery

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import butterknife.bindView
import net.treelzebub.chatooth.R
import net.treelzebub.chatooth.bluetooth.Bluetooth
import net.treelzebub.chatooth.convenience.onNextLayout
import net.treelzebub.chatooth.convenience.str
import org.jetbrains.anko.find

class DiscoveryActivity : AppCompatActivity(), DiscoveryListener {

    companion object {
        const val REQUEST_DISCOVERABLE = 0x615
    }

    private val remaining by bindView<TextView>(R.id.remaining)

    private val receiver  = DiscoveryReceiver()
    private val adapter   = DiscoveryAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discovery)
        registerReceiver(receiver, IntentFilter(ACTION_FOUND))
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        find<RecyclerView>(R.id.recycler).let {
            it.layoutManager = LinearLayoutManager(this@DiscoveryActivity)
            it.itemAnimator  = DefaultItemAnimator()
            it.adapter       = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        Bluetooth.discover()
        requestDiscoverable()
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.discovery, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onDeviceSelect(device: BluetoothDevice) {
        Bluetooth.pairWith(device)
    }

    private fun requestDiscoverable() {
        val i = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        i.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 240)
        startActivity(i)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode === REQUEST_DISCOVERABLE) {
            onNextLayout {
                remaining.text = R.string.remaining_x_secs.str("$resultCode")
            }
        }
    }

    private inner class DiscoveryReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action ?: return
            val device: BluetoothDevice = intent!!.getParcelableExtra(EXTRA_DEVICE) ?: return

            when (action) {
                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                    //start spinner
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    //stop spinner
                }

                ACTION_FOUND               -> adapter.add(device)
                ACTION_ACL_DISCONNECTED    -> adapter.remove(device)
                ACTION_BOND_STATE_CHANGED,
                ACTION_NAME_CHANGED        -> adapter.modify(device)
            }
        }
    }
}

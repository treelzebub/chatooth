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
import net.treelzebub.chatooth.R
import net.treelzebub.chatooth.bluetooth.Bluetooth
import org.jetbrains.anko.find

class DiscoveryActivity : AppCompatActivity() {

    private val receiver = DiscoveryReceiver()
    private val adapter  = DiscoveryAdapter()

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

package net.treelzebub.chatooth.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import butterknife.bindView
import net.treelzebub.chatooth.R

/**
 * Created by Tre Murillo on 8/27/16
 */
class DrawerActivity : ChatoothActivity(), NavigationView.OnNavigationItemSelectedListener {

    internal val toolbar by bindView<Toolbar>(R.id.toolbar)
    internal val drawer  by bindView<DrawerLayout>(R.id.drawer_layout)
    internal val nav     by bindView<NavigationView>(R.id.nav_view)
    internal val toggle  = ActionBarDrawerToggle(this, drawer, toolbar,
                                                 R.string.navigation_drawer_open,
                                                 R.string.navigation_drawer_close)

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        nav.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_camera    -> {}
            R.id.nav_gallery   -> {}
            R.id.nav_slideshow -> {}
            R.id.nav_manage    -> {}
            R.id.nav_share     -> {}
            R.id.nav_send      -> {}
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}

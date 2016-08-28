package net.treelzebub.chatooth.convenience

import android.app.Activity
import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import org.jetbrains.anko.find

/**
 * Created by Tre Murillo on 8/27/16
 */

fun Activity.setText(@IdRes textViewId: Int, text: () -> String) {
    find<TextView>(textViewId).text = text()
}

fun RecyclerView.ViewHolder.setText(@IdRes textViewId: Int, text: () -> String) {
    itemView.find<TextView>(textViewId).text = text()
}
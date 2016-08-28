package net.treelzebub.chatooth.convenience

import android.app.Activity
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver

/**
 * Created by Tre Murillo on 8/27/16
 */

val RecyclerView.ViewHolder.inflater: LayoutInflater get() = LayoutInflater.from(this.itemView.context)
val View.inflater: LayoutInflater                    get() = LayoutInflater.from(this.context)
val ViewGroup.inflater: LayoutInflater               get() = LayoutInflater.from(this.context)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attach: Boolean = false): View = inflater.inflate(layoutRes, this, attach)

fun View.onNextLayout(fn: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(SingleLayoutListener(this, fn))
}

fun Activity.onNextLayout(fn: () -> Unit) {
    findViewById(android.R.id.content).onNextLayout(fn)
}

private class SingleLayoutListener(
        private val view: View,
        private val fn: () -> Unit
) : ViewTreeObserver.OnGlobalLayoutListener {

    override fun onGlobalLayout() {
        view.viewTreeObserver.removeOnGlobalLayoutListener(this)
        fn()
    }
}

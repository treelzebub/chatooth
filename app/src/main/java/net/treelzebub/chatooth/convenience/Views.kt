package net.treelzebub.chatooth.convenience

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Tre Murillo on 8/27/16
 */

val RecyclerView.ViewHolder.inflater: LayoutInflater get() = LayoutInflater.from(this.itemView.context)
val View.inflater: LayoutInflater                    get() = LayoutInflater.from(this.context)
val ViewGroup.inflater: LayoutInflater               get() = LayoutInflater.from(this.context)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attach: Boolean = false): View = inflater.inflate(layoutRes, this, attach)


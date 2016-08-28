package net.treelzebub.chatooth.convenience

import android.content.Context
import net.treelzebub.chatooth.injection.ContextInjection

/**
 * Created by Tre Murillo on 8/27/16
 */

fun Int.str(c: Context = ContextInjection.c)                        = c.getString(this)

fun Int.str(vararg params: String) = ContextInjection.c.getString(this, *params)

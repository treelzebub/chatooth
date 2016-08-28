package net.treelzebub.chatooth

import android.app.Application
import net.treelzebub.chatooth.injection.ContextInjection

/**
 * Created by Tre Murillo on 8/27/16
 */
class ChatoothApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextInjection.c = this
    }
}

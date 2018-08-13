package com.ansgar.memorymanager

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import java.lang.ref.WeakReference

object MemoryManager {

    private var weakContext: WeakReference<Context>? = null
    private var weakServiceReceiver: WeakReference<ServiceReceiver>? = null
    var delay: Long = 0

    fun init(context: Context): MemoryManager {
        if (this.weakContext == null) {
            this.weakContext = WeakReference(context)
            weakServiceReceiver = WeakReference(ServiceReceiver(weakContext?.get()))

            val intentFilter = IntentFilter()
            // TODO Create File with constants
            intentFilter.addAction("action")
            weakContext?.get()?.registerReceiver(weakServiceReceiver?.get(), intentFilter)

            val intent = Intent(weakContext?.get(), MemoryManagerService::class.java)
            weakContext?.get()?.startService(intent)
        }
        return this
    }

    fun destroy() {
        Log.i("!!!!", "Destroyed")
        val intent = Intent(weakContext?.get(), MemoryManagerService::class.java)
        weakContext?.get()?.stopService(intent)
        weakContext?.get()?.unregisterReceiver(weakServiceReceiver?.get())
    }

}

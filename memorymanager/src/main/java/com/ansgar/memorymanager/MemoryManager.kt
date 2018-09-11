package com.ansgar.memorymanager

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import java.lang.ref.WeakReference

object MemoryManager {

    var delay: Long = 0
    var maxHeapSize = 50
    var x: Int = 0
    var y: Int = 0

    private var weakContext: WeakReference<Context>? = null
    private var weakServiceReceiver: WeakReference<ServiceReceiver>? = null

    fun init(context: Context): MemoryManager {
        weakContext = WeakReference(context)
        val overlay = OverlayView.initOverlayView("")
        OverlayView.weakContext = WeakReference(context)

        weakServiceReceiver = WeakReference(ServiceReceiver())

        val intentFilter = IntentFilter()
        intentFilter.addAction(Constants.EXTRA_ACTION)
        weakContext?.get()?.registerReceiver(weakServiceReceiver?.get(), intentFilter)

        val intent = Intent(weakContext?.get(), MemoryManagerService::class.java)
        weakContext?.get()?.startService(intent)

        return this
    }

    fun destroy() {
        val intent = Intent(weakContext?.get(), MemoryManagerService::class.java)
        weakContext?.get()?.stopService(intent)
        if (weakServiceReceiver?.get() != null) {
            weakContext?.get()?.unregisterReceiver(weakServiceReceiver?.get())
        }

        OverlayView.destroy()
    }

}

package com.ansgar.memorymanager

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*

internal class MemoryManagerService : Service() {

    private val tag = MemoryManagerService::class.java.canonicalName

    var timer: Timer? = null

    override fun onCreate() {
        timer = Timer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val delay = MemoryManager.delay

        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val memory = MemoryManagerUtil.getAppMemoryUsage() + "\n" + MemoryManagerUtil.getCpuAppUsage(1)
                val responseIntent = Intent()
                responseIntent.action = Constants.EXTRA_ACTION
                responseIntent.putExtra(Constants.EXTRA_MEMORY_USAGE_DATA, memory)
                sendBroadcast(responseIntent)
            }
        }, 5000, delay)

        return Service.START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }
}
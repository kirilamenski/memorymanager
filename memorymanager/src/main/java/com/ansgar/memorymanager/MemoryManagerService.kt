package com.ansgar.memorymanager

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*

internal class MemoryManagerService : Service() {

    private var timer: Timer? = null

    override fun onCreate() {
        timer = Timer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val responseIntent = Intent()
                responseIntent.action = Constants.EXTRA_ACTION
                sendBroadcast(responseIntent)
            }
        }, 1000, MemoryManager.delay)

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
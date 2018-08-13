package com.ansgar.memorymanager

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import java.util.*

class MemoryManagerService : Service() {

    private val tag = MemoryManagerService::class.java.canonicalName

    var timer: Timer? = null

    override fun onCreate() {
        Log.i(tag, "On Create")
        timer = Timer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val delay = MemoryManager.delay

        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val memory = MemoryManagerUtil.showAppMemoryUsage()
                val responseIntent = Intent()
                responseIntent.action = "action"
                responseIntent.putExtra("data", memory)
                sendBroadcast(responseIntent)
            }
        }, 5000, delay)

        return Service.START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.i(tag, "On Bind")
        return null
    }

    override fun onDestroy() {
        Log.i(tag, "On Destroyed")
        timer?.cancel()
        super.onDestroy()
    }
}
package com.ansgar.memorymanager

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.Choreographer
import java.util.*
import android.os.Looper


internal class MemoryManagerService : Service() {

    private val tag = MemoryManagerService::class.java.canonicalName

    private var timer: Timer? = null

    override fun onCreate() {
        timer = Timer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val delay = MemoryManager.delay

        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val memory = MemoryManagerUtil.getAppMemoryUsage() + "\n" + MemoryManagerUtil.getCpuAppUsage()
                val responseIntent = Intent()
                responseIntent.action = Constants.EXTRA_ACTION
                responseIntent.putExtra(Constants.EXTRA_MEMORY_USAGE_DATA, memory)
                sendBroadcast(responseIntent)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    val frameCallback = Choreographer.FrameCallback { nanos ->
                        Log.i("!!!!", "Nanos: $nanos")
                    }

                    Handler(Looper.getMainLooper()).post {
                        Choreographer.getInstance().postFrameCallback(frameCallback)
                    }
                }

            }
        }, 2000, delay)

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
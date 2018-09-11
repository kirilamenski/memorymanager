package com.ansgar.memorymanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Choreographer

internal class ServiceReceiver : BroadcastReceiver() {

    override fun onReceive(ctx: Context?, data: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            val text = data?.getStringExtra(Constants.EXTRA_MEMORY_USAGE_DATA)
                    ?: Constants.NOT_AVAILABLE

            val frameCallback = Choreographer.FrameCallback { nanos ->
                OverlayView.initOverlayView(text + MemoryManagerUtil.getFps(nanos, 20))
            }

            Handler(Looper.getMainLooper()).post {
                Choreographer.getInstance().postFrameCallback(frameCallback)
            }
        }
    }

}
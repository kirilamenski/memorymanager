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
            val frameCallback = Choreographer.FrameCallback { nanos ->
                OverlayView.initOverlayView(getString(nanos))
            }

            Handler(Looper.getMainLooper()).post {
                Choreographer.getInstance().postFrameCallback(frameCallback)
            }
        }
    }

    private fun getString(nanos: Long): String {
        val stringBuilder = StringBuilder()
        return stringBuilder.append(MemoryManagerUtil.getAppMemoryUsage())
                .append("\n")
                .append(MemoryManagerUtil.getCpuAppUsage())
                .append("\n")
                .append("FPS: ")
                .append(MemoryManagerUtil.getFps(nanos, 20))
                .toString()
    }

}
package com.ansgar.memorymanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

internal class ServiceReceiver : BroadcastReceiver() {

    override fun onReceive(ctx: Context?, data: Intent?) {
        val text = data?.getStringExtra(Constants.EXTRA_MEMORY_USAGE_DATA) ?: Constants.NOT_AVAILABLE

        OverlayView.initOverlayView(text)
    }

}
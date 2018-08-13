package com.ansgar.memorymanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView

class ServiceReceiver(private val context: Context?) : BroadcastReceiver() {

    //TODO split view to another file !!!!
    //TODO Use singleton for this purpose and create onDestroy method to prevent context leak after closing app
    var textView: TextView? = null
    var windowManager: WindowManager? = null

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (context == null) return

        var text = p1?.getStringExtra("data") ?: "Not available"

        if (windowManager == null) {
            val params = WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT)
            params.gravity = Gravity.TOP or Gravity.LEFT
            params.x = 100
            params.y = 400
            windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            textView = TextView(context)
            textView?.setBackgroundResource(R.drawable.popup_background)
            textView?.setTextColor(ContextCompat.getColor(context, android.R.color.white))
            windowManager?.addView(textView, params)
        }

        textView?.text = text
    }

}
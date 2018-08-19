package com.ansgar.memorymanager

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import java.lang.ref.WeakReference

internal object OverlayView {

    var weakContext: WeakReference<Context>? = null

    private var windowManager: WindowManager? = null
    private var weakTextView: WeakReference<TextView>? = null

    fun initOverlayView(text: String): OverlayView {
        if (windowManager == null && weakContext != null) {
            windowManager = weakContext?.get()?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            weakTextView = getTextView()
        }

        weakTextView?.get()?.text = text

        return this
    }

    fun destroy() {
        windowManager?.removeView(weakTextView?.get())
        weakContext = null
        weakTextView = null
        windowManager = null
    }

    private fun getWindowManagerParams(): WindowManager.LayoutParams {
        val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.TOP or Gravity.LEFT
        params.x = MemoryManager.x
        params.y = MemoryManager.y
        return params
    }

    private fun getTextView() : WeakReference<TextView> {
        val weakTv = WeakReference(TextView(weakContext?.get()))
        weakTv.get()?.setBackgroundResource(R.drawable.popup_background)
        weakTv.get()?.setTextColor(Color.WHITE)
        weakTv.get()?.setPadding(25, 10, 25, 10)
        weakTv.get()?.let {
            windowManager?.addView(it, getWindowManagerParams())
        }

        return weakTv
    }

    private fun getType(): Int = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY
    } else {
        WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
    }

}
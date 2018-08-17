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
            val windowManagerParams = getWindowManagerParams()
            windowManagerParams.gravity = Gravity.TOP or Gravity.LEFT
            windowManagerParams.x = MemoryManager.x
            windowManagerParams.y = MemoryManager.y

            windowManager = weakContext?.get()?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            weakTextView = WeakReference(TextView(weakContext?.get()))
            weakTextView?.get()?.setBackgroundResource(R.drawable.popup_background)
            weakTextView?.get()?.setTextColor(Color.WHITE)
            weakTextView?.get()?.let {
                windowManager?.addView(it, windowManagerParams)
            }
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

    private fun getWindowManagerParams(): WindowManager.LayoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
    )

    private fun getType(): Int = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY
    } else {
        WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
    }

}
package com.ansgar.memorymanager

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.*
import android.widget.TextView
import java.lang.ref.WeakReference

internal object OverlayView {

    var weakContext: WeakReference<Context>? = null

    private var windowManager: WindowManager? = null
    private var weakTextView: WeakReference<TextView>? = null
    private var screenHeight: Int = 0

    fun initOverlayView(text: String): OverlayView {
        if (windowManager == null && weakContext != null) initWindowManager()

        weakTextView?.get()?.text = text

        screenHeight = weakContext?.let { ScreenUtil(it).getScreenHeight() } ?: 0

        return this
    }

    fun destroy() {
        windowManager?.removeView(weakTextView?.get())
        weakContext = null
        weakTextView = null
        windowManager = null
    }

    private fun initWindowManager() {
        windowManager = weakContext?.get()?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        weakTextView = getTextView()
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

    private fun getTextView(): WeakReference<TextView> {
        val weakTv = WeakReference(TextView(weakContext?.get()))
        weakTv.get()?.setBackgroundResource(R.drawable.popup_background)
        weakTv.get()?.setTextColor(Color.WHITE)
        weakTv.get()?.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        weakTv.get()?.setPadding(25, 10, 25, 10)
        weakTv.get()?.let {
            windowManager?.addView(it, getWindowManagerParams())
        }

        // TODO make it movable
//        weakTv.get()?.setOnTouchListener(onTouchListener)

        return weakTv
    }

    private var xOffset = 0f
    private var yOffset = 0f
    @SuppressLint("ClickableViewAccessibility")
    private var onTouchListener = View.OnTouchListener { view, motionEvent ->
        var canBeMoving = true

        val x = motionEvent.x
        val y = motionEvent.y

        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                canBeMoving = !onMotionDown(x, y)
//                if (!canBeMoving) weakTextView?.get()?.setOnTouchListener(null)
            }
            MotionEvent.ACTION_MOVE -> onMotionMove(x, y)
            MotionEvent.ACTION_UP -> onMotionUp(x, y)
        }
        true
    }

    private fun onMotionDown(x: Float, y: Float): Boolean {
        val textView = weakTextView?.get()

        xOffset = x - (textView?.x ?: 0f)
        yOffset = y - (textView?.y ?: 0f)

        return x < (textView?.x ?: 0f) || x > ((textView?.x ?: 0f) + (textView?.width ?: 0))
                || y < (textView?.y ?: 0f) || y > ((textView?.y ?: 0f) + (textView?.height ?: 0))
    }

    private fun onMotionMove(x: Float, y: Float) {
        val textView = weakTextView?.get()
        textView?.x = x - xOffset
        textView?.y = y - yOffset
    }

    private fun onMotionUp(x: Float, y: Float) {

    }

    private fun getType(): Int = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY
    } else {
        WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
    }

}
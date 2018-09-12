package com.ansgar.memorymanager

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.provider.Settings
import android.view.*
import android.widget.TextView
import android.widget.Toast
import java.lang.ref.WeakReference

internal object OverlayView {

    var weakContext: WeakReference<Context>? = null

    private var windowManager: WindowManager? = null
    private var weakTextView: WeakReference<TextView>? = null

    fun initOverlayView(text: String): OverlayView {
        if (windowManager == null && weakContext != null && weakContext?.get() != null) {
            initWindowManager()
        }

        weakTextView?.get()?.text = text

        return this
    }

    fun destroy() {
        weakTextView?.get()?.let { windowManager?.removeView(it) }
        weakContext = null
        weakTextView = null
        windowManager = null
    }

    private fun initWindowManager() {
        windowManager = weakContext?.get()?.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (weakContext != null && weakContext?.get() != null && weakContext?.get() is Application) {
            if (!checkPermission()) return
        }
        weakTextView = getTextView()
    }

    private fun getWindowManagerParams(): WindowManager.LayoutParams {
        val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                getType(),
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.TOP or Gravity.LEFT
        params.x = MemoryManager.x
        params.y = MemoryManager.y
        return params
    }

    @SuppressLint("ClickableViewAccessibility")
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
        weakTv.get()?.setOnTouchListener(onTouchListener)

        return weakTv
    }

    private var xOffset = 0f
    private var yOffset = 0f
    @SuppressLint("ClickableViewAccessibility")
    private var onTouchListener = View.OnTouchListener { _, motionEvent ->
        val x = motionEvent.x
        val y = motionEvent.y
        val rawX = motionEvent.rawX
        val rawY = motionEvent.rawY

        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> onMotionDown(x, y)
            MotionEvent.ACTION_MOVE -> onMotionMove(rawX, rawY)
            MotionEvent.ACTION_UP -> onMotionUp(rawX, rawY)
        }
        true
    }

    private fun onMotionDown(x: Float, y: Float) {
        val textView = weakTextView?.get()

        xOffset = x - (textView?.x ?: 0f)
        yOffset = y - (textView?.y ?: 0f)
    }

    private fun onMotionMove(x: Float, y: Float) {
        val textView = weakTextView?.get()
        val params = textView?.layoutParams as WindowManager.LayoutParams
        params.x = (x - xOffset).toInt()
        params.y = (y - yOffset).toInt()

        windowManager?.updateViewLayout(textView, params)
    }

    private fun onMotionUp(x: Float, y: Float) {

    }

    private fun getType(): Int = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY
    } else {
        if (weakContext?.get() is Application) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
        }
    }

    private fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(weakContext?.get())) {
                Toast.makeText(OverlayView.weakContext?.get(),
                        "You need to enable permission",
                        Toast.LENGTH_LONG).show()
                MemoryManager.destroy()
                return false
            }
        }
        return true
    }

}
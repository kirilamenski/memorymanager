package com.ansgar.memorymanager

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import java.lang.ref.WeakReference

class ScreenUtil(val weakContext: WeakReference<Context>) {

    fun getDisplayMetric(): DisplayMetrics {
        val windowManager: WindowManager = weakContext.get()?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)
        return displayMetrics
    }

    fun getScreenHeight(): Int {
        return getDisplayMetric().heightPixels
    }

    fun getScreenWidth(): Int {
        return getDisplayMetric().widthPixels
    }

    fun convertToPixels(dp: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            dp, getDisplayMetric())

    fun convertToDp(px: Int): Float = px / getDisplayMetric().density

    fun getDensity(): Float = getDisplayMetric().density

}
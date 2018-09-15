package com.ansgar.example

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDexApplication
import com.ansgar.memorymanager.MemoryManager

class MainApp : MultiDexApplication(), Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(p0: Activity?) {

    }

    override fun onActivityResumed(p0: Activity?) {

    }

    override fun onActivityStarted(p0: Activity?) {

    }

    override fun onActivityDestroyed(p0: Activity?) {
//        MemoryManager.destroy()
    }

    override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {

    }

    override fun onActivityStopped(p0: Activity?) {

    }

    override fun onActivityCreated(p0: Activity?, p1: Bundle?) {

    }

    override fun onCreate() {
        super.onCreate()
//        val memoryManager = MemoryManager.init(applicationContext)
//        memoryManager?.delay = 1000
//        memoryManager?.maxHeapSize = 10
//        memoryManager?.x = 10
//        memoryManager?.y = 300
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        registerActivityLifecycleCallbacks(this)
    }

}
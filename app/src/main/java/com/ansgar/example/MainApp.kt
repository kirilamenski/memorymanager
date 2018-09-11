package com.ansgar.example

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.multidex.MultiDexApplication

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

        // Can be used by application context but need to enable permission Display overlay
//        val memoryManager = MemoryManager.init(applicationContext)
//        memoryManager.delay = 3000
    }

}
package com.ansgar.memorymanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Gravity

class MainActivity : AppCompatActivity() {

    //    var popUp: MemoryPopUp? = null
    var receiver: MyReciver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        anchor.post {
            //            popUp = MemoryPopUp(this)
//            popUp?.showAsDropDown(anchor)

            receiver = MyReciver(this)

            val intentFilter = IntentFilter()
            intentFilter.addAction("action")
            registerReceiver(receiver, intentFilter)

            val intent = Intent(this, MemoryManagerService::class.java)
            startService(intent)
        }

        initRecycerView()
    }

    override fun onDestroy() {
        val intent = Intent(this, MemoryManagerService::class.java)
        stopService(intent)
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    private fun initRecycerView() {
        val adapter = RvAdapter()
        adapter.models = getModels()
        recycler_View.layoutManager = LinearLayoutManager(this)
        recycler_View.adapter = adapter
    }

    private fun getModels(): ArrayList<Model> {
        val models = ArrayList<Model>()
        for (i in 1 until 1000) {
            models.add(Model("Name $i", i, R.drawable.ic_example))
        }

        return models
    }


    class MyReciver(val context: Context) : BroadcastReceiver() {

        var textView: TextView? = null
        var windowManager: WindowManager? = null

        override fun onReceive(p0: Context?, p1: Intent?) {
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

            Log.i("Memory", "get callback $text")
        }

    }

}

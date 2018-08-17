package com.ansgar.memorymanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        val memoryManager = MemoryManager.init(this)
        memoryManager.delay = 3000
        memoryManager.x = 10
        memoryManager.y = 300
    }

    override fun onPause() {
        super.onPause()
        MemoryManager.destroy()
    }

    private fun initRecyclerView() {
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

}

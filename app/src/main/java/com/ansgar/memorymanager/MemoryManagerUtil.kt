package com.ansgar.memorymanager

import android.util.Log
import java.io.*

class MemoryManagerUtil {

    companion object {
        fun getAppMemoryUsage(): String {
            val runtime = Runtime.getRuntime()
            val useMemoryMb = (runtime.totalMemory() - runtime.freeMemory()) / 1048576L
            val maxHeapSizeMb = runtime.maxMemory() / 1048576L
            val availableHeapSizeMb = maxHeapSizeMb - useMemoryMb

            return ("App used: " + useMemoryMb + " Mb" + "\n" + "Max heap size: " + maxHeapSizeMb + " Mb"
                    + "\n" + "Available heap size: " + availableHeapSizeMb + " Mb")
        }

        fun getCpuAppUsage(): String {
            var cpu = 0f
            val pid = android.os.Process.myPid()
            return try {
                val process = Runtime.getRuntime().exec("top -n 1 -d 1")
                val br = BufferedReader(InputStreamReader(process.inputStream))
                var i = 0
                while (br.readLine() != null) {
                    val line = br.readLine()?.split(" ") ?: arrayListOf()
                    line.forEach { item ->
                        if (isCpuValue(item)) cpu += item.replace("%", "").toFloat()
                    }
                    Log.i("!!!!", "Line $line ----- $pid")
                    i++
                    if (i == 10) break
                }
                "Total CPU: $cpu%"
            } catch (e: InterruptedException) {
                e.printStackTrace()
                "Not available"
            }
        }

        private fun isCpuValue(value: String): Boolean = value.matches("-?\\d+(\\.\\d+)?%".toRegex())

    }
}
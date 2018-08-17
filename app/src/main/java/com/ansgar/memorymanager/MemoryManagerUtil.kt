package com.ansgar.memorymanager

import android.util.Log
import java.io.*

class MemoryManagerUtil {

    companion object {
        var i = 0
        fun getAppMemoryUsage(): String {
            val runtime = Runtime.getRuntime()
            val useMemoryMb = (runtime.totalMemory() - runtime.freeMemory()) / 1048576L
            val maxHeapSizeMb = runtime.maxMemory() / 1048576L
            val availableHeapSizeMb = maxHeapSizeMb - useMemoryMb

            return ("App used: " + useMemoryMb + " Mb" + "\n" + "Max heap size: " + maxHeapSizeMb + " Mb"
                    + "\n" + "Available heap size: " + availableHeapSizeMb + " Mb")
        }

        fun getCpuAppUsage(pid: Int): String {
            var cpu = ""
            var mem = ""
            try {
                val process = Runtime.getRuntime().exec("top -n 1 -d 1")
                val br = BufferedReader(InputStreamReader(process.inputStream))
                var i = 0
                while (br.readLine() != null) {
                    if (i == 4) {
                        val line = br.readLine().split(" ") //
                        cpu = line[17]
                        mem = line[20]
                        Log.i("!!!!", "$i - Cpu: $line \n")
                    }
                    i++
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            return "CPU: $cpu%, MEM: $mem%"
        }

    }

}
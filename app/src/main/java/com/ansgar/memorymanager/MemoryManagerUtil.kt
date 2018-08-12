package com.ansgar.memorymanager

class MemoryManagerUtil {

    companion object {

        fun showAppMemoryUsage(): String {
            val runtime = Runtime.getRuntime()
            val useMemoryMb = (runtime.totalMemory() - runtime.freeMemory()) / 1048576L
            val maxHeapSizeMb = runtime.maxMemory() / 1048576L
            val availableHeapSizeMb = maxHeapSizeMb - useMemoryMb

            return ("App used: " + useMemoryMb + " Mb" + "\n" + "Max heap size: " + maxHeapSizeMb + " Mb"
                    + "\n" + "Available heap size: " + availableHeapSizeMb + " Mb")
        }

    }

}
package com.ansgar.memorymanager

class AppMemoryModel(
        var usedMemory: Long = 0,
        var remainingHeapSize: Long = 0,
        var maxHeapSize: Long = 0
) {
    override fun toString(): String {
        return "$usedMemory/$remainingHeapSize/$maxHeapSize"
    }
}
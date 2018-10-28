package com.ansgar.memorymanager

internal class AppMemoryModel(
        private var usedMemory: Long = 0,
        private var remainingHeapSize: Long = 0,
        private var maxHeapSize: Long = 0
) {
    override fun toString(): String {
        return "$usedMemory/$remainingHeapSize/$maxHeapSize"
    }
}
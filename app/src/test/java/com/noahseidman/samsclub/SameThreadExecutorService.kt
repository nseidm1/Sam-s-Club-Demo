package com.noahseidman.samsclub

import java.util.concurrent.AbstractExecutorService
import java.util.concurrent.TimeUnit

class SameThreadExecutorService : AbstractExecutorService() {

    //volatile because can be viewed by other threads
    @Volatile
    private var terminated: Boolean = false

    override fun shutdown() {
        terminated = true
    }

    override fun isShutdown(): Boolean {
        return terminated
    }

    override fun isTerminated(): Boolean {
        return terminated
    }

    @Throws(InterruptedException::class)
    override fun awaitTermination(theTimeout: Long, theUnit: TimeUnit): Boolean {
        shutdown() // TODO ok to call shutdown? what if the client never called shutdown???
        return terminated
    }

    override fun shutdownNow(): List<Runnable> {
        return emptyList()
    }

    override fun execute(theCommand: Runnable) {
        theCommand.run()
    }
}
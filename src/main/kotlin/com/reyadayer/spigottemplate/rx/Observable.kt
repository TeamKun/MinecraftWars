package com.reyadayer.spigottemplate.rx

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class Observable private constructor(private val tick: Long) {
    companion object {
        fun interval(tick: Long): Observable {
            return Observable(tick)
        }
    }

    private var limit: Long? = null
    private var doOnNextFunction: ((data: Long) -> Unit)? = null
    private var doOnCompleteFunction: ((data: Long) -> Unit)? = null
    private var doOnCompleteConditionFunction: ((data: Long) -> Boolean)? = null
    private var doOnErrorFunction: ((data: Long) -> Unit)? = null
    private var doOnErrorConditionFunction: ((data: Long) -> Boolean)? = null

    fun take(data: Long): Observable {
        limit = data
        return this
    }

    fun doOnNext(function: (data: Long) -> Unit): Observable {
        doOnNextFunction = function
        return this
    }

    fun doOnCompleteCondition(function: (data: Long) -> Boolean): Observable {
        doOnCompleteConditionFunction = function
        return this
    }

    fun doOnComplete(function: (data: Long) -> Unit): Observable {
        doOnCompleteFunction = function
        return this
    }

    fun doOnErrorCondition(function: (data: Long) -> Boolean): Observable {
        doOnErrorConditionFunction = function
        return this
    }

    fun doOnError(function: (data: Long) -> Unit): Observable {
        doOnErrorFunction = function
        return this
    }

    fun subscribe(plugin: JavaPlugin) {
        object : BukkitRunnable() {
            var data: Long = 0L
            override fun run() {
                val isComplete = doOnCompleteConditionFunction?.invoke(data) ?: false
                if (isComplete || isLimit(data)) {
                    doOnCompleteFunction?.invoke(data)
                    plugin.server.scheduler.cancelTask(taskId)
                }
                val isError = doOnErrorConditionFunction?.invoke(data) ?: false
                if (isError) {
                    doOnErrorFunction?.invoke(data)
                    plugin.server.scheduler.cancelTask(taskId)
                }
                doOnNextFunction?.invoke(data)
                data += 1
            }
        }.runTaskTimerAsynchronously(plugin, 0, tick)
    }

    private fun isLimit(data: Long): Boolean {
        val currentLimit = limit ?: return false
        return data >= currentLimit
    }
}


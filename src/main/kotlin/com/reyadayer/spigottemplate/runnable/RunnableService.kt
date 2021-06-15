package com.reyadayer.spigottemplate.runnable

import org.bukkit.scheduler.BukkitRunnable

object RunnableService {
    fun create(function: () -> Unit): BukkitRunnable = object : BukkitRunnable() {
        override fun run() {
            function.invoke()
        }
    }
}
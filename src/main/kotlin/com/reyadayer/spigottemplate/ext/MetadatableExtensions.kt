package com.reyadayer.spigottemplate.ext

import com.reyadayer.spigottemplate.metadata.MetadataKey
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.metadata.Metadatable
import org.bukkit.plugin.java.JavaPlugin

@Suppress("UNCHECKED_CAST")
fun <T> Metadatable.getMeta(metadataKey: MetadataKey<T>): T? {
    return try {
        getMetadata(metadataKey.value)[0].value() as T
    } catch (e: IndexOutOfBoundsException) {
        null
    }
}

fun <T> Metadatable.getMeta(metadataKey: MetadataKey<T>, default: T): T {
    return getMeta(metadataKey) ?: default
}

fun <T> Metadatable.setMeta(plugin: JavaPlugin, metadataKey: MetadataKey<T>, value: T) {
    setMetadata(metadataKey.value, FixedMetadataValue(plugin, value))
}

fun <T> Metadatable.removeMeta(plugin: JavaPlugin, metadataKey: MetadataKey<T>) {
    removeMetadata(metadataKey.value, plugin)
}
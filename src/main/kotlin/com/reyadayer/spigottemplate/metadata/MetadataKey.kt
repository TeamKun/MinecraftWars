package com.reyadayer.spigottemplate.metadata

sealed class MetadataKey<T>(val value: String) {
    object IsPlayerInteract : MetadataKey<Boolean>("IsPlayerInteract")
}
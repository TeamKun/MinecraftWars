package team.kun.minecraftwars.metadata

sealed class MetadataKey<T>(val value: String) {
    object IsPlayerInteract : MetadataKey<Boolean>("IsPlayerInteract")
}
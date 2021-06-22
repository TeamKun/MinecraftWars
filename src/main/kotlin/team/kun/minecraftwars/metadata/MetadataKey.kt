package team.kun.minecraftwars.metadata

import org.bukkit.entity.Entity
import org.bukkit.entity.Player

sealed class MetadataKey<T>(val value: String) {
    object IsPlayerInteract : MetadataKey<Boolean>("IsPlayerInteract")
    object Owner : MetadataKey<Player>("Owner")
    object IsAttacking : MetadataKey<Boolean>("IsAttacking")
    object Name : MetadataKey<String>("Name")
    object PlayerMobs : MetadataKey<List<Entity>>("PlayerMobs")
}
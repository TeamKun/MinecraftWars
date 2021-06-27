package team.kun.minecraftwars.item

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import team.kun.minecraftwars.ext.setNbt
import team.kun.minecraftwars.metadata.BasicNbtKey

abstract class PickUpItem : Item() {
    abstract fun pickUp(player: Player, plugin: JavaPlugin)

    fun spawn(location: Location, plugin: JavaPlugin) {
        val item = location.world?.dropItem(location, toItemStack(plugin)) ?: return
        item.customName = item.itemStack.itemMeta?.displayName
        item.isCustomNameVisible = true
        item.isGlowing = true
        item.persistentDataContainer.setNbt(plugin, BasicNbtKey.IsMinecraftWarsEntity, 1)
    }
}
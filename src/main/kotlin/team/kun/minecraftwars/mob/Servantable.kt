package team.kun.minecraftwars.mob

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

interface Servantable {
    fun spawn(location: Location, plugin: JavaPlugin)
    fun dead(killer: Entity, plugin: JavaPlugin)

    fun onSpawn(owner: Player, plugin: JavaPlugin)
    fun onDead(killer: Player, plugin: JavaPlugin)
}
package team.kun.minecraftwars.item

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import team.kun.minecraftwars.mob.ZombieMob

class ZombieItem : PickUpItem() {
    override val name = "ゾンビ"
    override val description = listOf(
        "ゾンビを召喚"
    )
    override val itemStack = ItemStack(Material.ZOMBIE_SPAWN_EGG)

    override fun pickUp(player: Player, plugin: JavaPlugin) {
        player.playSound(player.location, Sound.ENTITY_ZOMBIE_AMBIENT, 1.0f, 1.0f)
        ZombieMob(player).spawn(player.location, plugin)
    }
}
package team.kun.minecraftwars.item

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import team.kun.minecraftwars.mob.GolemMob

class GolemItem : PickUpItem() {
    override val name = "ゴーレム"
    override val description = listOf(
        "ゴーレムを召喚"
    )
    override val itemStack = ItemStack(Material.IRON_BLOCK)

    override fun pickUp(player: Player, plugin: JavaPlugin) {
        player.playSound(player.location, Sound.ENTITY_SNOW_GOLEM_AMBIENT, 1.0f, 1.0f)
        GolemMob(player).spawn(player.location, plugin)
    }
}
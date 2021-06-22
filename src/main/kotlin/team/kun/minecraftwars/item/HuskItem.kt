package team.kun.minecraftwars.item

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import team.kun.minecraftwars.mob.HuskMob

class HuskItem : PickUpItem() {
    override val name = "ハスク"
    override val description = listOf(
        "ハスクを召喚"
    )
    override val itemStack = ItemStack(Material.HUSK_SPAWN_EGG)

    override fun pickUp(player: Player, plugin: JavaPlugin) {
        player.playSound(player.location, Sound.ENTITY_HUSK_AMBIENT, 1.0f, 1.0f)
        HuskMob(player).spawn(player.location, plugin)
    }
}
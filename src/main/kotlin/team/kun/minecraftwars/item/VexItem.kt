package team.kun.minecraftwars.item

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import team.kun.minecraftwars.mob.VexMob

class VexItem : PickUpItem() {
    override val name = "ヴェックス"
    override val description = listOf(
        "ヴェックスを召喚"
    )
    override val itemStack = ItemStack(Material.VEX_SPAWN_EGG)

    override fun pickUp(player: Player, plugin: JavaPlugin) {
        player.playSound(player.location, Sound.ENTITY_VEX_AMBIENT, 1.0f, 1.0f)
        VexMob(player).spawn(player.location, plugin)
    }
}
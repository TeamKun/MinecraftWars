package team.kun.minecraftwars.item

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import team.kun.minecraftwars.mob.PhantomMob

class VexItem : PickUpItem() {
    override val name = "ファントム"
    override val description = listOf(
        "ファントムを召喚"
    )
    override val itemStack = ItemStack(Material.PHANTOM_SPAWN_EGG)

    override fun pickUp(player: Player, plugin: JavaPlugin) {
        player.playSound(player.location, Sound.ENTITY_VEX_AMBIENT, 1.0f, 1.0f)
        PhantomMob(player).spawn(player.location, plugin)
    }
}
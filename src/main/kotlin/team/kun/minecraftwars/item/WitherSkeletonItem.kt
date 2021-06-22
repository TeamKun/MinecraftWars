package team.kun.minecraftwars.item

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import team.kun.minecraftwars.mob.SkeletonMob

class WitherSkeletonItem : PickUpItem() {
    override val name = "ウィザースケルトン"
    override val description = listOf(
        "ウィザースケルトンを召喚"
    )
    override val itemStack = ItemStack(Material.WITHER_SKELETON_SPAWN_EGG)

    override fun pickUp(player: Player, plugin: JavaPlugin) {
        player.playSound(player.location, Sound.ENTITY_WITHER_SKELETON_AMBIENT, 1.0f, 1.0f)
        SkeletonMob(player).spawn(player.location, plugin)
    }
}
package team.kun.minecraftwars.item

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import team.kun.minecraftwars.ext.getMeta
import team.kun.minecraftwars.metadata.MetadataKey

class DiamondItem : PickUpItem() {
    override val name = "ダイヤモンド"
    override val description = listOf(
        "ダイヤモンド装備にする"
    )
    override val itemStack = ItemStack(Material.DIAMOND)

    override fun pickUp(player: Player, plugin: JavaPlugin) {
        player.playSound(player.location, Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f)
        player.getMeta(MetadataKey.PlayerMobs)?.filterIsInstance<LivingEntity>()?.forEach {
            val itemInMainHand = it.equipment?.itemInMainHand
            if (itemInMainHand?.type == Material.NETHERITE_SWORD) {
                return@forEach
            }
            it.equipment?.setItemInMainHand(ItemStack(Material.DIAMOND_SWORD))
            it.equipment?.chestplate = ItemStack(Material.DIAMOND_CHESTPLATE)
            it.equipment?.leggings = ItemStack(Material.DIAMOND_LEGGINGS)
            it.equipment?.boots = ItemStack(Material.DIAMOND_BOOTS)
        }
    }
}
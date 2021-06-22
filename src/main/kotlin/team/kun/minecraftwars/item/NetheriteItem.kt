package team.kun.minecraftwars.item

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import team.kun.minecraftwars.ext.getMeta
import team.kun.minecraftwars.metadata.MetadataKey

class NetheriteItem : PickUpItem() {
    override val name = "ネザライト"
    override val description = listOf(
        "ネザライト装備にする"
    )
    override val itemStack = ItemStack(Material.IRON_INGOT)

    override fun pickUp(player: Player, plugin: JavaPlugin) {
        player.playSound(player.location, Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f)
        player.getMeta(MetadataKey.PlayerMobs)?.filterIsInstance<LivingEntity>()?.forEach {
            it.equipment?.setItemInMainHand(ItemStack(Material.NETHERITE_SWORD))
            it.equipment?.chestplate = ItemStack(Material.NETHERITE_CHESTPLATE)
            it.equipment?.leggings = ItemStack(Material.NETHERITE_LEGGINGS)
            it.equipment?.boots = ItemStack(Material.NETHERITE_BOOTS)
        }
    }
}
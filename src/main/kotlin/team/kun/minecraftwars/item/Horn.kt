package team.kun.minecraftwars.item

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Creature
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import team.kun.minecraftwars.ext.getMeta
import team.kun.minecraftwars.ext.playSound
import team.kun.minecraftwars.ext.setMeta
import team.kun.minecraftwars.metadata.MetadataKey

class Horn : Item() {
    override val name = "角笛"
    override val description = listOf(
        "攻撃司令を切り替える"
    )
    override val itemStack = ItemStack(Material.IRON_HORSE_ARMOR)

    fun execute(player: Player, plugin: JavaPlugin) {
        val isAttacking = player.getMeta(MetadataKey.IsAttacking, false)
        if (isAttacking) {
            player.location.playSound(Sound.ENTITY_SHULKER_CLOSE, 1.0f, 1.0f)
            player.sendActionBar("${ChatColor.RED}攻撃停止")
        } else {
            player.location.playSound(Sound.EVENT_RAID_HORN, 8.0f, 1.3f)
            player.sendActionBar("${ChatColor.GREEN}攻撃開始")
            player.getMeta(MetadataKey.PlayerMobs)?.filterIsInstance<Creature>()?.forEach { entity ->
                entity.target = null
            }
        }
        player.setMeta(plugin, MetadataKey.IsAttacking, !isAttacking)
    }
}
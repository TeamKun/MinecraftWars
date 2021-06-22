package team.kun.minecraftwars.item

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.plugin.java.JavaPlugin
import team.kun.minecraftwars.ext.setNbt
import team.kun.minecraftwars.metadata.BasicNbtKey

class HeadItem(private val player: Player) : Item() {
    override val name = "${player.name}の頭"
    override val description = listOf(
        "プレイヤーの頭"
    )
    override val itemStack = ItemStack(Material.PLAYER_HEAD)

    override fun toItemStack(plugin: JavaPlugin): ItemStack {
        val resultItemStack = itemStack
        val itemMeta = resultItemStack.itemMeta
        if(itemMeta is SkullMeta){
            itemMeta.owningPlayer = player
        }
        itemMeta?.setDisplayName(name)
        itemMeta?.lore = description
        itemMeta?.persistentDataContainer?.setNbt(plugin, BasicNbtKey.Name, this::class.simpleName)
        resultItemStack.itemMeta = itemMeta
        return resultItemStack
    }
}
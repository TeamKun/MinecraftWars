package team.kun.minecraftwars.item

import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import team.kun.minecraftwars.ext.getNbt
import team.kun.minecraftwars.ext.setNbt
import team.kun.minecraftwars.metadata.BasicNbtKey

abstract class Item {
    abstract val name: String
    abstract val description: List<String>
    abstract val itemStack: ItemStack

    open fun toItemStack(plugin: JavaPlugin): ItemStack {
        val resultItemStack = itemStack
        val itemMeta = resultItemStack.itemMeta
        itemMeta?.setDisplayName(name)
        itemMeta?.lore = description
        itemMeta?.persistentDataContainer?.setNbt(plugin, BasicNbtKey.Name, this::class.simpleName)
        resultItemStack.itemMeta = itemMeta
        return resultItemStack
    }

    fun toItemStack(amount: Int, plugin: JavaPlugin): ItemStack {
        val itemStack = toItemStack(plugin)
        itemStack.amount = amount
        return itemStack
    }

    fun equal(targetItemStack: ItemStack, plugin: JavaPlugin): Boolean {
        return this::class.simpleName == targetItemStack.itemMeta?.persistentDataContainer?.getNbt(
            plugin,
            BasicNbtKey.Name
        )
    }
}
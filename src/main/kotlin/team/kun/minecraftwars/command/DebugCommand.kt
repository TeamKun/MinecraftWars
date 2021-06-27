package team.kun.minecraftwars.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import team.kun.minecraftwars.MinecraftWars
import team.kun.minecraftwars.ext.getMeta
import team.kun.minecraftwars.ext.setMeta
import team.kun.minecraftwars.item.Horn
import team.kun.minecraftwars.metadata.MetadataKey
import team.kun.minecraftwars.mob.*

class DebugCommand(private val plugin: MinecraftWars) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            return when (Action.find(args[0])) {
                Action.SPAWN -> {
                    val location = sender.location.clone()
                    val mob = when (SpawnAction.find(args[1])) {
                        SpawnAction.ZOMBIE -> ZombieMob(sender)
                        SpawnAction.SKELETON -> SkeletonMob(sender)
                        SpawnAction.GOLEM -> GolemMob(sender)
                        SpawnAction.PHANTOM -> PhantomMob(sender)
                        SpawnAction.HUSK -> HuskMob(sender)
                        else -> null
                    }
                    mob?.spawn(location, plugin)
                    true
                }
                Action.ATTACK -> {
                    val isAttack = sender.getMeta(MetadataKey.IsAttacking, false)
                    sender.setMeta(plugin, MetadataKey.IsAttacking, !isAttack)
                    true
                }
                Action.ITEM -> {
                    sender.inventory.addItem(Horn().toItemStack(plugin))
                    true
                }
                else -> false
            }
        } else {
            return false
        }
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): List<String> {
        when (args.size) {
            1 -> {
                val keys = Action.values().map { it.value }
                return keys.filter { it.startsWith(args[0]) }
            }
            2 -> {
                val action = Action.find(args[0]) ?: return listOf()
                return when (action) {
                    Action.SPAWN -> {
                        val keys = SpawnAction.values().map { it.value }
                        keys.filter { it.startsWith(args[1]) }
                    }
                    else -> listOf()
                }
            }
            else -> {
                return emptyList()
            }
        }
    }

    private enum class Action(val value: String) {
        ATTACK("attack"),
        SPAWN("spawn"),
        ITEM("item");

        companion object {
            fun find(value: String?): Action? {
                return values().firstOrNull { it.value == value }
            }
        }
    }

    private enum class SpawnAction(val value: String) {
        ZOMBIE("zombie"),
        SKELETON("skeleton"),
        GOLEM("golem"),
        PHANTOM("phantom"),
        HUSK("husk");

        companion object {
            fun find(value: String?): SpawnAction? {
                return values().firstOrNull { it.value == value }
            }
        }
    }
}
package team.kun.minecraftwars.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import team.kun.minecraftwars.MinecraftWars
import team.kun.minecraftwars.game.Game

class GameCommand(private val plugin: MinecraftWars) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            return when (Action.find(args[0])) {
                Action.START -> {
                    Game().start(plugin)
                    true
                }
                Action.STOP -> {
                    Game().stop(plugin)
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
            else -> {
                return emptyList()
            }
        }
    }

    private enum class Action(val value: String) {
        START("start"),
        STOP("stop");

        companion object {
            fun find(value: String?): Action? {
                return values().firstOrNull { it.value == value }
            }
        }
    }
}
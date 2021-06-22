package team.kun.minecraftwars

import net.kunmc.lab.theworld.PluginCommands
import org.bukkit.permissions.PermissionDefault
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.annotation.command.Command
import org.bukkit.plugin.java.annotation.command.Commands
import org.bukkit.plugin.java.annotation.dependency.Dependency
import org.bukkit.plugin.java.annotation.dependency.DependsOn
import org.bukkit.plugin.java.annotation.permission.Permission
import org.bukkit.plugin.java.annotation.permission.Permissions
import org.bukkit.plugin.java.annotation.plugin.ApiVersion
import org.bukkit.plugin.java.annotation.plugin.Plugin
import org.bukkit.plugin.java.annotation.plugin.author.Author
import team.kun.minecraftwars.command.DebugCommand
import team.kun.minecraftwars.command.GameCommand
import team.kun.minecraftwars.ext.initCommand
import team.kun.minecraftwars.ext.registerListener
import team.kun.minecraftwars.ext.scheduleRunnable
import team.kun.minecraftwars.listener.EntityListener
import team.kun.minecraftwars.listener.PlayerListener
import team.kun.minecraftwars.runnable.ItemRunnable

@Plugin(name = "MinecraftWars", version = "1.0-SNAPSHOT")
@Author("ReyADayer")
@ApiVersion(ApiVersion.Target.v1_15)
@DependsOn(
    Dependency("ProtocolLib")
)
@Commands(
    Command(
        name = PluginCommands.DEBUG,
        desc = "debug command",
        usage = "/<command>",
        permission = PluginPermissions.ADMIN,
        permissionMessage = "You don't have <permission>"
    ),
    Command(
        name = PluginCommands.GAME,
        desc = "game command",
        usage = "/<command>",
        permission = PluginPermissions.ADMIN,
        permissionMessage = "You don't have <permission>"
    )
)
@Permissions(
    Permission(
        name = PluginPermissions.ADMIN,
        desc = "Gives access to Atlantis admin commands",
        defaultValue = PermissionDefault.OP
    )
)
class MinecraftWars : JavaPlugin() {
    var isStart: Boolean = false

    override fun onEnable() {
        initCommand(PluginCommands.DEBUG, DebugCommand(this))
        initCommand(PluginCommands.GAME, GameCommand(this))

        registerListener(EntityListener(this))
        registerListener(PlayerListener(this))
    }

    override fun onDisable() {
    }
}